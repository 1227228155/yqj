package com.erze.yqj.moudle.Login;

import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.entity.RegisterBean;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/6/21.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    void RegisterUser(String userPhone) {
        LogUtils.e("accept",userPhone);
      mRxmanager.add(mModel.getRegisterUser(userPhone).subscribe(new Consumer<RegisterBean>() {
          @Override
          public void accept(@NonNull RegisterBean registerBean) throws Exception {
              LogUtils.e("accept", registerBean.getCode());
              LogUtils.e("accept", registerBean.getMsg());
              if (registerBean.getResult()!=null){
                  LogUtils.e("accept", registerBean.getResult().toString());
              }
              mView.showUserPhone(registerBean);
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
