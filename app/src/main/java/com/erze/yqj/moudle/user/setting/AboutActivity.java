package com.erze.yqj.moudle.user.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.common_title)
    TextView commonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("关于");
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
