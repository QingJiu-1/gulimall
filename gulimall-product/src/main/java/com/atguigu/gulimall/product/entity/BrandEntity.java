package com.atguigu.gulimall.product.entity;

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
	@TableId
	private Long brandId;
	/**
	 * 品牌名 不能为空
	 * @NotNull、@NotEmpty和@NotBlank
	 * @NotEmpty:表示所标注的元素必须不是null或者空串
	 * @NotBlank:表示所标注的元素，必须包含至少有一个非空字符（不能是空格，至少有一个非空格字符）
	 * 这些注解除了可以对其进行校验判断外，还以自定义其校验内容;
	 */
	@NotBlank(message = "品牌名必须提交")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty
	@URL(message = "logo必须是一个合法url地址")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */

	private Integer showStatus;
	/**
	 * 检索首字母 JSR303并未提供，但是可以通过@Pattern往里传入正则表达式实现
	 */
	@NotEmpty
	@Pattern(regexp = "/^[a-zA-Z]]$/",message = "检索首字母必须是一个字母")
	private String firstLetter;
	/**
	 * 排序：
	 *  注意：@NotEmpty不能为整数字段表示空需要使用@NotNull
	 */
	@NotNull
	@Min(value = 0,message = "排序必须大于等于0")
	private Integer sort;

}
