package com.erze.yqj.moudle.Login.personal;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.RegisterPersonalDataBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

/**
 * Created by 1227228155@qq.com on 2017/6/26.
 */

public class RegisterPersonalDataModel implements RegisterPersonalDataContract.Model {

    @Override
    public Observable<RegisterPersonalDataBean> getRegisterPersonalData(@Part List<MultipartBody.Part> partList) {
        return NetDao.getInstance().getUserApi().registerPersonalData(partList).compose(Rxschedulers.<RegisterPersonalDataBean>io_main());
    }
}
