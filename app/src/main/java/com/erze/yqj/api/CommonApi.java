package com.erze.yqj.api;

import com.erze.yqj.entity.BgBean;
import com.erze.yqj.entity.Code;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.entity.FindBgmBean;
import com.erze.yqj.entity.FindBgmPlayBean;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.entity.HotBean;
import com.erze.yqj.entity.HotVideoBean;
import com.erze.yqj.entity.RegisterBean;
import com.erze.yqj.entity.ReplyBean;
import com.erze.yqj.entity.SendComment;
import com.erze.yqj.entity.SendReply;
import com.erze.yqj.entity.ThirdReply;
import com.erze.yqj.entity.UpdateBean;
import com.erze.yqj.entity.UserWorksBean;
import com.erze.yqj.entity.Videobean;
import com.erze.yqj.entity.WorldBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public interface CommonApi {

    /**
     * 注册用户
     *
     * @return
     */
    @FormUrlEncoded
    @POST("veri")
    Observable<RegisterBean> registerUser(@Field("mobile") String userPhone);


    /**
     * 根据歌曲名字或者歌手搜索歌曲列表
     * @param name
     * @return
     */
   @GET("audioInfo")
    Observable<FindBgmBean> findBgm(@Query("field") String name);

    /**
     * 根据歌曲信息的Hash请求地址
     * @param hash
     * @return
     */
    @GET("hashAudio")
    Observable<FindBgmPlayBean> findBgmPlay(@Query("hash") String hash);


    /**
     *
     * @param   map
     * @return  获得世界fragment推荐页面的数据
     */
    @FormUrlEncoded
    @POST("topCarousel")
    Observable<WorldBean> getWorld(@FieldMap Map<String, String> map);
    /**
     *
     * @param   map
     * @return  获得世界fragment世界页面的数据
     */
    @FormUrlEncoded
    @POST("topCarousel")
    Observable<Videobean> getWorld2(@FieldMap Map<String, String> map);

   /**
     * 关注和取消关注
     * @return
     */
    @FormUrlEncoded
    @POST("userAttenStatus")
    Observable<CodeBean>getFollowStatus(@FieldMap Map<String, String> map);

    /**
     * 获得关注页面信息
     * @return
     */
    @FormUrlEncoded
    @POST("userAtten")
    Observable<FollowBean>getFollow(@FieldMap Map<String, String> map);

    /**
     * 获得关注列表或者粉丝列表
     * @return
     */
    @FormUrlEncoded
    @POST("userAttenInfo")
    Observable<FansListBean>getFollowList(@FieldMap Map<String, String> map);

    /**
     * 点赞  观看  分享  统计接口
     * @return
     */
    @FormUrlEncoded
    @POST("videoTimes")
    Observable<Code>getVideoTimes(@FieldMap Map<String, String> map);

    /**
     * 用户添加收藏接口
     * @return
     */
    @FormUrlEncoded
    @POST("userCollection")
    Observable<Code>getCollection(@FieldMap Map<String, String> map);
 /**
  * 用户查看收藏页面
  * @return
  */
    @FormUrlEncoded
    @POST("userCollectionInfo")
    Observable<FollowBean>getCollectionInfo(@FieldMap Map<String, String> map);
/**
 * 获取用户评论数据
 * @return
 */
    @FormUrlEncoded
    @POST("userWatchVideo")
    Observable<CommentBean>getComment(@FieldMap Map<String, String> map);

    /**
     * 获取用户评论二级回复数据
     * @return
     */
    @FormUrlEncoded
    @POST("userWatchVideo")
    Observable<ReplyBean>getReply(@FieldMap Map<String, String> map);
    /**
     * 用户评论
     * @return
     */
    @FormUrlEncoded
    @POST("userComment")
    Observable<SendComment>sendComment(@FieldMap Map<String, String> map);
    /**
     * 用户回复
     * @return
     */
    @FormUrlEncoded
    @POST("userComment")
    Observable<SendReply>sendReply(@FieldMap Map<String, String> map);
    /**
     * 用户二级回复
     * @return
     */
    @FormUrlEncoded
    @POST("userComment")
    Observable<ThirdReply>sendReply2(@FieldMap Map<String, String> map);
    /**
     * 更换用户绑定手机号
     * @return
     */
    @FormUrlEncoded
    @POST("userMobChange")
    Observable<CodeBean>updatePhone(@FieldMap Map<String, String> m);


    /**
     * 举报接口
     * @return
     */
    @Multipart
    @POST("report")
    Observable<CodeBean>report(@Part List<MultipartBody.Part> partList);
    /**
     * 上传接口
     * @return
     */
    @FormUrlEncoded
    @POST("videoAddress")
    Observable<UpdateBean>update(@FieldMap Map<String, String> m);
    /**
     * 上传背景图片接口
     * @return
     */
    @Multipart
    @POST("userWall")
    Observable<BgBean>uploadBg(@Part List<MultipartBody.Part> partList);

    /**
     * 人气榜接口
     * @return
     */
    @FormUrlEncoded
    @POST("vidoePopHot")
    Observable<HotBean>getHot(@FieldMap Map<String, String> m);
    /**
     * 人气视频榜接口
     * @return
     */
    @FormUrlEncoded
    @POST("vidoePopHot")
    Observable<HotVideoBean>getHotVideo(@FieldMap Map<String, String> m);
    /**
     * 个人作品接口
     * @return
     */
    @FormUrlEncoded
    @POST("uservideo")
    Observable<UserWorksBean>getUserWorks(@FieldMap Map<String, String> m);
}
