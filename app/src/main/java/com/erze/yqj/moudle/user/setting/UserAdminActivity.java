package com.erze.yqj.moudle.user.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.entity.ObjKey;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.utils.AutoUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAdminActivity extends AppCompatActivity {
    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.admin_id)
    TextView adminId;
    @BindView(R.id.admin_avatar)
    ImageView adminAvatar;
    private SPUtils spUtils = SPUtils.getInstance("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("账号管理");
        initView();
    }

    private void initView() {
        adminId.setText("ID: " + spUtils.getString("userid"));
        if (EmptyUtils.isNotEmpty(spUtils.getString("useravatar"))) {
            Glide.with(this).load(spUtils.getString("useravatar")).into(adminAvatar);
        } else {
            adminAvatar.setImageResource(R.drawable.null_avatar);
        }
    }


    @OnClick({R.id.common_back, R.id.user_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.user_exit:
                showNormalDialog();
                break;

        }
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否退出登录?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                spUtils.clear();
                                EventBus.getDefault().post(new ObjKey("initAdapter"), "initData");
                                ActivityUtils.startActivity(UserAdminActivity.this, MainActivity.class);
                                finish();
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                LogUtils.e("msg",message);

                            }
                        });
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


}
