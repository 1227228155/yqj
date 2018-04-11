package com.erze.yqj.moudle.voice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.czt.mp3recorder.RecordConstant;
import com.erze.yqj.R;
import com.erze.yqj.entity.ComposeVoice;
import com.erze.yqj.entity.Music;
import com.erze.yqj.moudle.voice.Tool.Function.AudioFunction;
import com.erze.yqj.moudle.voice.Tool.Function.CommonFunction;
import com.erze.yqj.moudle.voice.Tool.Function.VoiceFunctionF2;
import com.erze.yqj.moudle.voice.Tool.Interface.ComposeAudioInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.DecodeOperateInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.VoicePlayerInterface;
import com.erze.yqj.moudle.voice.Tool.Interface.VoiceRecorderOperateInterface;
import com.erze.yqj.moudle.voice.Tool.Recorder.RecorderEngine;
import com.erze.yqj.moudle.voice.bgm.SelectorBgmActivity;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.moudle.voice.ui.SpectrumView;
import com.erze.yqj.moudle.voice.utils.AppInfo;
import com.erze.yqj.moudle.voice.utils.PermissiUtils;
import com.erze.yqj.moudle.voice.utils.RecorderAndPlayUtil;
import com.erze.yqj.moudle.voice.utils.String2TimeUtils;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.erze.yqj.R.id.musictime;


public class VoiceActivity extends BaseFrameActivity<VoicePresenter, VoiceModel> implements VoicePlayerInterface, DecodeOperateInterface, ComposeAudioInterface,
        VoiceRecorderOperateInterface, VoiceContract.View {
    private final String PLAY_MUSIC_URL1 = "http://ws.stream.qqmusic.qq.com/";
    private final String PLAY_MUSIC_URL2 = ".m4a?fromtag=46";
    @BindView(R.id.spectrumView)
    SpectrumView spectrumView;
    private String play_music_url;
    @BindView(R.id.voice_back_iv)
    ImageView voiceBackIv;
    @BindView(R.id.voice_upload)
    ImageView voiceUpload;
    @BindView(musictime)
    TextView musictime1;
    @BindView(R.id.voice_bgm_tv)
    TextView voiceBgmTv;
    @BindView(R.id.voice_record_start)
    ImageView voiceRecordStart;
    @BindView(R.id.voice_selector_bgm)
    ImageView voiceSelectorBgm;
    @BindView(R.id.voice_play)
    ImageView voicePlay;
    @BindView(R.id.voice_rl1)
    RelativeLayout voiceRl1;
    @BindView(R.id.voice_rl2)
    RelativeLayout voiceRl2;
    @BindView(R.id.voice_rl3)
    RelativeLayout voiceRl3;
    @BindView(R.id.composeProgressBar)
    ProgressBar composeProgressBar;
    private boolean recordVoiceBegin;
    private boolean recordComFinish = false;
    private String tag = "RecordAllActivity";
    //    private int width;
//    private int height;
    private int recordTime;
    private int actualRecordTime;
    private TextView tv_record;
    private TextView musicName;
    //  private TextView recordVoiceButton;
    private String className;
    private String tempVoicePcmUrl;
    //  private String musicFileUrl;
    private String decodeFileUrl;
    private String composeVoiceUrl;
    private VoiceActivity instance;
    private final static int REQUESTMUSIC = 0x2001;
    private final static int REQUESTARTICLE = 0x2002;
    private final static int REQUESTUPLOAD = 0x2003;

    private ImageView title_back;
    private ImageView image_anim;
    //    private Animation rotate;
    private String fileNameCom;
    private ComposeVoice composeVoice;
    private String2TimeUtils string2TimeUtils;

    private static int TOTALTIME = 600;  //默认录音600s
    //    private static int TOTALTIME = 600;  //默认录音600s
    private int totalTime = TOTALTIME;  //默认录音600s
    //private Music music;
    private AudioManager audoManager;
    private String eid;
    private String fileName;
    private TextView tv_totaltime;
    private int mid;
    private RecorderAndPlayUtil recorderUtil;
    boolean is2mp3 = true;  //false为有背景音乐，录制音频为pcm文件，true为没有背景音乐，录制音频为Mp3文件
    boolean isPlay = false; //false为没有开始录音，true为开始播放状态
    //    private String eventName;
    private String musicdesc;
    // private SelectArticle selectArticle;
    private String event_title;
    //   protected PlayService mPlayService;
    private AlertDialog.Builder alertBuilder;
    private String music_name, music_id, music_singer;
    private MediaPlayer voicePlayer;
    private LoadingView loading;//加载View
    private Music.ResultBean.ListBean musicListBeen;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        instance = this;
        setContentView(R.layout.activity_voice);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        string2TimeUtils = new String2TimeUtils();
        PermissionGen.needPermission(this, 100, Manifest.permission.RECORD_AUDIO);
        className = getClass().getSimpleName();
        play_music_url= "";
        musicListBeen = (Music.ResultBean.ListBean) getIntent().getSerializableExtra("musicbean");
        if (musicListBeen != null) {
            play_music_url = musicListBeen.getMp3_url();
            music_name = musicListBeen.getMp3_name();
        } else if (getIntent().getStringExtra("music_id")!=null){
            music_name = getIntent().getStringExtra("music_name");
            music_id = getIntent().getStringExtra("music_id");
            music_singer = getIntent().getStringExtra("music_singer");
            play_music_url = PLAY_MUSIC_URL1 + music_id + PLAY_MUSIC_URL2;
        }

        if (voicePlayer == null) {
            voicePlayer = new MediaPlayer();
        }
        handler = new Handler();
        initMusicUI();
        setListener();
    }

    /**
     * 监听MediaPlayer的prepare方法完成
     */
    private void setListener() {
        voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                loading.dismiss();
                voiceBgmTv.setText(music_name + "-" + music_singer);
            }
        });
    }


    public void initMusicUI() {
        try {
            voicePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            voicePlayer.setDataSource(play_music_url);
            voicePlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (EmptyUtils.isNotEmpty(music_name)) {
            is2mp3 = false;
            loading = new LoadingView(this, R.style.CustomDialog);
            loading.show();
        }
        decodeFileUrl = "/sdcard/yqj" + "/" + System.currentTimeMillis() + "bgm.pcm";
        recordTime = 0;
        long t = System.currentTimeMillis() / 1000;
        fileNameCom = t + "cv.mp3";
        composeVoiceUrl = "/sdcard/yqj" + "/" + fileNameCom;
        //     FileUtils.createFileByDeleteOldFile(new File(decodeFileUrl));
        //  FileUtils.createFileByDeleteOldFile(new File(composeVoiceUrl));
//        VoiceFunctionF2.PlayToggleVoice(musicFileUrl, this);
//        VoiceFunctionF2.StopVoice();
        File file = new File(decodeFileUrl);
        File file1 = new File(composeVoiceUrl);
        if (!file.exists()) {
            try {
                file.createNewFile();
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @PermissionSuccess(requestCode = 100)
    public void openContact() {
        recorderUtil = new RecorderAndPlayUtil(this);
        //  initData();
    }

    @PermissionFail(requestCode = 100)
    public void failContact() {
        Toast.makeText(instance, "没有录音权限，无法开启这个功能，请开启权限", Toast.LENGTH_SHORT).show();
        finish();
        PermissiUtils.openSettingActivity(this);
    }

    @Override
    public void playVoiceBegin(long duration) {
        int pTotalTime = (int) duration / 1000;
//        tv_totaltime.setText(string2TimeUtils.stringForTimeS(pTotalTime));

        if (play_music_url != null) {
            AudioFunction.DecodeMusicFile(play_music_url, decodeFileUrl, 0,
                    totalTime, instance);
        }
    }

    @Override
    public void playVoiceFail() {

    }

    @Override
    public void playVoiceStateChanged(long currentDuration) {
        if (recordComFinish) {
            if (currentDuration > 0) {
                int playtime = (int) (currentDuration / RecordConstant.OneSecond);
                musictime1.setText(string2TimeUtils.stringForTimeS(playtime));

            }
        }
    }

    @Override
    public void playVoicePause() {

    }

    @Override
    public void playVoiceFinish() {

    }

    @Override
    public void recordVoiceBegin() {
        if (!recordVoiceBegin) {
            recordVoiceBegin = true;
            recordTime = 0;
            musictime1.setText(string2TimeUtils.stringForTimeS(recordTime));
            musictime1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void recordVoiceStateChanged(int volume, long recordDuration) {
        if (recordDuration > 0) {
            recordTime = (int) (recordDuration / RecordConstant.OneSecond);
            int leftTime = totalTime - recordTime;
            musictime1.setText(string2TimeUtils.stringForTimeS(recordTime));
            if (leftTime <= 0) {
                VoiceFunctionF2.StopRecordVoice(is2mp3);

            }
        }
    }

    @Override
    public void prepareGiveUpRecordVoice() {

    }

    @Override
    public void recoverRecordVoice() {

    }

    @Override
    public void giveUpRecordVoice() {

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
        // musictime.setVisibility(View.INVISIBLE);

    }

    private void goRecordSuccessState() {
        recordVoiceBegin = false;
        musictime1.setText(string2TimeUtils.stringForTimeS(actualRecordTime));
        recordComFinish = true;
        if (play_music_url != null) {
            compose();
        } else {
            stopRecording();
        }
    }

    private void stopRecording() {

        VoiceFunctionF2.PlayToggleVoice(VoiceFunctionF2.getRecorderPath(), instance);
    }

    private void compose() {
        composeProgressBar.setProgress(0);
        composeProgressBar.setVisibility(View.VISIBLE);
        tempVoicePcmUrl = VoiceFunctionF2.getRecorderPath();
        audoManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            AudioFunction.BeginComposeAudio(tempVoicePcmUrl, decodeFileUrl, composeVoiceUrl, false,
                    RecordConstant.VoiceWeight, RecordConstant.VoiceBackgroundWeight,
                    0, this);
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
            composeProgressBar.setProgress(composeProgress);
        }
    }

    @Override
    public void composeSuccess() {
        composeProgressBar.setVisibility(View.GONE);
        recordComFinish = true;
        if (AppInfo.isForeground(instance, getClass().getSimpleName())) {
            try {
                voicePlayer.setDataSource(composeVoiceUrl);
                voicePlayer.prepare();
                voicePlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            voiceRecordStart.setImageResource(R.mipmap.voice_start);
            CommonFunction.showToast("合成成功", className);
        }
    }

    @Override
    public void composeFail() {
        composeProgressBar.setVisibility(View.GONE);
        CommonFunction.showToast("合成失败", className);
    }

    @Override
    public void updateDecodeProgress(int decodeProgress) {
        /*    composeProgressBar.setProgress(
                    decodeProgress * 50/ 100);*/
    }

    @Override
    public void decodeSuccess() {
        Toast.makeText(instance, "解码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void decodeFail() {
        Toast.makeText(instance, "对不起，音频解码失败，请在设置意见反馈中提交您的机型。", Toast.LENGTH_SHORT).show();
    }

    public void voice_back(View view) {
        finish();
    }

    @OnClick({R.id.voice_record_start, R.id.voice_selector_bgm, R.id.voice_main, R.id.voice_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_record_start:
                if (isPlay) {
                    isPlay = false;
                    voicePlayer.stop();
                    handler.removeCallbacks(runnable);
                    voiceRecordStart.setImageResource(R.mipmap.voice_start);
                    VoiceFunctionF2.pauseRecordVoice(is2mp3);
                    voicePlayer.reset();
                } else {
                    if (EmptyUtils.isNotEmpty(play_music_url)) {
                        voicePlayer.start();
                    }
                    handler.post(runnable);
                    isPlay = true;
                    voiceRecordStart.setImageResource(R.mipmap.voice_stop);
                    VoiceFunctionF2.StartRecordVoice(is2mp3, instance);
                }
                break;
            case R.id.voice_selector_bgm:
                Intent intent = new Intent(this, SelectorBgmActivity.class);
                startActivity(intent);
                break;
            case R.id.voice_play:
                goRecordSuccessState();
                break;
            case R.id.voice_main:
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacks(runnable);
            handler = null;
        }
        if (voicePlayer != null && voicePlayer.isPlaying()) {
            voicePlayer.stop();
            voicePlayer.release();
            voicePlayer = null;
        }
        if (RecorderEngine.getInstance()!=null)
        RecorderEngine.Destroy();
    }
}
