package com.erze.yqj.moudle.world.world;

import com.erze.yqj.entity.Videobean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class TabWorldPresenter extends TabWorldContract.Presenter {
    @Override
    void getVideo(Map<String, String> map) {
        mRxmanager.add(mModel.getVideo(map).subscribe(new Consumer<Videobean>() {
            @Override
            public void accept(@NonNull Videobean videobean) throws Exception {
                    if (videobean.getCode().equals("200")){
                        mView.getVideo(videobean);
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

            }
        }));
    }
}
