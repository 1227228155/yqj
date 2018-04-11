package com.erze.yqj.moudle.voice.bgm;

import com.erze.yqj.entity.Music;
import com.erze.yqj.entity.MusicBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/17.
 */

public interface BgmContract {
    interface Model extends BaseModel{
        //请求网络音乐
        Observable<MusicBean> findBgm(String url);
        //请求分类音乐
        Observable<Music> getMusic(Map<String,String> map);

    }

    interface View extends BaseView{
            void getBgmInfo(MusicBean been);
            void getMusic(Music music);
            void showErrorLog();
            void dismissDialog();
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void findBgm(String url);
        abstract void  getMusic(Map<String,String> map);
    }
}
