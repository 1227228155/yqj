package com.erze.yqj.moudle.world.world;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erze.yqj.R;
import com.erze.yqj.entity.Videobean;
import com.erze.yqj.moudle.world.world.adapter.TabWorldAdapter;
import com.erze.yqj.rxframe.base.BaseFrameFragment;
import com.erze.yqj.utils.SpaceItemDecoration;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabWorldFragment extends BaseFrameFragment<TabWorldPresenter, TabWorldModel> implements TabWorldContract.View {

    @BindView(R.id.world_tab_recyclerView)
    PullLoadMoreRecyclerView worldTabRecyclerView;
    Unbinder unbinder;
    private Map<String, String> map = new ArrayMap<>();
    private int page = 1;
    private boolean update = false ;
    private boolean pullload = false;
    TabWorldAdapter adapter;
    public TabWorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tab_world, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        getData(page);
        initListener1();
        return inflate;
    }

    private void getData(int page) {
        map.clear();
        map.put("param", "world");
        map.put("page", String.valueOf(page));
        mPresenter.getVideo(map);
    }

    private void initListener1() {
        worldTabRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                update = true;
                page=1;
                getData(1);
            }

            @Override
            public void onLoadMore() {
                page++;
                pullload = true;
                getData(page);
            }
        });
    }

    @Override
    public void showErrorLog() {
        worldTabRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void getVideo(Videobean videobean) {
        List<Videobean.ResultBean> beanList = videobean.getResult();
        if (pullload){
                adapter.addAdapter(beanList);
                  pullload = false;
            worldTabRecyclerView.setPullLoadMoreCompleted();
        }else {
            if (update){
                adapter.initAdapter(beanList);
                update = false;
            }else {
                adapter = new TabWorldAdapter(getActivity(), beanList,getActivity().getWindow(),worldTabRecyclerView);
                //3.为recyclerView设置布局管理器
                worldTabRecyclerView.setStaggeredGridLayout(2);
                worldTabRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
                worldTabRecyclerView.setAdapter(adapter);
            }
        }

        worldTabRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
