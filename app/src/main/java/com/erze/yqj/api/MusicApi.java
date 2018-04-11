package com.erze.yqj.api;

import com.erze.yqj.entity.Music;
import com.erze.yqj.entity.MusicBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by 1227228155@qq.com on 2017/7/29.
 */

public interface MusicApi {
    /**
     * 根据歌曲名字或者歌手 查询歌曲列表
     *  查询网络歌曲
     * @return
     */
    @GET
    Observable<MusicBean> getNetMusic(@Url String url);
    /**
     * 获取歌曲分类列表，本地上传到金山云的歌曲
     * @return
     */
    @FormUrlEncoded
    @POST("userMusic")
    Observable<Music>getMusic(@FieldMap Map<String, String> map);

    /**
     * 下载歌曲
     * @param fileUrl
     * @return
     */
    @GET
    Call<ResponseBody> downloadMusic(@Url String fileUrl);
}
