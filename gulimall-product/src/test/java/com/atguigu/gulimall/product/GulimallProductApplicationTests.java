package com.atguigu.gulimall.product;


import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import feign.QueryMap;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
        //添加
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功.......");
    }

    @Test
    public void updateName() {
        //修改
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("华为手机");
        brandEntity.setName("华为");
        brandService.updateById(brandEntity);
        System.out.println("修改成功");
    }


    @Test
    public void select() {
        //修改
        String byId = String.valueOf(brandService.getById(1L));
        List<BrandEntity> brandId = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "华为"));
        brandId.forEach((item) -> {
            System.out.println(item);
        });
        System.out.println("查询成功"+byId);
    }




}
