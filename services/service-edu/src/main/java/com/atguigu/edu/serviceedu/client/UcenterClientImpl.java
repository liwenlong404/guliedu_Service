package com.atguigu.edu.serviceedu.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.commentvo.UcenterMemberCom;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;

/**
 * @author li
 * @create 2021-06-27 9:30
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMemberCom getInfoUc(@PathVariable("memberId") String  memberId) {
        return null;
    }
}
