package com.atguigu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性分组
 * 
 * @author QingJiu
 * @email liuyongjian01@gmail.com
 * @date 2024-10-08 23:38:35
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	private Long attrGroupId;
	/**
	 * 组名
	 */
	private String attrGroupName;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 描述
	 */
	private String descript;
	/**
	 * 组图标
	 */
	private String icon;
	/**
	 * 所属分类id
	 */
	private Long catelogId;

	/**
	 * 完整路径
	 * 由于该字段不是数据库字段：使用该注解可以避免 MyBatis-Plus 在生成 SQL 语句时，尝试查找不存在的字段，从而避免因缺失字段导致的 SQL 错误
	 */
	@TableField(exist = false)
	private Long[]  catelogPath;

}
