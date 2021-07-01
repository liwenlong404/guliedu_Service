package com.atguigu.msm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtil;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 * @create 2021-05-28 15:26
 */
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送手机短信验证码
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //1.从redis获取验证码，如果获取到直接返回，不发送
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.如果redis没获取到验证码，则发送

        //生成验证码（随机值），传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);

        //调用service发送短信的方法
       boolean isSend = msmService.send(param,phone);

       if (isSend){
           //发送成功，把发送成功验证码放到redis中
           //设置有效时间5分钟
           redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
           return R.ok();
       }else {
           return R.error().message("短信发送失败");
       }

    }

    //发送邮箱验证码
    @GetMapping("sendMile/{mail}")
    public R sendMile(@PathVariable String mail){
        //1.从redis获取验证码，如果获取到直接返回，不发送
        String code = redisTemplate.opsForValue().get(mail);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.如果redis没获取到验证码，则发送
        //生成验证码（随机值）
        code = RandomUtil.getFourBitRandom();

        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.163.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setCharset("utf-8");//设置发送的字符类型
        try {
            email.addTo(mail);//设置收件人
            email.setFrom("batchelorli@163.com","batchelorli");//发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication("batchelorli@163.com","LZTKYAOFEUUYVCBT");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
            email.setSubject("验证码");//设置发送主题
            email.setMsg("你的验证码为"+code+"。5分钟内有效");//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            e.printStackTrace();
        }

        //发送成功，把发送成功验证码放到redis中
        //设置有效时间5分钟
        redisTemplate.opsForValue().set(mail, code,5, TimeUnit.MINUTES);
        return R.ok();
    }

}
