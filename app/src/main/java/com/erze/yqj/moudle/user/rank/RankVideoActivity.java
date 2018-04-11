package com.erze.yqj.moudle.user.rank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.HotVideoBean;
import com.erze.yqj.moudle.user.rank.adapter.RankVideoAdapter;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.SpaceItemDecoration;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RankVideoActivity extends AppCompatActivity {

    RankVideoAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private Map<String, String> map = new ArrayMap<>();
    private  List<HotVideoBean.ResultBean> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rank_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        map.put("user_id", spUtils.getString("userid"));
        map.put("type", "h");
        NetDao.getInstance().getCommonApi().getHotVideo(map).compose(Rxschedulers.<HotVideoBean>io_main())
                .subscribe(new Consumer<HotVideoBean>() {
                    @Override
                    public void accept(@NonNull HotVideoBean hotVideoBean) throws Exception {
                        if (hotVideoBean.getCode().equals("200")){
                            initView(hotVideoBean);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    private void initView(HotVideoBean hotVideoBean) {
        result = hotVideoBean.getResult();
        adapter = new RankVideoAdapter(this,result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(adapter);
    }
}
