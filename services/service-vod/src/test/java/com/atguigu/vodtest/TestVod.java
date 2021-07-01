package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

/**
 * @author li
 * @create 2021-05-03 22:20
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
        //1.根据视频id，获取播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tQ3z3fTRrmH9AwB2HeD", "vL2PkLwnwVFL2Vj1Kpp1f3l3TAykUO");

        //创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request里设置视频id
        request.setVideoId("ec74001de24744728c89da4bfcdd75d7");

        //调用初始化对象里的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        //输出请求结果
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }
}

