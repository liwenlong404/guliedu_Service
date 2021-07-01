package com.atguigu.edu.serviceedu.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.commentvo.UcenterMemberCom;
import com.atguigu.edu.serviceedu.client.UcenterClient;
import com.atguigu.edu.serviceedu.entity.EduComment;
import com.atguigu.edu.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-06-27
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}/{courseId}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                @PathVariable    String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

        UcenterMemberCom ucenterInfo = ucenterClient.getInfoUc(memberId);

        System.out.println(ucenterInfo);
        System.out.println(ucenterInfo.getAvatar());
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());

        commentService.save(comment);
        return R.ok();
    }
}

