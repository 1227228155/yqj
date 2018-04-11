package com.erze.yqj.moudle.shortvideo;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.KS3Bean;
import com.ksyun.media.shortvideo.utils.KS3ClientWrap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

/**
 * get ks3 token on 17/4/13.
 */

public class KS3TokenTask {
    private static String TAG = "KS3TokenTask";

    //Get ks3 Token
    private static final String TYPE = "contType";
    private static final String MD5 = "contMd5";
    private static final String VERB = "httpVerb";
    private static final String RES = "res";
    private static final String HEADERS = "headers";
    private static final String DATE = "date";
    private RequestQueue mRequestQueue;
    private Context mContext;
    KS3Bean ks3Bean;
    String token,serverDate;
    public KS3TokenTask(Context context) {
        mContext = context;
    }

    /**
     * 向APPServer请求token
     *
     * @param httpMethod
     * @param contentType
     * @param date
     * @param contentMD5
     * @param resource
     * @param headers
     * @return
     */
    public KS3ClientWrap.KS3AuthInfo requsetTokenToAppServer(String httpMethod, String contentType, String
            date, String
            contentMD5, String resource, String headers) {
        Map<String, String> param = new HashMap<>();
        MyAsyncTask asyncTask = new MyAsyncTask();
        param.put(VERB, httpMethod);
        param.put(MD5, contentMD5);
        param.put(TYPE, contentType);
        param.put(HEADERS, headers);
        param.put(DATE, date);
        param.put(RES, resource);
        Response<KS3Bean> ks3 = asyncTask.doInBackground(param);
        LogUtils.e("a",httpMethod);
        LogUtils.e("a1",contentType);
        LogUtils.e("a2",date);
        LogUtils.e("a3",contentMD5);
        LogUtils.e("a4",resource);
        LogUtils.e("a5",headers);
        LogUtils.e("ks3",ks3.body().toString());
        token = ks3.body().getAuthorization();
        serverDate = ks3.body().getDate();
        LogUtils.e("toke",token);
        LogUtils.e("date",serverDate);
        return new KS3ClientWrap.KS3AuthInfo(token, serverDate);

    }


  /*  //Get Image Token
    private Response<KS3Bean> doGetToken(String httpMethod, String contentType, String contentMD5, String
            date, String resource, String headers) throws IOException {

        return NetDao.getInstance().getKSVSApi().getKS3(param).execute() ;
    }
*/
   public class MyAsyncTask extends AsyncTask<Object, Object, Response<KS3Bean>> {

       @Override
       protected Response<KS3Bean> doInBackground(Object... params) {
           try {
               return NetDao.getInstance().getKSVSApi().getKS3((Map<String, String>) params[0]).execute();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }

  }

}
