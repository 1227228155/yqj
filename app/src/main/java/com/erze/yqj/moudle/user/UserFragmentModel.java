package com.erze.yqj.moudle.user;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.UserBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class UserFragmentModel implements UserFragmentContract.Model {
    @Override
    public Observable<UserBean> getUserData(Map<String, String> map) {
        return NetDao.getInstance().getUserApi().getUser(map).compose(Rxschedulers.<UserBean>io_main());
    }
}
