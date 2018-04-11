package com.erze.yqj.moudle.user.fansList;

import com.erze.yqj.entity.FansListBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public class FansListPresenter extends FansListContract.Presenter{
    @Override
    void getFansBean(Map<String, String> map) {
        mRxmanager.add(mModel.getFans(map).subscribe(new Consumer<FansListBean>() {
            @Override
            public void accept(@NonNull FansListBean bean) throws Exception {
                if (bean.getCode().equals("200")){
                    mView.getFansBean(bean);
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
