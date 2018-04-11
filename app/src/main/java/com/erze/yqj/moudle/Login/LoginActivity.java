package com.erze.yqj.moudle.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.RegisterBean;
import com.erze.yqj.moudle.Login.personal.RegisterPersonalDataActivity;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends BaseFrameActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    EventHandler eventHandler;
    Disposable subscribe;
    String userPhoneNumber, YzmString;
    String substring;
    String code;
    String msg;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.btn_send_msg)
    RelativeLayout btnSendMsg;
    @BindView(R.id.Yzm_et)
    EditText YzmEt;
    @BindView(R.id.login_goto_main)
    Button loginGotoMain;
    SPUtils spUtils;
    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.yzm)
    TextView yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.hideStatusBar(this);
        AutoUtils.setSize(this, false, 1080, 1920);
        setContentView(R.layout.activity_login);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("手机登录");
        initMob();
        if (spUtils == null) {
            spUtils = SPUtils.getInstance("user");
        }
    }


    @OnClick(R.id.btn_send_msg)
    public void onViewClicked() {
        userPhoneNumber = phoneEt.getText().toString().trim();
        //如果手机号码长度大于等于4，取前四位手机号
        if (userPhoneNumber.length() > 3) {
            substring = userPhoneNumber.substring(0, 4);
        } else {
            substring = "00000000000";
        }
        if (EmptyUtils.isEmpty(userPhoneNumber)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else if (substring.equals("3793") && userPhoneNumber.length() == 11) { //如果注册手机是3793开头，并且为11位，直接跳过验证码验证
            mPresenter.RegisterUser(userPhoneNumber);
        } else if (RegexUtils.isMobileExact(userPhoneNumber)) {
            initCountDown();
            mPresenter.RegisterUser(userPhoneNumber);
            SMSSDK.getSupportedCountries();
            SMSSDK.getVerificationCode("86", userPhoneNumber);
        } else {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
        }
    }

    private void initCountDown() {
        final int count = 60;
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        btnSendMsg.setEnabled(false);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        yzm.setText(aLong + "s重新发送");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        yzm.setText("发送验证码");
                        btnSendMsg.setEnabled(true);
                    }
                });

    }


    /**
     * 初始化Mob
     */
    private void initMob() {
      /*  // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(boolShowInDialog)
*/
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message m = Message.obtain();
                m.what = 1;
                m.arg1 = event;//event
                m.arg2 = result;//result
                handler.sendMessage(m);
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    msg = throwable.getMessage();
                    LogUtils.e("error", msg);
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }


    @Override
    public void showUserPhone(RegisterBean registerBean) {
        code = registerBean.getCode();
        LogUtils.e("code",code);
        LogUtils.e("substring",substring);
        if (code.equals("2001") && substring.equals("3793")) {
            Intent intent = new Intent(LoginActivity.this, RegisterPersonalDataActivity.class);
            intent.setAction(userPhoneNumber);
            startActivity(intent);
            finish();
        } else if (code.equals("200")) {
            if (spUtils == null) {
                spUtils = SPUtils.getInstance("user");
            }
            spUtils.clear();
            spUtils.put("isLogin", "登录状态");
            spUtils.put("userid", registerBean.getResult().getUser_id());
            spUtils.put("userhxid", registerBean.getResult().getUser_id_number());
            spUtils.put("username", registerBean.getResult().getUser_nickname());
            spUtils.put("usersex", registerBean.getResult().getUser_sex());
            spUtils.put("userage", registerBean.getResult().getUser_age());
            spUtils.put("useravatar", registerBean.getResult().getUser_pic());
            spUtils.put("uservip", registerBean.getResult().getUser_vip_status());
            spUtils.put("userphone", registerBean.getResult().getUser_mobile());
            spUtils.put("userbirth", registerBean.getResult().getUser_birth());
            spUtils.put("userlabel", registerBean.getResult().getUser_label());
            if (substring.equals("3793")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setAction(userPhoneNumber);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
    }

    /**
     * 初始化Mob回调方法
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//发送成功的情况
                        if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                            if (code.equals("2001")) {
                                Intent intent = new Intent(LoginActivity.this, RegisterPersonalDataActivity.class);
                                intent.setAction(userPhoneNumber);
                                startActivity(intent);
                                finish();
                            } else {
                                spUtils.put("isLogin", "登陆状态");
                                ActivityUtils.startActivity(LoginActivity.this, MainActivity.class);
                                finish();
                            }

                        } else if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//验证码已经从服务器发出
                            Toast.makeText(getApplicationContext(), "验证码已发出,请注意查收", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void gotoMain(View view) {
        YzmString = YzmEt.getText().toString().trim();
        if (EmptyUtils.isNotEmpty(YzmString)) {
            SMSSDK.submitVerificationCode("86", userPhoneNumber, YzmString);
        } else {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
