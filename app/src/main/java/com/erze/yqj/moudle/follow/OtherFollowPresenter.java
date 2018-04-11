package com.erze.yqj.moudle.follow;

import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.UserDataBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class OtherFollowPresenter extends OtherFollowContract.Presenter {

    @Override
    void getUserData(Map<String, String> map) {
        mRxmanager.add(mModel.getUserData(map).subscribe(new Consumer<UserDataBean>() {
            @Override
            public void accept(@NonNull UserDataBean bean) throws Exception {
                    if (bean.getCode().equals("200")){
                        mView.getUserData(bean);
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

    @Override
    void getFollowStatus(Map<String, String> map) {
        mRxmanager.add(mModel.getFollowStatus(map).subscribe(new Consumer<CodeBean>() {
            @Override
            public void accept(@NonNull CodeBean codeBean) throws Exception {
                LogUtils.e("codeBean",codeBean.toString());
                if (codeBean.getCode().equals("200")){
                        mView.getFollowStatus(codeBean);
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
