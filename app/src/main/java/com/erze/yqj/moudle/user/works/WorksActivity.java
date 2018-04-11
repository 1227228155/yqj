package com.erze.yqj.moudle.user.works;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.UserWorksBean;
import com.erze.yqj.moudle.user.works.adapter.WorksAdapter;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;
import com.weavey.loading.lib.LoadingLayout;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class WorksActivity extends AppCompatActivity {

    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.listView)
    PullLoadMoreRecyclerView listView;
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private Map<String, String> map = new ArrayMap<>();
    private int page = 1;
    private WorksAdapter adapter;
    private boolean refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("我的作品");
        initListView();
    }

    private void initListView() {
        listView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetData(page);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNetData(page);
    }

    private void getNetData(int page1) {
        map.clear();
        map.put("user_id", spUtils.getString("userid"));
        map.put("page", String.valueOf(page1));
        NetDao.getInstance().getCommonApi().getUserWorks(map).compose(Rxschedulers.<UserWorksBean>io_main())
                .subscribe(new Consumer<UserWorksBean>() {
                    @Override
                    public void accept(@NonNull UserWorksBean userWorksBean) throws Exception {
                        if (userWorksBean.getCode().equals("200") && EmptyUtils.isNotEmpty(userWorksBean.getResult())) {
                            initView(userWorksBean);
                        } else {
                            stopLoad();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                            stopLoad();
                    }
                });
    }

    private void stopLoad() {
        if (page==1){
            loading.setStatus(LoadingLayout.Empty);
        }
        listView.setPullLoadMoreCompleted();
    }

    private void initView(UserWorksBean worksBean) {
        loading.setStatus(LoadingLayout.Success);
        listView.setPullLoadMoreCompleted();
        if (page == 1) {
            if (refresh) {
                adapter.initAdapter(worksBean.getResult());
                listView.refresh();
            }
            listView.setLinearLayout();
            adapter = new WorksAdapter(WorksActivity.this, worksBean.getResult());
            listView.setAdapter(adapter);
        } else {
            adapter.addAdapter(worksBean.getResult());
        }
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
