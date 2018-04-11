package com.erze.yqj.moudle.user;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PhoneUtils;
import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends AppCompatActivity {

    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.phone)
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("客服");
    }

    @OnClick({R.id.rl1, R.id.rl2, R.id.common_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.rl1:
                PhoneUtils.dial(phone.getText().toString());
                break;
            case R.id.rl2:
                gotoQQchat(qq.getText().toString());
                break;
        }
    }

    private void gotoQQchat(String number) {
        if (checkApkExist(this, "com.tencent.mobileqq")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + number + "&version=1")));
        } else {
            Toast.makeText(this, "本机未安装手机QQ", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
