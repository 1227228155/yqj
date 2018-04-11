package com.erze.yqj.moudle.user.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;
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

public class UpdatePhoneActivity extends AppCompatActivity {

    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.update_phone)
    EditText updatePhone;
    @BindView(R.id.update_yzm)
    EditText updateYzm;
    @BindView(R.id.send_yzm)
    Button sendYzm;
    @BindView(R.id.old_phone)
    EditText oldPhone;
    private String phoneNumber;
    private String yzm;
    private EventHandler eventHandler;
    private Disposable subscribe;
    private Disposable disposable;
    private String msg;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private Map<String, String> map = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        ButterKnife.bind(this);
        commonTitle.setText("更改绑定");
        initMob();
    }

    private void initMob() {
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

    /**
     * 初始化Mob回调方法
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//发送成功的情况
                        if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                            sendPhone();//向自己服务器发起请求，修改绑定的手机
                        }

                    } else if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//验证码已经从服务器发出
                        Toast.makeText(getApplicationContext(), "验证码已发出,请注意查收", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                    }
            }
        }

    };

    private void sendPhone() {
        map.put("mobile", phoneNumber);
        map.put("user_id", spUtils.getString("userid"));
        disposable = NetDao.getInstance().getCommonApi().updatePhone(map).compose(Rxschedulers.<CodeBean>io_main()).subscribe(new Consumer<CodeBean>() {
            @Override
            public void accept(@NonNull CodeBean bean) throws Exception {

                if (bean.getCode().equals("200")) {
                    Toast.makeText(UpdatePhoneActivity.this, "修改绑定成功", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @OnClick({R.id.common_back, R.id.send_yzm, R.id.update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.send_yzm:
                send();//发送验证码
                break;
            case R.id.update_btn:
                update();//验证 验证码，修改绑定手机
                break;
        }
    }

    private void update() {
        yzm = updateYzm.getText().toString().trim();
        if (EmptyUtils.isNotEmpty(yzm)) {
            SMSSDK.submitVerificationCode("86", phoneNumber, yzm);
        } else {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void send() {
        phoneNumber = updatePhone.getText().toString().trim();
        if (oldPhone.getText().toString().trim().equals(spUtils.getString("userphone"))){
            if (RegexUtils.isMobileExact(phoneNumber)) {
                SMSSDK.getSupportedCountries();
                SMSSDK.getVerificationCode("86", phoneNumber);
                initCountDown();
            } else {
                Toast.makeText(this, "新手机号码格式不正确", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "旧手机号码不正确", Toast.LENGTH_SHORT).show();
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
                        sendYzm.setEnabled(false);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        sendYzm.setText(aLong + "s重新发送");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        sendYzm.setText("发送验证码");
                        sendYzm.setEnabled(true);
                    }
                });

    }
}
