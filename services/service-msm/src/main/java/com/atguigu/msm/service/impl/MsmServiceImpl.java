package com.atguigu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author li
 * @create 2021-05-28 15:27
 */
@Service
public class MsmServiceImpl implements MsmService {
    //发送短信
    @Override
    public boolean send(Map<String, Object> param, String phone) {


        if (StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");
        IAcsClient client = new DefaultAcsClient(profile);

        //相关固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone);//手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");//签名名称
        request.putQueryParameter("TemplateCode", "");//模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));


        try {
            client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
