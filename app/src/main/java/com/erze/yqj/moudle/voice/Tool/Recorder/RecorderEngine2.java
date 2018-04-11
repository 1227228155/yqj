package com.erze.yqj.moudle.voice.Tool.Recorder;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.czt.mp3recorder.RecordConstant;
import com.erze.yqj.MyApplication;
import com.erze.yqj.moudle.voice.Tool.Function.CommonFunction;
import com.erze.yqj.moudle.voice.Tool.Function.FileFunction;
import com.erze.yqj.moudle.voice.Tool.Function.LogFunction;
import com.erze.yqj.moudle.voice.Tool.Interface.VoiceRecorderOperateInterface;

import java.io.File;
import java.io.IOException;

/**
 * Created by 1227228155@qq.com on 2017/9/5.
 */

public class RecorderEngine2 {
    private boolean recording;

    private final int sampleDuration = 500;// 间隔取样时间

    private long recordStartTime;
    private long recordDuration;

    private String recordFileUrl;

    private VoiceRecorderOperateInterface voiceRecorderInterface;

    private AudioManager audioManager;

    private Handler handler;

    private static PCMRecorder recorder;

    //    private static NativeRecorder recorder;

    private static RecorderEngine2 instance;

    private RecorderEngine2() {
        audioManager = (AudioManager) MyApplication.getInstance()
                .getSystemService(Context.AUDIO_SERVICE);

        handler = new Handler();
        recorder = new PCMRecorder();
    }

    public static RecorderEngine2 getInstance() {
        if (instance == null) {
            synchronized (RecorderEngine2.class) {
                if (instance == null) {
                    instance = new RecorderEngine2();
                }
            }
        }

        return instance;
    }

    public synchronized static void Destroy() {
        if (instance != null) {
            recorder.release();
        }

        instance = null;
    }

    public boolean IsRecording() {
        return recording;
    }

    private boolean startRecordVoice(String recordFileUrl) {
        if (CommonFunction.isEmpty(recordFileUrl)) {
            CommonFunction.showToast("无法录制语音，请检查您的手机存储", "VoiceRecorder");
            return false;
        }

        File recordFile = new File(recordFileUrl);

        if (!recordFile.exists()) {
            try {
                recordFile.createNewFile();
            } catch (IOException e) {
                LogFunction.error("建立语音文件异常:" + recordFileUrl, e);

                CommonFunction.showToast("建立语音文件异常", "VoiceRecorder");
                return false;
            }
        }

        return recorder.startRecordVoice(recordFileUrl);
    }

    public void startRecordVoice(String recordFileUrl,
                                 VoiceRecorderOperateInterface voiceRecorderOperateInterface) {
        stopRecordVoice();

        recordDuration = 0;

        recording = startRecordVoice(recordFileUrl);

        if (recording) {
            this.recordFileUrl = recordFileUrl;
            this.voiceRecorderInterface = voiceRecorderOperateInterface;

            recordStartTime = System.currentTimeMillis();

            updateMicStatus();

            if (voiceRecorderOperateInterface != null) {
                voiceRecorderOperateInterface.recordVoiceBegin();
            }
        } else {
            CommonFunction.showToast("录音失败", "VoiceRecorder");

            if (voiceRecorderOperateInterface != null) {
                voiceRecorderOperateInterface.recordVoiceFail();
            }
        }
    }

    public void stopRecordVoice() {
        if (recording) {
            boolean recordVoiceSuccess = recorder.stopRecordVoice();
            long recordDuration = System.currentTimeMillis() - recordStartTime;

            recording = false;

            if (recordDuration < RecordConstant.OneSecond) {
                recordVoiceSuccess = false;
            }

            if (!recordVoiceSuccess) {
                CommonFunction.showToast("录音太短", "VoiceRecorder");

                if (voiceRecorderInterface != null) {
                    voiceRecorderInterface.recordVoiceFail();
                }

                FileFunction.DeleteFile(recordFileUrl);
                return;
            }

            if (voiceRecorderInterface != null) {
                voiceRecorderInterface.recordVoiceFinish();
            }
        }
    }

    public void giveUpRecordVoice() {
        if (recording) {
            boolean stopRecordSuccess = recorder.stopRecordVoice();

            recording = false;

            if (stopRecordSuccess) {
                if (voiceRecorderInterface != null) {
                    //   voiceRecorderInterface.recordVoiceFinish();
                }
            } else {
                if (voiceRecorderInterface != null) {
                    voiceRecorderInterface.recordVoiceFail();
                }
            }

            FileFunction.DeleteFile(recordFileUrl);

            if (voiceRecorderInterface != null) {
                voiceRecorderInterface.giveUpRecordVoice();
            }
        }
    }

//    public void setPause(boolean pause) {
//        if (recording) {
//            recorder.setPause(pause);
//        }
//    }
    /**
     * 暂停
     */
    public void pauseRecording() {
        if (recording) {
            recorder.setPause(true);
        }

    }


    public boolean isPause(){
        if (recording) {
            return recorder.isPause();
        }
        return false;
    }

    /**
     * 暂停
     */
    public void restartRecording() {
        if (recording) {
            recorder.setPause(false);
        }
    }



    public void prepareGiveUpRecordVoice(boolean fromHand) {
        if (voiceRecorderInterface != null) {
            voiceRecorderInterface.prepareGiveUpRecordVoice();
        }
    }

    public void recoverRecordVoice(boolean fromHand) {
        if (voiceRecorderInterface != null) {
            voiceRecorderInterface.recoverRecordVoice();
        }
    }

    public void recordVoiceStateChanged(int volume) {
        if (voiceRecorderInterface != null) {
            voiceRecorderInterface.recordVoiceStateChanged(volume, recordDuration);
        }
    }


    private void updateMicStatus() {
        int volume = recorder.getVolume();
        recordVoiceStateChanged(volume);
        handler.postDelayed(updateMicStatusThread, sampleDuration);
    }

    private Runnable updateMicStatusThread = new Runnable() {
        public void run() {
            if (recording) {
                // 判断是否超时
                if (!recorder.isPause()){
                    recordDuration += sampleDuration;
                }
                updateMicStatus();
            }
        }
    };
}
