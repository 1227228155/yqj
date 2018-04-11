package com.erze.yqj.moudle.voice.bgm;

import com.erze.yqj.entity.Music;
import com.erze.yqj.entity.MusicBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/17.
 */

public class BgmPresenter extends BgmContract.Presenter {
    @Override
    void findBgm(String url) {
        mRxmanager.add(mModel.findBgm(url).subscribe(new Consumer<MusicBean>() {
            @Override
            public void accept(@NonNull MusicBean musicBean) throws Exception {
                if (musicBean.getCode()==0){
                        mView.getBgmInfo(musicBean);
                }else {
                    mView.showErrorLog();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                    throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                mView.dismissDialog();
            }
        }));
    }

    @Override
    void getMusic(Map<String, String> map) {
        mRxmanager.add(mModel.getMusic(map).subscribe(new Consumer<Music>() {
            @Override
            public void accept(@NonNull Music music) throws Exception {
                    mView.getMusic(music);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                    throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                    mView.dismissDialog();
            }
        }));
    }
}
