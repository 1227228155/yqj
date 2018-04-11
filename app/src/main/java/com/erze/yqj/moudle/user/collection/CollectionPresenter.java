package com.erze.yqj.moudle.user.collection;

import com.erze.yqj.entity.FollowBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public class CollectionPresenter extends CollectionContract.Presenter {
    @Override
    void getCollectionInfo(Map<String, String> map) {
        mRxmanager.add(mModel.getCollectionInfo(map).subscribe(new Consumer<FollowBean>() {
            @Override
            public void accept(@NonNull FollowBean bean) throws Exception {
                 String code =  bean.getCode();
                    if (code.equals("200")){
                        mView.getInfo(bean);
                    }else  if (code.equals("2015")){
                        mView.showEmptyLog();
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
