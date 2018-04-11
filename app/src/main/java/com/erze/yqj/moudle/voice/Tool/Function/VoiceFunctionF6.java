package com.erze.yqj.moudle.voice.Tool.Function;

import com.erze.yqj.moudle.voice.Tool.Interface.VoicePlayerInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.VoiceRecorderOperateInterface;
import com.erze.yqj.moudle.voice.Tool.Player.VoicePlayerEngine;
import com.erze.yqj.moudle.voice.Tool.Recorder.Mp3RecorderEngine;
import com.erze.yqj.moudle.voice.Tool.Recorder.RecorderEngine;
import com.erze.yqj.moudle.voice.utils.Variable;

/**
 * Created by 1227228155@qq.com on 2017/9/5.
 */

public class VoiceFunctionF6 {
    private static String filePath;
    private static String tmpName;

    public static boolean IsRecordingVoice() {
        return RecorderEngine.getInstance().IsRecording();
    }

    public synchronized static String StartRecordVoice(boolean is2mp3, VoiceRecorderOperateInterface voiceRecorderOperateInterface) {
        tmpName = System.currentTimeMillis()+"ly";

        if (is2mp3) {
            filePath =  Variable.StorageMusicCachPath +tmpName + ".mp3";
            Mp3RecorderEngine.getInstance()
                    .startRecordVoice(filePath, voiceRecorderOperateInterface);
            //    LogUtils.LOGE("path",filePath);
        } else {
            filePath = Variable.StorageLyricCachPath + tmpName + ".pcm";
            RecorderEngine.getInstance()
                    .startRecordVoice(filePath, voiceRecorderOperateInterface);
            //   LogUtils.LOGE("path",filePath);
        }
        return tmpName;

    }

    /**
     * 获取录音路径
     *
     * @return
     */
//     public static String getRecorderMp3Path() {
//        return mp3FilePath;
//    }
    public static String getRecorderPath() {
        return filePath;
    }

    public static String getRecorderName() {
        return tmpName;
    }

    public static void StopRecordVoice(boolean is2mp3) {
        if (is2mp3) {
            Mp3RecorderEngine.getInstance().stopRecordVoice();
        } else {
            RecorderEngine.getInstance().stopRecordVoice();
        }
    }

    public static boolean isPauseRecordVoice(boolean is2mp3) {
        if (is2mp3) {
            return Mp3RecorderEngine.getInstance().isPause();
        } else {
            return RecorderEngine.getInstance().isPause();
        }

    }

    public static void pauseRecordVoice(boolean is2mp3) {
        if (is2mp3) {
            Mp3RecorderEngine.getInstance().pauseRecording();
        } else {
            RecorderEngine.getInstance().pauseRecording();
        }
    }

    public static void restartRecording(boolean is2mp3) {
        if (is2mp3) {
            Mp3RecorderEngine.getInstance().restartRecording();
        } else {

            RecorderEngine.getInstance().restartRecording();
        }

    }

    public synchronized static void PrepareGiveUpRecordVoice(boolean fromHand) {
        RecorderEngine.getInstance().prepareGiveUpRecordVoice(fromHand);
    }

    public synchronized static void RecoverRecordVoice(boolean fromHand) {
        RecorderEngine.getInstance().recoverRecordVoice(fromHand);
    }

    public synchronized static void GiveUpRecordVoice(boolean fromHand) {

        if (fromHand) {
            Mp3RecorderEngine.getInstance().giveUpRecordVoice();
        } else {

            RecorderEngine.getInstance().giveUpRecordVoice();
        }
    }

    public synchronized static String getPlayingUrl() {
        return VoicePlayerEngine.getInstance().getPlayingUrl();
    }

    public synchronized static boolean IsPlaying() {
        return VoicePlayerEngine.getInstance().isPlaying();
    }

    private synchronized static boolean IsPlayVoice(String fileUrl) {
        if (CommonFunction.isEmpty(fileUrl)) {
            return false;
        }

        return getPlayingUrl().equals(fileUrl);
    }

    /**
     * @param fileUrl 是否在播
     * @return
     */
    public synchronized static boolean IsPlayingVoice(String fileUrl) {
        if (IsPlayVoice(fileUrl)) {
            return VoicePlayerEngine.getInstance().isPlaying();
        } else {
            return false;
        }
    }

    /**
     * @param fileUrl 是否在播
     * @return
     */
//    public synchronized static int getVoiceDuration(String fileUrl) {
////        if (IsPlayVoice(fileUrl)) {
//            return VoicePlayerEngine.getInstance().getDuration(fileUrl);
////        } else {
////            return false;
////        }
//    }
    public synchronized static void PlayToggleVoice(String fileUrl,
                                                    VoicePlayerInterface voicePlayerInterface) {
        if (IsPlayVoice(fileUrl)) {
            VoicePlayerEngine.getInstance().stopVoice();
        } else {
            //  VoicePlayerEngine.getInstance().stopVoice();
//            VoicePlayerEngine.getInstance()
//                    .playVoice(fileUrl, voicePlayerInterface);
        }

        VoicePlayerEngine.getInstance()
                .playVoice(fileUrl, voicePlayerInterface);
    }

    public synchronized static void PlayToggleVoice(String fileUrl,
                                                    VoicePlayerInterface voicePlayerInterface, int time) {
        if (IsPlayVoice(fileUrl)) {
            VoicePlayerEngine.getInstance().stopVoice();
        } else {
            VoicePlayerEngine.getInstance()
                    .playVoice(fileUrl, voicePlayerInterface, time);
        }
    }

    public synchronized static void StopVoice() {
        VoicePlayerEngine.getInstance().stopVoice();
    }

    public synchronized static void StopVoice(String fileUrl) {
        if (getPlayingUrl().equals(fileUrl)) {
            VoicePlayerEngine.getInstance().stopVoice();
        }
    }

    public synchronized static int pauseVoice(String fileUrl) {
        if (getPlayingUrl().equals(fileUrl)) {
            return VoicePlayerEngine.getInstance().pauseVoice();
        }
        return 0;
    }

    public synchronized static void pauseVoice() {
        VoicePlayerEngine.getInstance().pauseVoice();
    }


    public synchronized static int startPlayVoice() {

        return VoicePlayerEngine.getInstance().restartVoice();

    }
//    public synchronized static void seekTo(int time) {
//        VoicePlayerEngine.getInstance().seekTo(time);
//    }
}
