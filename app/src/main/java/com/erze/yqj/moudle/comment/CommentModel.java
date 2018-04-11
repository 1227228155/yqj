package com.erze.yqj.moudle.comment;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.SendComment;
import com.erze.yqj.entity.SendReply;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/31.
 */

public class CommentModel implements CommentContract.Model {
    @Override
    public Observable<CommentBean> getComment(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getComment(map).compose(Rxschedulers.<CommentBean>io_main());
    }

    @Override
    public Observable<SendComment> sendComment(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().sendComment(map).compose(Rxschedulers.<SendComment>io_main());
    }

    @Override
    public Observable<SendReply> sendReply(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().sendReply(map).compose(Rxschedulers.<SendReply>io_main());
    }
}
