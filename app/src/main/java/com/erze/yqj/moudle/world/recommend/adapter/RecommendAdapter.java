package com.erze.yqj.moudle.world.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.erze.yqj.GlideImageLoader;
import com.erze.yqj.R;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.utils.AutoUtils;
import com.erze.yqj.utils.SpaceItemDecoration;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/8/7.
 */

public class RecommendAdapter extends RecyclerView.Adapter{
    Context context;
    List<WorldBean.ResultBean.BannerBean> bannerBeanList ;//轮播图集合
    List<WorldBean.ResultBean.AnnoBean> annoBeanList ;//跑马灯集合
    List<WorldBean.ResultBean.VideoListBean> video_list ;//recyclerView数据集合
    ArrayList<String> info = new ArrayList<>();
    ArrayList<String> bannerList = new ArrayList<>();
    ArrayList<String> urlList = new ArrayList<>();
    private Window window;
    private View popupView;
    boolean update = false;
    private  int type;
    StaggeredGridLayoutManager.LayoutParams layoutParams ;
    RecyclerViewAdapter adapter;
    public RecommendAdapter(Context context,WorldBean worldBean1,
                            Window window, View view) {
        this.context = context;
        this.bannerBeanList = worldBean1.getResult().getBanner();
        this.annoBeanList = worldBean1.getResult().getAnno();
        this.video_list = worldBean1.getResult().getVideo_list();
        this.window = window;
        popupView = view;
    }

    public void initAdapter(WorldBean worldBean1){
        this.bannerBeanList = worldBean1.getResult().getBanner();
        this.annoBeanList = worldBean1.getResult().getAnno();
        this.video_list = worldBean1.getResult().getVideo_list();
        update = true;
        notifyDataSetChanged();
    }
    public void addAdapter(WorldBean worldBean1){
        this.video_list.addAll(worldBean1.getResult().getVideo_list());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (viewType){
            case 0:
                view =layoutInflater .inflate(R.layout.item_banner, parent,false);
                AutoUtils.auto(view);
                holder = new BannerViewHolder(view);
            break;
            case 1:
                view =layoutInflater .inflate(R.layout.item_marquee, parent,false);
                AutoUtils.auto(view);
               holder = new MarqueeViewHolder(view);
                break;
            case 2:
                view =layoutInflater .inflate(R.layout.item_recyclerview, parent,false);
                AutoUtils.auto(view);
                holder =  new RecyclerViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                initBannerData(holder);
                break;
            case 1:
                initMarqueeData(holder);
                break;
            case 2:
                initRecyclerViewData(holder);
                break;
        }
    }

    private void initRecyclerViewData(RecyclerView.ViewHolder holder) {
        if (update){
            adapter.initAdapter(video_list);
        }else {
            RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
            adapter = new RecyclerViewAdapter(context, video_list,window,popupView);
            viewHolder.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewHolder.recyclerView.addItemDecoration(new SpaceItemDecoration(20));
            viewHolder.recyclerView.setFocusable(false);
            viewHolder.recyclerView.setAdapter(adapter);
        }

    }

    private void initMarqueeData(RecyclerView.ViewHolder holder) {
        MarqueeViewHolder viewHolder = (MarqueeViewHolder) holder;
        MarqueeView marqueeView = viewHolder.marqueeView;
        info.clear();
        for (int i = 0; i < annoBeanList.size(); i++) {
            info.add(annoBeanList.get(i).getAnno_title());
        }
        marqueeView.startWithList(info);
        marqueeView.start();
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(context, "第"+position+"条信息被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBannerData(RecyclerView.ViewHolder holder) {
        BannerViewHolder viewHolder = (BannerViewHolder) holder;
        Banner banner = viewHolder.banner;
        bannerList.clear();
        urlList.clear();
        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerList.add(bannerBeanList.get(i).getBanner_img());
            urlList.add(bannerBeanList.get(i).getBanner_url());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置标题集合（当banner样式有显示title时）
        //   banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        //  banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, urlList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        } else if (position == 1){
            return 1;
        } else {
            return 2;
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner banner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.world_banner);
        }
    }

    public class MarqueeViewHolder extends RecyclerView.ViewHolder {

        MarqueeView marqueeView;
        public MarqueeViewHolder(View itemView) {
            super(itemView);
            marqueeView = (MarqueeView) itemView.findViewById(R.id.marqueeView);
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}
