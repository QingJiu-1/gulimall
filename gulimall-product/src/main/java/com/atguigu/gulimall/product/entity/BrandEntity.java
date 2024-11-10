package com.atguigu.gulimall.product.entity;

import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.ListValue;
import com.atguigu.common.valid.UpdateGroup;
import com.atguigu.common.valid.UpdateStatusGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author QingJiu
 * @email liuyongjian01@gmail.com
 * @date 2024-10-08 23:38:35
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改必须指定品牌id",groups = {UpdateGroup.class})//什么情况下必须不为空
	@Null(message = "新增不能指定id",groups = {AddGroup.class})//什么情况下必须为空
	@TableId
	private Long brandId;
	/**
	 * 品牌名 不能为空
	 * @NotNull、@NotEmpty和@NotBlank
	 * @NotEmpty:表示所标注的元素必须不是null或者空串
	 * @NotBlank:表示所标注的元素，必须包含至少有一个非空字符（不能是空格，至少有一个非空格字符）
	 * 这些注解除了可以对其进行校验判断外，还以自定义其校验内容;
	 */
	@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(groups = {AddGroup.class}) //对其在新增时进行约束一下
	@URL(message = "logo必须是一个合法url地址",groups = {AddGroup.class,UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class,UpdateStatusGroup.class})
	@ListValue(vals ={0,1},groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母 JSR303并未提供，但是可以通过@Pattern往里传入正则表达式实现
	 */
	@NotEmpty(groups = {AddGroup.class}) //新增加时不能为空，修改时可以为空
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索首字母必须是一个字母",groups = {AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序：
	 *  注意：@NotEmpty不能为整数字段表示空需要使用@NotNull
	 */
	@NotNull(groups = {AddGroup.class}) //新增时不能为空，修改时可以为空
	@Min(value = 0,message = "排序必须大于等于0",groups = {AddGroup.class,UpdateGroup.class})
	private Integer sort;

}
