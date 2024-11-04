package com.atguigu.gulimall.product;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

//    @Autowired
//    OSSClient ossClient;
//
//    @Test
//    public void testUpload() throws FileNotFoundException {
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        /*String endpoint = "oss-cn-beijing.aliyuncs.com";
//        *//*云账户AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常维护*//*
//        String accessKeyId = "LTAI5tHgzE3a4mT7vsGUpjDt";
//        String accessKeySecret = "xgsFxxytT8anhPW4n5rOW8CLlCHIoo";
//
//        //创建OSSClient实例
//        OSS ossClinet = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);*/
//
//        //上传文件流(图片的访问路径)
//        InputStream inputStream = new FileInputStream("C:\\Users\\ASUS\\Pictures\\Camera Roll\\3d7d279ced66d15c3ff90a0ada15f0348ec5cd8bf4295ee88.jpg");
//        ossClient.putObject("gulimall-liuyongjian","3d7d279ced66d15c3ff90a0ada15f0348ec5cd8bf4295ee88(1).jpg",inputStream);
//
//        //关闭OSSClist
//        ossClient.shutdown();
//
//        System.out.println("上传成功(❁´◡`❁)");
//
//    }


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
