package com.erze.yqj.moudle.comment;

import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.SendComment;
import com.erze.yqj.entity.SendReply;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/31.
 */

public class CommentPresneter extends CommentContract.Presenter {
    @Override
    void getComment(Map<String, String> map) {
        mRxmanager.add(mModel.getComment(map).subscribe(new Consumer<CommentBean>() {
            @Override
            public void accept(@NonNull CommentBean bean) throws Exception {
                    mView.getCommentBean(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }

    @Override
    void sendComment(Map<String, String> map) {
        mRxmanager.add(mModel.sendComment(map).subscribe(new Consumer<SendComment>() {
            @Override
            public void accept(@NonNull SendComment sendComment) throws Exception {
                            mView.sendComment(sendComment);
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
    void sendReply(Map<String, String> map) {
        mRxmanager.add(mModel.sendReply(map).subscribe(new Consumer<SendReply>() {
            @Override
            public void accept(@NonNull SendReply sendReply) throws Exception {
                        mView.sendReply(sendReply);
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
