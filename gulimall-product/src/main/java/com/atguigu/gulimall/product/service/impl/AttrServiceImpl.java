package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.ProductConstant;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    /**
     * 分组
     */
    @Autowired
    AttrGroupDao attrGroupDao;

    /**
     * 分类
     * @param params
     * @return
     */
    @Autowired
    CategoryDao categoryDao;


    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        //将页面提交来的数据，封装进AttrEntity中
        /**
         * 可以一个一个进行封装，但太过麻烦
         * attrEntity.setAttrName(attr.getAttrName());
         */
        /**
         * 可以使用spring家提供的工具类 BeanUtils
         * 中提供的copyProperties方法
         */
        BeanUtils.copyProperties(attr,attrEntity);
        //先给attr表中保存基本信息
        //保存基本数据
        this.save(attrEntity);
        //再给关联的分组信息进行保存
        //保存关联关系
        /**
         * 对attr获取的属性，等于1证明为基本属性，才需要保存分组关系
         * 通过对com.atguigu.common.constant.ProductConstant 枚举类的引用 ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()获取值
         * 方便后期对其统一修改，只要修改枚举类里的值，即可全部修改
         */
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        //分页方法，当条件复杂时，在外创建new QueryWrapper<AttrEntity>()在引入page中
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type","base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        //根据不同情况封装查询条件
        if(catelogId != 0){
            queryWrapper.eq("catelog_id",catelogId);
        }

        //获取到检索条件,用来做模糊查询
        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)){
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }


        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();

        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            /**
             * 设置分类和分组的名字
             */
            //分组
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId())
                );//通过中间表关联关系,获取到属性id
                if (attrId != null) {
                    //attrId.getAttrGroupId()通过分类id获取到组id
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }


            //分类
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }


            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);

        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        //更具attrId查询出AttrEntity的信息
        AttrEntity attrEntity = this.getById(attrId);
        //将AttrEntity中的信息拷贝到attrRespVo中
        BeanUtils.copyProperties(attrEntity,respVo);

        //出来AttrEntity中的基本信息还要补齐其他信息
        /**
         * 设置分组信息，确保其为基本属性才对其修改分组信息
         */
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelation = relationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId)
            );
            if(attrAttrgroupRelation != null){
                respVo.setAttrGroupId(attrAttrgroupRelation.getAttrId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelation.getAttrGroupId());
                if (attrGroupEntity != null){
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }


        /**
         * 设置分类信息
         */
        Long catelogId = attrEntity.getCatelogId(); //当前类中有分类id但不完整
        Long[] catelogPath = categoryService.findCatelogPath(catelogId); //注入分类实现类，通过catelogId查询出完整路径
        respVo.setCatelogPath(catelogPath); //写入完整路径

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId); //通过对id：catelogId查询出全部的categoryEntity
        if(categoryEntity != null){
            respVo.setCatelogName(categoryEntity.getName()); //写入分类名
        }


        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        //先对基本数据进行更新
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        //1、修改分组关联
        /**
         * 确保修改的分组类型为基本属性
         */
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());

            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (count > 0){
                relationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            }else {
                relationDao.insert(relationEntity);
            }
        }


    }

}