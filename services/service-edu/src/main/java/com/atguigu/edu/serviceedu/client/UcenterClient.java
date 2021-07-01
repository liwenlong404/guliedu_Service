package com.atguigu.edu.serviceedu.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.commentvo.UcenterMemberCom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * @create 2021-06-27 9:25
 */
@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{memberId}")
    public UcenterMemberCom getInfoUc(@PathVariable("memberId") String  memberId);
}


