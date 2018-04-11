package com.erze.yqj.moudle.voice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.czt.mp3recorder.RecordConstant;
import com.erze.yqj.R;
import com.erze.yqj.api.NetCallBack;
import com.erze.yqj.api.NetResponse;
import com.erze.yqj.entity.Music;
import com.erze.yqj.moudle.release.ReleaseVoiceActivity;
import com.erze.yqj.moudle.voice.Tool.Function.AudioFunction;
import com.erze.yqj.moudle.voice.Tool.Function.VoiceFunctionF6;
import com.erze.yqj.moudle.voice.Tool.Interface.ComposeAudioInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.DecodeOperateInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.VoicePlayerInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.VoiceRecorderOperateInterface;
import com.erze.yqj.moudle.voice.bgm.SelectorBgmActivity;
import com.erze.yqj.moudle.voice.ui.SpectrumView;
import com.erze.yqj.moudle.voice.utils.AppInfo;
import com.erze.yqj.moudle.voice.utils.DialogHelper;
import com.erze.yqj.moudle.voice.utils.MenuDialogGiveupRecordHelper;
import com.erze.yqj.moudle.voice.utils.MenuDialogListerner;
import com.erze.yqj.moudle.voice.utils.PermissionUtils;
import com.erze.yqj.moudle.voice.utils.RecorderAndPlayUtil;
import com.erze.yqj.moudle.voice.utils.String2TimeUtils;
import com.erze.yqj.moudle.voice.utils.Variable;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class Voice2Activity extends Activity
        implements VoicePlayerInterface, DecodeOperateInterface, ComposeAudioInterface,
        VoiceRecorderOperateInterface, NetCallBack {

    @BindView(R.id.musictime)
    TextView musictime;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.spectrumView)
    SpectrumView spectrumView;
    private String tag = "Voice2Activity";
    private final String PLAY_MUSIC_URL1 = "http://ws.stream.qqmusic.qq.com/";
    private final String PLAY_MUSIC_URL2 = ".m4a?fromtag=46";
    @BindView(R.id.voice_record_start)
    ImageView voiceRecordStart;
    @BindView(R.id.voice_selector_bgm)
    ImageView voiceSelectorBgm;
    @BindView(R.id.voice_play)
    ImageView voicePlay;
    @BindView(R.id.voice_bgm_tv)
    TextView voiceBgmTv;
    private final static int REQUESTMUSIC = 0x2001;
    private String className;
    private String event_title, eid;
    private static int TOTALTIME = 600;  //默认录音600s
    private int totalTime = TOTALTIME;  //默认录音600s
    private String2TimeUtils string2TimeUtils;
    Music.ResultBean.ListBean musicListBeen;
    private String tempVoicePcmUrl;
    private String musicFileUrl;
    private String decodeFileUrl;
    private String composeVoiceUrl;
    private boolean recordVoiceBegin;
    private boolean is2mp3 = true;
    private boolean recordComFinish = false;
    private File backgroudMusciFile;
    private static Voice2Activity instance;
    private String play_music_url, music_name, music_id, music_singer, fileNameCom;
    private int recordTime;
    private int actualRecordTime;
    private AudioManager audoManager;
    private RecorderAndPlayUtil recorderUtil;
    private int i;
    private double a;
    private Handler handler;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private Map<String, String> map = new ArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice2);
        ButterKnife.bind(this);
        instance = this;
        init();
    }

    private void init() {
        handler = new Handler();
        string2TimeUtils = new String2TimeUtils();
        PermissionGen.needPermission(this, 100, Manifest.permission.RECORD_AUDIO);
        className = getClass().getSimpleName();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTMUSIC:
                    Bundle b = data.getExtras(); //data为B中回传的Intent
                    musicListBeen = ((Music.ResultBean.ListBean) b.getSerializable("data"));
                    if (musicListBeen != null) {
                        play_music_url = musicListBeen.getMp3_url();
                        music_name = musicListBeen.getMp3_name();
                    }
                    LogUtils.e("play_music_url", play_music_url);
                    LogUtils.e("music_name", music_name);
                    initMusicData();
                    break;
                case 123:
                    Bundle c = data.getExtras(); //data为B中回传的Intent
                    music_name   = c.getString("music_name");
                    music_id     = c.getString("music_id");
                    music_singer = c.getString("music_singer");
                    play_music_url = PLAY_MUSIC_URL1 + music_id + PLAY_MUSIC_URL2;
                    initMusicData();
                    break;
            }
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void openContact() {
        initView();
        //  initData();
    }

    @PermissionFail(requestCode = 100)
    public void failContact() {
        finish();
        PermissionUtils.openSettingActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void initView() {
        audoManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        recorderUtil = new RecorderAndPlayUtil(this);
    }

    public void initMusicData() {
        backgroudMusciFile = new File(Variable.StorageMusicCachPath, music_name);
        LogUtils.e("backgroudMusciFile", backgroudMusciFile);
        if (backgroudMusciFile.exists()) {
            initMusicUI();
        } else {
            downloadFile(play_music_url, backgroudMusciFile.getAbsolutePath());
        }
    }

    private void deleteMusicUI() {
        recordTime = 0;
        fileNameCom = "";
        composeVoiceUrl = "";
        voiceBgmTv.setText("背景音乐");
        voiceRecordStart.setBackgroundResource(R.mipmap.voice_start);
        is2mp3 = true;
    }


    private void goRecordSuccessState() {
        recordVoiceBegin = false;
        recordComFinish = true;
        if (backgroudMusciFile != null && backgroudMusciFile.exists()) {
            compose();
        } else {
            stopRecording();
        }
    }

    private void stopRecording() {
        VoiceFunctionF6.PlayToggleVoice(VoiceFunctionF6.getRecorderPath(), instance);
    }

    private DialogHelper dialogHelper;

    private void compose() {
       /* composeProgressBar.setProgress(0);
        composeProgressBar.setVisibility(View.VISIBLE);*/
        voiceRecordStart.setEnabled(false);
        tempVoicePcmUrl = VoiceFunctionF6.getRecorderPath();
        LogUtils.e("tempVoicePcmUrl", tempVoicePcmUrl);
        LogUtils.e("decodeFileUrl", decodeFileUrl);
        LogUtils.e("composeVoiceUrl", composeVoiceUrl);
        if (audoManager.isWiredHeadsetOn()) { //true 带了耳机
            AudioFunction.BeginComposeAudio(tempVoicePcmUrl, decodeFileUrl, composeVoiceUrl, false,
                    RecordConstant.VoiceEarWeight, RecordConstant.VoiceEarBackgroundWeight,
                    0, this);
        } else {
            AudioFunction.BeginComposeAudio(tempVoicePcmUrl, decodeFileUrl, composeVoiceUrl, false,
                    RecordConstant.VoiceWeight, RecordConstant.VoiceBackgroundWeight,
                    0, this);
        }
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper(instance, 100);
            dialogHelper.showProgressDialog();
        }
    }

    @Override
    public void playVoiceBegin(long duration) {
        int pTotalTime = (int) duration / 1000;
       musictime.setText(string2TimeUtils.stringForTimeS(pTotalTime));
        if (backgroudMusciFile != null && backgroudMusciFile.exists()) {
            AudioFunction.DecodeMusicFile(musicFileUrl, decodeFileUrl, 0,
                    totalTime, instance);
        }
    }

    @Override
    public void playVoiceFail() {
        //无
    }

    @Override
    public void playVoiceStateChanged(long currentDuration) {
        if (recordComFinish) {
            if (currentDuration > 0) {
                int playtime = (int) (currentDuration / RecordConstant.OneSecond);
                musictime.setText(string2TimeUtils.stringForTimeS(playtime));
                LogUtils.e("musictime2", string2TimeUtils.stringForTimeS(playtime));
            }
        }

    }

    @Override
    public void playVoicePause() {
        //无
    }

    @Override
    public void playVoiceFinish() {
        if (recordComFinish) {
            voiceRecordStart.setImageResource(R.mipmap.voice_start);
        }
    }

    @Override
    public void recordVoiceBegin() {
        if (!recordVoiceBegin) {
            recordVoiceBegin = true;
            recordTime = 0;
            musictime.setText(string2TimeUtils.stringForTimeS(recordTime));
            LogUtils.e("musictime3", string2TimeUtils.stringForTimeS(recordTime));
            musictime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void recordVoiceStateChanged(int volume, long recordDuration) {
        if (recordDuration > 0) {
            recordTime = (int) (recordDuration / RecordConstant.OneSecond);
            int leftTime = totalTime - recordTime;
            if (!recordComFinish){
                musictime.setText(string2TimeUtils.stringForTimeS(recordTime));
            }
           LogUtils.e("musictime5", string2TimeUtils.stringForTimeS(recordTime));
            if (leftTime <= 0) {
                VoiceFunctionF6.StopRecordVoice(is2mp3);
            }
        }
    }

    @Override
    public void prepareGiveUpRecordVoice() {
        //无
    }

    @Override
    public void recoverRecordVoice() {
        //无
    }

    @Override
    public void giveUpRecordVoice() {
        //无
    }

    @Override
    public void recordVoiceFail() {
        if (recordVoiceBegin) {
            if (actualRecordTime != 0) {
                goRecordSuccessState();
            } else {
                goRecordFailState();
            }
        }
    }

    private void goRecordFailState() {
        recordVoiceBegin = false;
    }

    @Override
    public void recordVoiceFinish() {
        if (recordVoiceBegin) {
            actualRecordTime = recordTime;
            if (actualRecordTime == totalTime) {

                } else {
                    goRecordSuccessState();
                }


            } else {
                goRecordSuccessState();
            }


    }

    @Override
    public void updateComposeProgress(int composeProgress) {
        if (composeProgress == 10 || composeProgress == 20 ||
                composeProgress == 30 || composeProgress == 40 ||
                composeProgress == 50 || composeProgress == 60 ||
                composeProgress == 70 || composeProgress == 80 ||
                composeProgress == 90 || composeProgress == 100) {
            //    composeProgressBar.setProgress(composeProgress);
            dialogHelper.setProgress(composeProgress);
        }
    }

    @Override
    public void composeSuccess() {
        recordComFinish = true;
        if (AppInfo.isForeground(instance, getClass().getSimpleName())) {
            VoiceFunctionF6.PlayToggleVoice(composeVoiceUrl, this);
            voiceRecordStart.setImageResource(R.mipmap.voice_start);
            Toast.makeText(instance, "合成成功", Toast.LENGTH_SHORT).show();
        }
        if (dialogHelper != null) {
            dialogHelper.dismissProgressDialog();
            dialogHelper = null;
        }
    }

    @Override
    public void composeFail() {
        voiceRecordStart.setEnabled(true);
        //   composeProgressBar.setVisibility(View.GONE);
        Toast.makeText(instance, "合成失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDecodeProgress(int decodeProgress) {
        //无
    }

    @Override
    public void decodeSuccess() {
        //无
    }

    @Override
    public void decodeFail() {
        Toast.makeText(instance, "对不起，音频解码失败，请在设置意见反馈中提交您的机型。", Toast.LENGTH_SHORT).show();
        // composeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetNoStart(String id) {

    }

    @Override
    public void onNetStart(String id) {

    }

    @Override
    public void onNetEnd(String id, int type, NetResponse netResponse) {

    }

    @OnClick({R.id.voice_back_iv, R.id.voice_upload, R.id.voice_record_start, R.id.voice_selector_bgm, R.id.voice_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_back_iv:
                break;
            case R.id.voice_upload:
                vioceUpload();
                break;
            case R.id.voice_record_start:
                if (recordVoiceBegin) {
                    if (VoiceFunctionF6.isPauseRecordVoice(is2mp3)) {
                        VoiceFunctionF6.restartRecording(is2mp3);
                        voiceRecordStart.setImageResource(R.mipmap.voice_start);
                        if (backgroudMusciFile != null && backgroudMusciFile.exists()) {
                            VoiceFunctionF6.startPlayVoice();
                        }
                        handler.post(runnable);
                    } else {
                        VoiceFunctionF6.pauseRecordVoice(is2mp3);
                        voiceRecordStart.setImageResource(R.drawable.null_avatar);
                        if (backgroudMusciFile != null && backgroudMusciFile.exists()) {
                            VoiceFunctionF6.pauseVoice();
                        }
                        handler.removeCallbacks(runnable);
                    }
                } else {
                    if (!is2mp3) {
                        VoiceFunctionF6.PlayToggleVoice(backgroudMusciFile.getAbsolutePath(), this);
                    } else {
                        voiceBgmTv.setText("无背景音乐");
                    }
                    VoiceFunctionF6.StartRecordVoice(is2mp3, instance);
                    handler.post(runnable);
                }
                break;
            case R.id.voice_selector_bgm:
                deleteMusicUI();
                Intent intent = new Intent(this, SelectorBgmActivity.class);
                startActivityForResult(intent, REQUESTMUSIC);
                break;
            case R.id.voice_play:
                if (recordComFinish) {
                    if (recordVoiceBegin) {

                    } else {
                        if (VoiceFunctionF6.IsPlaying()) {
                            VoiceFunctionF6.pauseVoice();
                            voicePlay.setImageResource(R.drawable.record_voice_finsh);
                        } else {
                            VoiceFunctionF6.startPlayVoice();
                            voicePlay.setImageResource(R.drawable.null_avatar);
                        }
                    }
                } else {
                    goRecordSuccessState();
                }
                break;
        }
    }

    private void vioceUpload() {
        File file = new File(composeVoiceUrl);
        if (file.exists()){
            reset();
            Intent intent = new Intent(Voice2Activity.this, ReleaseVoiceActivity.class);
            intent.setAction(composeVoiceUrl);
            startActivity(intent);
        }else {
            Toast.makeText(instance, "您还没有录制", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
        if (handler!=null){
            handler.removeCallbacks(runnable);
            handler = null;
        }
        VoiceFunctionF6.StopRecordVoice(is2mp3);
        VoiceFunctionF6.StopVoice();
    }

    /***
     *  //音频波形图
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            spectrumView.addSpectrum(new Random().nextInt(160));
            handler.postDelayed(this, 100);
        }
    };

    /**
     * 下载歌曲
     *
     * @param url  歌曲Url
     * @param path 歌曲本地存放的绝对路径
     */
    private void downloadFile(String url, String path) {
        LogUtils.e("准备下载的URl", url);
        LogUtils.e("准备保存的本地路径", path);
        FileDownloader.setup(getApplicationContext());
        progress.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        voicePlay.setEnabled(false);
        voiceRecordStart.setEnabled(false);
        voiceSelectorBgm.setEnabled(false);
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                        a = soFarBytes * 1.0 / totalBytes;
                        i = (int) (a * 100);
                        progressText.setText(i + "%");
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        progress.setVisibility(View.GONE);
                        progressText.setVisibility(View.GONE);
                        voicePlay.setEnabled(true);
                        voiceRecordStart.setEnabled(true);
                        voiceSelectorBgm.setEnabled(true);
                        initMusicUI();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


    private void initMusicUI() {
        is2mp3 = false;
        musicFileUrl = backgroudMusciFile.getAbsolutePath();
        decodeFileUrl = Variable.StorageMusicPath + music_name + ".pcm";
        recordTime = 0;
        long t = System.currentTimeMillis() / 1000;
        fileNameCom = music_name + t + "cv.mp3";
        composeVoiceUrl = Variable.StorageMusicPath + fileNameCom;
        voiceBgmTv.setText(music_name);
//        VoiceFunctionF6.PlayToggleVoice(musicFileUrl, this);
//        VoiceFunctionF6.StopVoice();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
//            dialog();
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (recordVoiceBegin) {
            MenuDialogGiveupRecordHelper menuDialogGiveupRecordHelper = new MenuDialogGiveupRecordHelper(instance, new MenuDialogListerner() {
                @Override
                public void onSelected(int selected) {
                    switch (selected) {
                        case 0:
                            reset();
                            break;
                        case 1:
                            finish();
                            VoiceFunctionF6.GiveUpRecordVoice(true);
                            break;
                    }
                }
            });
            menuDialogGiveupRecordHelper.show(voiceRecordStart);
        } else {
            finish();
        }
    }

    private void reset() {
        actualRecordTime = 0;
        recordComFinish = false;
        recordVoiceBegin = false;
        VoiceFunctionF6.StopRecordVoice(is2mp3);
        VoiceFunctionF6.StopVoice();
        voicePlay.setImageResource(R.mipmap.voice_play);
        voiceRecordStart.setImageResource(R.mipmap.voice_start);
        musictime.setText("00:00");
        voiceSelectorBgm.setEnabled(true);
        voicePlay.setEnabled(true);
        voiceRecordStart.setEnabled(true);
//        et_content.setVisibility(View.VISIBLE);

    }


}
