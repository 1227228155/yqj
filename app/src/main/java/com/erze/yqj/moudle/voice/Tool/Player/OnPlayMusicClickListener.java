package com.erze.yqj.moudle.voice.Tool.Player;

import com.erze.yqj.entity.Music;

/**
 * Created by Administrator on 2017/5/9.
 */

public interface OnPlayMusicClickListener {
    public void onPlayClick(Music sound);
    public void onPauseClick(Music sound);
}