package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author QingJiu
 * @email liuyongjian01@gmail.com
 * @date 2024-10-12 00:29:31
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
