package com.erze.yqj.moudle.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;
import com.hyphenate.easeui.EaseConstant;
import com.erze.yqj.moudle.chat.ui.EaseChatFragment;

import static com.erze.yqj.moudle.main.MainActivity.FlymeSetStatusBarLightMode;
import static com.erze.yqj.moudle.main.MainActivity.MIUISetStatusBarLightMode;

public class EaseChatActivity extends AppCompatActivity {
    private SPUtils spUtils = SPUtils.getInstance("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          requestWindowFeature(Window.FEATURE_NO_TITLE);
        MIUISetStatusBarLightMode(getWindow(), true);
        FlymeSetStatusBarLightMode(getWindow(), true);
        BarUtils.setColor(this, Color.WHITE);
        setContentView(R.layout.activity_ease_chat);
        AutoUtils.auto(this);
        //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        initView();
    }

    private void initView() {
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, getIntent().getStringExtra("hxid"));
        args.putString("nickname",getIntent().getStringExtra("username"));
       // args.putString("avatar",getIntent().getStringExtra("useravatar"));
        //args.putString("hxid",getIntent().getStringExtra("userhxid"));
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
