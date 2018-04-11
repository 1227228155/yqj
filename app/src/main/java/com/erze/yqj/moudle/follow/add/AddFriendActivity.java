package com.erze.yqj.moudle.follow.add;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.erze.yqj.R;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.et)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                //参数为要添加的好友的username和添加理由
                send();
                break;
        }
    }

    private void send() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                EMClient.getInstance().contactManager().addContact(getIntent().getStringExtra("hxid"), et.getText().toString().trim());
                e.onComplete();
            }
        }).compose(Rxschedulers.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(AddFriendActivity.this, "发送申请成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
