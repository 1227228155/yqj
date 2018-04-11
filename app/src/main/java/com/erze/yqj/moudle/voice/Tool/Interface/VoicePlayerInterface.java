package com.erze.yqj.moudle.voice.Tool.Interface;

public interface VoicePlayerInterface {
    public void playVoiceBegin(long duration);

    public void playVoiceFail();
    public void playVoiceStateChanged(long currentDuration);
    public void playVoicePause();
    public void playVoiceFinish();
}
