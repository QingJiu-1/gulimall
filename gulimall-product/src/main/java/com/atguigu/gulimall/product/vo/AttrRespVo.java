package com.atguigu.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{

    /**
     * 前端页面的分类名字
     */
    private String catelogName;

    /**
     * 前端页面的分组名字
     */
    private String groupName;

}
