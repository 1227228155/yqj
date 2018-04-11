package com.erze.yqj.moudle.main.popup;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.erze.yqj.R;
import com.erze.yqj.moudle.shortvideo.FileImportActivity;
import com.erze.yqj.moudle.shortvideo.HttpRequestTask;
import com.erze.yqj.moudle.shortvideo.RecordActivity;
import com.erze.yqj.utils.AutoUtils;
import com.ksyun.media.shortvideo.utils.AuthInfoManager;
import com.ksyun.media.streamer.encoder.VideoEncodeFormat;
import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.kit.StreamerConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 1227228155@qq.com on 2017/6/30.
 */

public class PopupShotActivity extends Activity {
    ShortVideoConfig mShortVideoConfig = new ShortVideoConfig();
    private static String TAG = "ShortVideoActivity";
    public static String AUTH_SERVER_URI = "http://ksvs-demo.ks-live.com:8321/Auth";//the uri of your appServer
    //view
    private Button mAuthButton;    //SDK鉴权
    private Button mRecordButton;  //进入录制短视频功能
    private Button mImportFileButton; //导入本地视频用于进入短视频编辑

    private ButtonObserver mButtonObserver;
    private Handler mMainHandler;

    //config params
    //auth
    private HttpRequestTask mAuthTask;  //SDK鉴权异步任务
    private HttpRequestTask.HttpResponseListener mAuthResponse;
    private static int MAX_RETRY_COUNT = 3;  //若AKSK请求失败尝试3次
    private int mRetryCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_shot);
        AutoUtils.auto(this);
        initVideoConfig();
        RecordActivity.startActivity(getApplicationContext(),
                mShortVideoConfig.fps, mShortVideoConfig.videoBitrate,
                mShortVideoConfig.audioBitrate, mShortVideoConfig.resolution, mShortVideoConfig.encodeType,
                mShortVideoConfig.encodeMethod, mShortVideoConfig.encodeProfile);
      //  finish();
        mAuthButton = (Button) findViewById(R.id.auth);
        mRecordButton = (Button) findViewById(R.id.record);
        mImportFileButton = (Button) findViewById(R.id.import_file);

        mButtonObserver = new ButtonObserver();
        mAuthButton.setOnClickListener(mButtonObserver);
        mRecordButton.setOnClickListener(mButtonObserver);
        mImportFileButton.setOnClickListener(mButtonObserver);

        mMainHandler = new Handler();
    }

    /**
     *  短视频录制之前的配置信息
     */
    private void initVideoConfig() {
        mShortVideoConfig.fps = 40;
        mShortVideoConfig.audioBitrate = 1000;
        mShortVideoConfig.videoBitrate = 70;
        mShortVideoConfig.encodeMethod = StreamerConstants.ENCODE_METHOD_SOFTWARE;
        mShortVideoConfig.encodeProfile = VideoEncodeFormat.ENCODE_PROFILE_HIGH_PERFORMANCE;
        mShortVideoConfig.encodeType = AVConst.CODEC_ID_HEVC;
        mShortVideoConfig.resolution = StreamerConstants.VIDEO_RESOLUTION_720P;

    }

    public class ShortVideoConfig {

        public int fps;//采集帧率
        public int resolution;//分辨率 StreamerConstants.VIDEO_RESOLUTION_720P 360  480  540
        public int videoBitrate;// 视频码率
        public int audioBitrate; // 音频码率
        public int encodeType;//编码类型  AVConst.CODEC_ID_HEVC H265 或者AVConst.CODEC_ID_AVC H264
        public int encodeMethod;//编码方式  硬编or软编  StreamerConstants.ENCODE_METHOD_SOFTWARE
        public int encodeProfile;//编码简况   低功耗 平衡 or 高性能    VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }

        if (mAuthTask != null) {
            mAuthTask.cancel(true);
            mAuthTask.release();
            mAuthTask = null;
        }

        AuthInfoManager.getInstance().removeAuthResultListener(mCheckAuthResultListener);
    }

    private class ButtonObserver implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.auth:
                    mRetryCount = 0;
                    onAuthClick();
                    break;
                case R.id.record:
                    onRecordClick();
                    break;
                case R.id.import_file:
                    onImportFileClick();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * SDK鉴权
     */
    private void onAuthClick() {
        mAuthButton.setEnabled(false);

        if (mAuthResponse == null) {
            mAuthResponse = new HttpRequestTask.HttpResponseListener() {
                @Override
                public void onHttpResponse(int responseCode, String response) {
                    //params response
                    boolean authResult = false;
                    if (responseCode == 200) {
                        try {
                            JSONObject temp = new JSONObject(response);
                            JSONObject data = temp.getJSONObject("Data");
                            int result = data.getInt("RetCode");
                            if (result == 0) {
                                String authInfo = data.getString("Authorization");
                                String date = data.getString("x-amz-date");
                                //初始化鉴权信息
                                AuthInfoManager.getInstance().setAuthInfo(getApplicationContext(),
                                        authInfo, date);
                                //添加鉴权结果回调接口(不是必须)
                                AuthInfoManager.getInstance().addAuthResultListener(mCheckAuthResultListener);
                                //开始向KSServer申请鉴权
                                AuthInfoManager.getInstance().checkAuth();
                                authResult = true;
                            } else {
                                Log.e(TAG, "get auth failed from app server RetCode:" + result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "get auth failed from app server json parse failed");
                        }
                    } else {
                        Log.e(TAG, "get auth failed from app server responseCode:" + responseCode);
                    }

                    final boolean finalAuthResult = authResult;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!finalAuthResult) {

                                //鉴权失败，尝试3次
                                if(mRetryCount < MAX_RETRY_COUNT) {
                                    mRetryCount++;
                                    onAuthClick();
                                } else {
                                    mAuthButton.setEnabled(true);
                                }
                            } else {
                                mAuthButton.setEnabled(true);
                            }
                        }
                    });
                }
            };
        }

        if (mAuthTask != null && mAuthTask.getStatus() != AsyncTask.Status.FINISHED) {
            mAuthTask.cancel(true);
            mAuthTask = null;
        }
        //开启异步任务，向AppServer请求鉴权信息
        mAuthTask = new HttpRequestTask(mAuthResponse);
        String url = AUTH_SERVER_URI + "?Pkg=" + getApplicationContext().getPackageName();
        Log.d(TAG, "request auth:" + url);
        mAuthTask.execute(url);
    }

    private AuthInfoManager.CheckAuthResultListener mCheckAuthResultListener = new AuthInfoManager
            .CheckAuthResultListener() {
        @Override
        public void onAuthResult(int result) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    AuthInfoManager.getInstance().removeAuthResultListener(mCheckAuthResultListener);
                    if (AuthInfoManager.getInstance().getAuthState()) {

                    } else {

                    }
                }
            });
        }
    };

    private void onRecordClick() {
      /*  //params config
        final ShortVideoConfigDialog configDialog = new ShortVideoConfigDialog(this,
                ShortVideoConfigDialog.SHORTVIDEOCONFIG_TYPE_RECORD);
        configDialog.setCancelable(false);
        configDialog.show();
        configDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ShortVideoConfigDialog.ShortVideoConfig config = configDialog.getShortVideoConfig();
                if (config != null) {*/
          /*
                    RecordActivity.startActivity(getApplicationContext(),
                            config.fps, config.videoBitrate,
                            config.audioBitrate, config.resolution, config.encodeType,
                            config.encodeMethod, config.encodeProfile);*/
                }

            //}
      //  });
   // }

    private void onImportFileClick() {
        FileImportActivity.startActivity(getApplicationContext());
    }
}
