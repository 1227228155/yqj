package com.erze.yqj.moudle.voice.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.czt.mp3recorder.MP3Recorder;
import com.shuyu.waveview.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 封装音频录音工具类
 *
 * @author zhouyou
 */
public class RecorderAndPlayUtil {

    private String mPlayingPath = null;
    private MP3Recorder mRecorder;
    private Context context;
    private String filePath;
    private String path = "/sdcard/yqj" + "/";
    public RecorderAndPlayUtil(Context context) {
        this.context = context;
        filePath = path+ System.currentTimeMillis() + ".mp3";
        //mRecorder = new MP3Recorder(new File(filePath));
    }

    /**
     * 开始录音
     */
    public void startRecording() {

        mRecorder.setErrorHandler(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MP3Recorder.ERROR_TYPE) {
                    Toast.makeText(context, "没有麦克风权限", Toast.LENGTH_SHORT).show();
                    resolveError();
                }
            }
        });

        try {
            mRecorder.startRecordVoice(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "录音出现异常", Toast.LENGTH_SHORT).show();
            resolveError();
            return;
        }
    }

    /**
     * 停止录音
     */
    public void stopRecording() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
//            audioWave.stopView();
        }
    }

    /**
     * 录音异常
     */
    private void resolveError() {
//        resolveNormalUI();
        FileUtils.deleteFile(filePath);
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
//            audioWave.stopView();
        }
    }


    /**
     * 暂停
     */
    public void pauseRecording() {
        if (mRecorder != null && mRecorder.isRecording()) {
//            if (mRecorder.isPause()) {
            mRecorder.setPause(false);
//            } else {
//                mRecorder.setPause(true);
//            }

        }

    }


    public boolean isPause(){

        if (mRecorder != null && mRecorder.isRecording()) {
            //    LogUtils.LOGE("1",String.valueOf(mRecorder.isPause()));
            return mRecorder.isPause();

        }
        return false;
    }

    /**
     * 暂停
     */
    public void restartRecording() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(true);
        }
    }



    public void release() {
        stopRecording();

    }

    /**
     * 获取录音路径
     *
     * @return
     */
    public String getRecorderPath() {
        return filePath;
    }

    /**
     * 获取录音类实例
     *
     * @return
     */
    public MP3Recorder getRecorder() {
        return mRecorder;
    }

    /**
     * 获取分贝值
     *
     * @return
     */
    public int getVolume() {
        return mRecorder.getVolume();
    }
}
