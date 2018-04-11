package com.erze.yqj.moudle.Login.yzm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.moudle.Login.personal.RegisterPersonalDataActivity;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;

public class YzmActivity extends AppCompatActivity {
    String usernamePhone;
    @BindView(R.id.login_et)
    EditText loginEt;
    String YzmString;
    String code;
    SPUtils spUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
    }




    @OnClick(R.id.login_goto_main)
    public void onViewClicked() {
        YzmString = loginEt.getText().toString().trim();
        code = getIntent().getPackage();
        LogUtils.e("CODE",code);
        //getIntent().getAction 传过来的用户手机号
        usernamePhone = getIntent().getAction();
        SMSSDK.submitVerificationCode("86",usernamePhone,YzmString);

        //如果code码为201，说明用户未注册，跳转到注册用户信息的Acticity
        //否则直接跳转到MainActivity
        if (code.equals("2001")){
            Intent intent = new Intent(YzmActivity.this, RegisterPersonalDataActivity.class);
            intent.setAction(usernamePhone);
            startActivity(intent);
        }else {
            if (spUtils==null){
                spUtils = SPUtils.getInstance("user");
            }
            spUtils.put("isLogin","登陆状态");
            ActivityUtils.startActivity(YzmActivity.this, MainActivity.class);
        }
    }
}
