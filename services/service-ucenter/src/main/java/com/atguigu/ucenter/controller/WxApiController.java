package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.config.EduException;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.atguigu.ucenter.utils.ConstantWxUtils;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author li
 * @create 2021-06-12 14:28
 */
//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //获取信息添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            //1.获取code值

            //2.拿着code请求微信固定地址，获取accsess token 和openid
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接参数：id,  秘钥  ，和code
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);

            //请求拼接后的url,得到access token 和openid
            //使用httpclient发送请求

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo取出accessToken和openid
            //把accessTokenInfo字符串转换map集合，根据map里面可以获取对应值

            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //将扫码人信息添加到数据库
            //判断数据库表里是否存在相同微信信息，openid
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {//member为空·则添加扫码人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid);

                //发送请求，获取信息
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //获取userInfo字符串的信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");


                member = new UcenterMember();
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                memberService.save(member);
            }

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());


            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            throw new EduException(20001, "登录失败");
        }
    }


    @GetMapping("login")
    public String getWxCode() {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");


        return "redirect:" + url;
    }

}
