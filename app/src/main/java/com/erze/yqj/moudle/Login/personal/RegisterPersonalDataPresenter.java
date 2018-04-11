package com.erze.yqj.moudle.Login.personal;

import com.erze.yqj.entity.RegisterPersonalDataBean;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

/**
 * Created by 1227228155@qq.com on 2017/6/26.
 */

public class RegisterPersonalDataPresenter extends RegisterPersonalDataContract.Presenter {

    @Override
    void getRegisterPersonalData(@Part List<MultipartBody.Part> partList) {
        mModel.getRegisterPersonalData(partList).subscribe(new Consumer<RegisterPersonalDataBean>() {
            @Override
            public void accept(@NonNull RegisterPersonalDataBean registerPersonalDataBean) throws Exception {
                        mView.getPersonalData(registerPersonalDataBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                    mView.gotoMain();
            }
        });
    }
}
