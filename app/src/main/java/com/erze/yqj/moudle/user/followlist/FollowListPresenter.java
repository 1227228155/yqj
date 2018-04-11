package com.erze.yqj.moudle.user.followlist;

import com.erze.yqj.entity.FansListBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class FollowListPresenter extends FollowListContract.Presenter {
    @Override
    void getFollowList(Map<String, String> map) {
        mRxmanager.add(mModel.getFollowList(map).subscribe(new Consumer<FansListBean>() {
            @Override
            public void accept(@NonNull FansListBean bean) throws Exception {
                    if (bean.getCode().equals("200")){
                        mView.getFollowList(bean);
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
