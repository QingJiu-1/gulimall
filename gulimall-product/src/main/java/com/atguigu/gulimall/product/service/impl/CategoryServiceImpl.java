package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /** 可以通过该方法导入，也可以通过ServiceImpl泛型中指定的baseMapper
     * @Autowired
     * CategoryDao categoryDao;
     */

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2、组装成父子的树形结构
        //2.1 找到所有的一级分类
        /*标准写法
        entities.stream().filter( (categoryEntitya) -> {
            return categoryEntitya.getParentCid().equals("0");
        }).collect(Collectors.toList());*/
        //简写形式：因为只要返回categoryEntitya，且只有一个categoryEntitya元素 小括号、大括号和return可以省略
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
                    //System.out.println("menu前" + menu);
                    menu.setChildren(getChildrens(menu,entities));
                    //System.out.println("menu后" + menu);
                    return menu;
        }).sorted((menu1,menu2) -> {
            //System.out.println( "menu1" + menu1);
            //System.out.println( "menu2" + menu2);
            return (menu1.getSort() == null ? 0 : menu1.getSort())- (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1、检查当前删除的菜单，是不是被别的地方引用
        //批量直接删除删除
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 找到catelogId的完整路径
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        //Collections用于反转指定列表的元素顺序;
        //reverse是原地操作方法，不会返回新的 List，它直接改变传入的 List 对象的顺序
        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        boolean b = this.updateById(category);

        //要更新关联表需要建关联注入
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());


    }

    private List<Long> findParentPath(Long catelogId,List<Long> paths){

        //收集当前信息id
        paths.add(catelogId);
        //查出当前分类的信息
        CategoryEntity byId = this.getById(catelogId);
        //如果它有父分类找到其父分类
        if(byId.getParentCid() != 0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }


    /**
     * 调用该方法将找到的所有一级分类的子分类全部找出遍历
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == root.getCatId()
        ).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return  categoryEntity;
        }).sorted((menu1,menu2) -> {
            //2、菜单的排序
            //System.out.println( "子菜单menu1" + menu1);
            //System.out.println( "子菜单menu2" + menu2);
            return  (menu1.getSort() == null ? 0 : menu1.getSort())- (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

}