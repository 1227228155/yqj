package com.erze.yqj.moudle.comment.reply;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.ReplyBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/8/1.
 */

public class ReplyModel implements ReplyContract.Model {

    @Override
    public Observable<ReplyBean> getReply(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getReply(map).compose(Rxschedulers.<ReplyBean>io_main());
    }
}
