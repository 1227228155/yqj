package com.erze.yqj.moudle.follow;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class FollowModel implements FollowContract.Model {
    @Override
    public Observable<FollowBean> getFollow(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getFollow(map).compose(Rxschedulers.<FollowBean>io_main());
    }
}
