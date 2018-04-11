package com.erze.yqj.moudle.user.collection;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Window;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.moudle.comment.listview.PullUpLoadMoreListView;
import com.erze.yqj.moudle.follow.adapter.FollowAdapter;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity extends BaseFrameActivity<CollectionPresenter, CollectionModel> implements CollectionContract.View {

    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.collection_listView)
    PullUpLoadMoreListView collectionListView;
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    private Map<String, String> map = new ArrayMap<>();
    private SPUtils spUtils = SPUtils.getInstance("user");
    private int page =1;
    private    FollowAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        AutoUtils.auto(this);
        commonTitle.setText("收藏");
        initData1(1);
        initListener1();
    }

    private void initData1(int page) {
        map.put("user_id", spUtils.getString("userid"));
        map.put("page", String.valueOf(page));
        mPresenter.getCollectionInfo(map);
    }

    private void initListener1() {
        collectionListView.setOnLoadMoreListener(new PullUpLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                page++;
                initData1(page);
            }
        });
    }


    @Override
    public void getInfo(FollowBean bean) {
        loading.setStatus(LoadingLayout.Success);
        if (page>1){
            adapter.addAdapter(bean.getResult());
        }else {
            List<FollowBean.ResultBean> beanList = bean.getResult();
            Window window = getWindow();
             adapter = new FollowAdapter(this, beanList, window, "收藏", collectionListView);
            collectionListView.setAdapter(adapter);
        }

    }

    @Override
    public void showEmptyLog() {
        if (page==1){
            loading.setStatus(LoadingLayout.Empty);
        }else {
            collectionListView.setLoadState(false);
        }
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
