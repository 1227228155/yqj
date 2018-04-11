package com.erze.yqj.moudle.world;

import com.erze.yqj.entity.WorldBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class WorldPresenter extends WorldContract.Presenter {
    @Override
    void getRecommendData(Map<String, String> map) {
        mRxmanager.add(mModel.getRecommendData(map).subscribe(new Consumer<WorldBean>() {
            @Override
            public void accept(@NonNull WorldBean worldBean) throws Exception {
                    if (worldBean.getCode().equals("200")){
                            mView.getRecommendData(worldBean);
                    }else {
                            mView.showErrorLog();
                    }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                mView.showErrorNet();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                        mView.showData();
            }
        }));
    }
}
