package com.erze.yqj.moudle.user;

import com.erze.yqj.entity.UserBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class UserFragmentPresenter extends UserFragmentContract.Presenter {
    @Override
    void getUserData(Map<String, String> map) {
        mRxmanager.add(mModel.getUserData(map).subscribe(new Consumer<UserBean>() {
            @Override
            public void accept(@NonNull UserBean bean) throws Exception {
                    if (bean.getCode().equals("200")){
                        mView.getUserDataBean(bean);
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
