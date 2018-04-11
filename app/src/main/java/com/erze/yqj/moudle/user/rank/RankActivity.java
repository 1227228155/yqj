package com.erze.yqj.moudle.user.rank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.HotBean;
import com.erze.yqj.moudle.user.rank.adapter.RankAdapter;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class RankActivity extends AppCompatActivity {


    @BindView(R.id.rank_no1_avatar)
    ImageView rankNo1Avatar;
    @BindView(R.id.rank_no2_avatar)
    ImageView rankNo2Avatar;
    @BindView(R.id.rank_no3_avatar)
    ImageView rankNo3Avatar;
    @BindView(R.id.rank_no2_name)
    TextView rankNo2Name;
    @BindView(R.id.rank_no1_name)
    TextView rankNo1Name;
    @BindView(R.id.rank_no3_name)
    TextView rankNo3Name;
    @BindView(R.id.rank_listView)
    ListView rankListView;
    @BindView(R.id.rank_count)
    TextView rankCount;
    private Map<String, String> map = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rank);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        map.clear();
        map.put("type", "p");
        NetDao.getInstance().getCommonApi().getHot(map).compose(Rxschedulers.<HotBean>io_main())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(@NonNull HotBean hotBean) throws Exception {
                        initData(hotBean);
                    }
                });
    }

    private void initData(HotBean hotBean) {
        Glide.with(this).load(hotBean.getResult().get(0).getUser_pic()).into(rankNo1Avatar);
        Glide.with(this).load(hotBean.getResult().get(1).getUser_pic()).into(rankNo2Avatar);
        Glide.with(this).load(hotBean.getResult().get(2).getUser_pic()).into(rankNo3Avatar);
        rankNo1Name.setText(hotBean.getResult().get(0).getUser_nickname());
        rankNo2Name.setText(hotBean.getResult().get(1).getUser_nickname());
        rankNo3Name.setText(hotBean.getResult().get(2).getUser_nickname());
        initList(hotBean);
    }

    private void initList(HotBean hotBean) {
        RankAdapter adapter = new RankAdapter(RankActivity.this,hotBean.getResult());
        rankListView.setAdapter(adapter);
    }

    @OnClick(R.id.rank_back)
    public void onViewClicked() {
        finish();
    }
}
