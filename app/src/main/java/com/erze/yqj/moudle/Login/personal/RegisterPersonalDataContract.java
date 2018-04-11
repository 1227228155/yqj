package com.erze.yqj.moudle.Login.personal;

import com.erze.yqj.entity.RegisterPersonalDataBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

/**
 * Created by 1227228155@qq.com on 2017/6/26.
 */

public interface RegisterPersonalDataContract {
    interface Model extends BaseModel{
        Observable<RegisterPersonalDataBean> getRegisterPersonalData(@Part List<MultipartBody.Part> partList);
    }

    interface View extends BaseView{
       void getPersonalData(RegisterPersonalDataBean bean);
        void gotoMain();
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getRegisterPersonalData(@Part List<MultipartBody.Part> partList);
    }
}
