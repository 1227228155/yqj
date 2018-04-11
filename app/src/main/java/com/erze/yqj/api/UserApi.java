package com.erze.yqj.api;

import com.erze.yqj.entity.RegisterBean;
import com.erze.yqj.entity.RegisterPersonalDataBean;
import com.erze.yqj.entity.UserBean;
import com.erze.yqj.entity.UserDataBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public interface UserApi {
 /**
     * 注册用户
     *
     * @return
     */
    @FormUrlEncoded
    @POST("veri")
    Observable<RegisterBean> registerUser(@Field("mobile") String userPhone);

    /**
     * 注册用户信息
     * @param
     * @return
     */
    @Multipart
    @POST("userInfoInCom")
    Observable<RegisterPersonalDataBean> registerPersonalData(@Part List<MultipartBody.Part> partList);
   /**
    * 获取观看其他用户信息
    * @param
    * @return
    */
   @FormUrlEncoded
   @POST("userCenter")
   Observable<UserDataBean> getUserData(@FieldMap Map<String, String> map);

    /**
     * 获取用户自己信息
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("userCenter")
    Observable<UserBean> getUser(@FieldMap Map<String, String> map);
}
