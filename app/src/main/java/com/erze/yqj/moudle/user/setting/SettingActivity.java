package com.erze.yqj.moudle.user.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.moudle.user.ServiceActivity;
import com.erze.yqj.utils.AutoUtils;
import com.erze.yqj.utils.CleanDataMannger;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    String cacheSize;
    String phone;
    SPUtils spUtils = SPUtils.getInstance("user");
    @BindView(R.id.setting_avatar)
    ImageView settingAvatar;
    @BindView(R.id.phone)
    TextView phoneTv;
    @BindView(R.id.switch_button_msg)
    SwitchButton switchButtonMsg;
    @BindView(R.id.switch_button_sr)
    SwitchButton switchButtonSr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    private void initView() {
        if (EmptyUtils.isNotEmpty(spUtils.getString("useravatar"))) {
            Glide.with(this).load(spUtils.getString("useravatar")).into(settingAvatar);
        } else {
            settingAvatar.setImageResource(R.drawable.null_avatar);
        }
        phone = spUtils.getString("userphone");
        String number1 = phone.substring(0, 3);
        String number2 = phone.substring(8);
        phoneTv.setText(number1 + "******" + number2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initSwitchButton();
    }

    /**
     * 获取及监听SwitchButton的状态
     */
    private void initSwitchButton() {
        switchButtonMsg.setChecked(spUtils.getBoolean("sbtn1", false));
        switchButtonSr.setChecked(spUtils.getBoolean("sbtn2", false));
        switchButtonSr.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                view.setChecked(isChecked);
                spUtils.put("sbtn2",isChecked);
            }
        });
        switchButtonMsg.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                view.setChecked(isChecked);
                spUtils.put("sbtn1",isChecked);
            }
        });
    }


    @OnClick({R.id.rl5, R.id.rl6, R.id.rl3, R.id.rl1, R.id.rl2, R.id.rl4, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl5:
                ActivityUtils.startActivity(this, ServiceActivity.class);
                break;
            case R.id.rl6:
                ActivityUtils.startActivity(this, AboutActivity.class);
                break;
            case R.id.rl3:
                ActivityUtils.startActivity(this, FeedBackActivity.class);
                break;
            case R.id.rl1:
                ActivityUtils.startActivity(this, UserAdminActivity.class);
                finish();
                break;
            case R.id.rl2:
                ActivityUtils.startActivity(this, UpdatePhoneActivity.class);
                break;
            case R.id.rl4:
                cleanCache();
                break;
        }
    }

    private void cleanCache() {
        try {
            cacheSize = CleanDataMannger.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清除缓存" + cacheSize + "?")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CleanDataMannger.clearAllCache(getApplicationContext());
            }
        });
        builder.create().show();
    }
}
