package com.erze.yqj.moudle.world.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.moudle.world.recommend.adapter.RecommendAdapter;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/6/28.
 */

public class TabRecommendFragment extends Fragment {
    WorldBean worldBean;
    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView pullLoadRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    RecommendAdapter adapter;
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    private Map<String, String> map = new ArrayMap<>();
    private Map<String, String> map1 = new ArrayMap<>();
    private int page = 0;

    public TabRecommendFragment(WorldBean worldBean1) {
        worldBean = worldBean1;
    }

    public TabRecommendFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tab_recommend, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView1();
        initListener1();
    }

    private void initListener1() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(3000);
                initRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
                initPullLoad();
            }
        });
    }

    private void initPullLoad() {
        map1.clear();
        page++;
        map1.put("param", "recommend");
        map1.put("page", String.valueOf(page));
        NetDao.getInstance().getCommonApi().getWorld(map1).compose(Rxschedulers.<WorldBean>io_main()).subscribe(new Consumer<WorldBean>() {
            @Override
            public void accept(@NonNull WorldBean worldBean) throws Exception {
                if (worldBean.getCode().equals("200")) {
                    adapter.addAdapter(worldBean);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                refreshLayout.finishLoadmore();
                refreshLayout.setLoadmoreFinished(true);
            }
        });
    }

    private void initRefresh() {
        map.clear();
        map.put("param", "recommend");
        map.put("page", "1");
        NetDao.getInstance().getCommonApi().getWorld(map).compose(Rxschedulers.<WorldBean>io_main()).subscribe(new Consumer<WorldBean>() {
            @Override
            public void accept(@NonNull WorldBean worldBean) throws Exception {
                adapter.initAdapter(worldBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                refreshLayout.finishLoadmore();
                refreshLayout.setLoadmoreFinished(true);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView1() {
        adapter = new RecommendAdapter(getActivity(), worldBean, getActivity().getWindow(), pullLoadRecyclerView);
        pullLoadRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pullLoadRecyclerView.setHasFixedSize(true);
        pullLoadRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
