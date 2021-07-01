package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author li
 * @since 2021-05-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    //注册
    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegister(String day);
}
