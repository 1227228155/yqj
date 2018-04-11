package com.erze.yqj.moudle.follow;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.UserDataBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class OtherFollowModel implements OtherFollowContract.Model {
    @Override
    public Observable<UserDataBean> getUserData(Map<String, String> map) {
        return NetDao.getInstance().getUserApi().getUserData(map).compose(Rxschedulers.<UserDataBean>io_main());
    }

    @Override
    public Observable<CodeBean> getFollowStatus(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getFollowStatus(map).compose(Rxschedulers.<CodeBean>io_main());
    }
}
