package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.EduException;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.mapper.UcenterMemberMapper;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-05-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登陆
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号与密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new EduException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        //判断查出对象是否为空
        if (mobileMember == null){
            //数据库无此手机号
            throw new EduException(20001,"登录失败");
        }

        //判断密码
        //将输入密码加MD5密，再进行比较
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new EduException(20001,"登录失败，密码错误");
        }

        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new EduException(20001,"登录失败，已被禁用");
        }

        //登录成功
        //生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());



        return jwtToken;

    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //验证码由mail邮箱发送，存redis时，key为邮箱，value为code
        String mail = registerVo.getMail();


        //非空判断
        if (StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)
        ){
            throw new EduException(20001,"注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mail);
        if (!code.equals(redisCode)){
            throw new EduException(20001,"注册失败，验证码错误");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw new EduException(20001,"注册失败,手机号已存在");
        }

        //最终添加
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://edu-teacher-li.oss-cn-beijing.aliyuncs.com/2021/04/27/3c2bce3f-f3c3-42fa-ba54-17b23b82a57cfile.png");

        baseMapper.insert(ucenterMember);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }

}




















