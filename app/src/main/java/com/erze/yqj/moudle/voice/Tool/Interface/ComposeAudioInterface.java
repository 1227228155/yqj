package com.erze.yqj.moudle.voice.Tool.Interface;

/**
 * Created by 郑童宇 on 2016/05/10.
 */
public interface ComposeAudioInterface {
    public void updateComposeProgress(int composeProgress);

    public void composeSuccess();

    public void composeFail();
}
