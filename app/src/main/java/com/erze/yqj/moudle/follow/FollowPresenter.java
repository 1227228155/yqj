package com.erze.yqj.moudle.follow;

import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.entity.FollowBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class FollowPresenter extends FollowContract.Presenter {
    @Override
    void getFollow(Map<String, String> map) {
        mRxmanager.add(mModel.getFollow(map).subscribe(new Consumer<FollowBean>() {
            @Override
            public void accept(@NonNull FollowBean bean) throws Exception {
                LogUtils.e("code",bean.getCode());
                    if (bean.getCode().equals("200")){
                        mView.getFollowBean(bean);
                    }else if (bean.getCode().equals("1012")){
                        mView.showEmptyLog();
                    }
                    else{
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
