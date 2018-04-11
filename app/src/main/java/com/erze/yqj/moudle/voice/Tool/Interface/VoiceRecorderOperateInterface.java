package com.erze.yqj.moudle.voice.Tool.Interface;

public interface VoiceRecorderOperateInterface {
    public void recordVoiceBegin();

    public void recordVoiceStateChanged(int volume, long recordDuration);

    public void prepareGiveUpRecordVoice();

    public void recoverRecordVoice();

    public void giveUpRecordVoice();

    public void recordVoiceFail();

    public void recordVoiceFinish();
}
