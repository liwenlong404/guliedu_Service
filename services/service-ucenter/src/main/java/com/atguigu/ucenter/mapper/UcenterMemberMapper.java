package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author li
 * @since 2021-05-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegister(String day);
}
