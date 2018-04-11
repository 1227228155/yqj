package com.erze.yqj.moudle.comment.reply;

import com.erze.yqj.entity.ReplyBean;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/8/1.
 */

public class ReplyPresenter extends ReplyContract.Presenter {

    @Override
    void getReply(Map<String, String> map) {
        mRxmanager.add(mModel.getReply(map).subscribe(new Consumer<ReplyBean>() {
            @Override
            public void accept(@NonNull ReplyBean replyBean) throws Exception {
                    mView.getReplyBean(replyBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }
}
