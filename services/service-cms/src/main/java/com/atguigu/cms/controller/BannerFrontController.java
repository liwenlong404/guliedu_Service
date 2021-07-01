package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/educms/bannerFront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner(){
      List<CrmBanner> list = bannerService.selectAllBanner();

      return R.ok().data("list",list);
    }

    //redis测试
    @GetMapping("getRedisDta")
    public R getRedisDta(){
        ValueOperations<String, String> opsForValue = this.stringRedisTemplate.opsForValue();
        String name = opsForValue.get("name");
        opsForValue.set("redisTest", "why");
        return R.ok().data("name",name);
    }
}

