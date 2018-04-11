package com.erze.yqj.moudle.voice.bgm;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.Music;
import com.erze.yqj.entity.MusicBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/17.
 */

public class BgmModel implements BgmContract.Model {


    @Override
    public Observable<MusicBean> findBgm(String url) {
        return NetDao.getInstance().getMusicApi().getNetMusic(url).compose(Rxschedulers.<MusicBean>io_main());
    }

    @Override
    public Observable<Music> getMusic(Map<String, String> map) {
        return NetDao.getInstance().getMusicApi().getMusic(map).compose(Rxschedulers.<Music>io_main());
    }
}
