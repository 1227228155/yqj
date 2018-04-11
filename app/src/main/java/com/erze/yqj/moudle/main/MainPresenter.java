package com.erze.yqj.moudle.main;

import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.entity.KSYSDKBean;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/6.
 */

public class MainPresenter extends MainContract.Presenter {
    @Override
    void getSDK(String pkg) {
        mRxmanager.add(mModel.getSDK(pkg).subscribe(new Consumer<KSYSDKBean>() {
            @Override
            public void accept(@NonNull KSYSDKBean ksysdkBean) throws Exception {
                LogUtils.e("ksysdkBean",ksysdkBean.getData().toString());
                mView.getSDK(ksysdkBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                mView.gotoRecord();
            }
        }));
    }
}
