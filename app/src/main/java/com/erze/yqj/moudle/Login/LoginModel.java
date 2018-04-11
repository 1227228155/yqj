package com.erze.yqj.moudle.Login;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.RegisterBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/6/21.
 */

public class LoginModel implements LoginContract.Model {


    @Override
    public Observable<RegisterBean> getRegisterUser(String userPhone) {
        return NetDao.getInstance().getUserApi().registerUser(userPhone).compose(Rxschedulers.<RegisterBean>io_main());
    }
}
