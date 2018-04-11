package com.erze.yqj.api;

import com.erze.yqj.entity.KS3Bean;
import com.erze.yqj.entity.KSYSDKBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 1227228155@qq.com on 2017/7/1.
 */

public interface KSVSApi {
    /**
     * 向自己服务器请求 用来SDK鉴权的值
     * @param pkg 包名
     * @return
     */
    @FormUrlEncoded
    @POST("yunsdk")
    Observable<KSYSDKBean> getSDK(@Field("pkg") String pkg);

    /**
     * KS3鉴权
     * @param map 视频合成成功会返回六个参数
     * @return  向自己服务器发起KS3鉴权
     */
    @FormUrlEncoded
    @POST("kssdk")
    Call<KS3Bean> getKS3(@FieldMap Map<String, String> map);

    /**
     * 上传成功时给自己服务器视频播放地址
     * @param objKey  上传时的 包名+时间戳
     * @return
     */
    @FormUrlEncoded
    @POST("one")
    Call<ResponseBody> getTest(@Field("objKey") String objKey);
}
