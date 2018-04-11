package com.erze.yqj.moudle.main.popup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

public class PopupVoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_voice);
        AutoUtils.auto(this);
    }
}
