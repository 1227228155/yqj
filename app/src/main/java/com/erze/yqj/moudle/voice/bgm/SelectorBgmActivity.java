package com.erze.yqj.moudle.voice.bgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.Music;
import com.erze.yqj.entity.MusicBean;
import com.erze.yqj.moudle.voice.bgm.adapter.MusicAdapter;
import com.erze.yqj.moudle.voice.bgm.adapter.NetMusicAdapter;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectorBgmActivity extends BaseFrameActivity<BgmPresenter, BgmModel> implements BgmContract.View,MusicAdapter.OnItemSelectListener,
                                           NetMusicAdapter.OnItemSelectListener {
    private final String MUSIC_URL1 = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n=";
    private final String MUSIC_URL2 = "&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p=1&catZhida=0&remoteplace=sizer.newclient.next_song&w=";
    @BindView(R.id.bgm_tab)
    TabLayout bgmTab;
    private String music_url;//拼接之后的歌曲请求地址
    private String music_id;//歌曲ID 用来获取播放地址
    private String music_name;//歌曲名字
    private String music_singer;//歌手名字
    private String name;//用户输入的歌曲名字或者歌手
    private String[] strArray = null;//包含歌曲ID的数组
    @BindView(R.id.find_music_et)
    EditText findMusicEt;
    @BindView(R.id.bgmListView)
    PullLoadMoreRecyclerView bgmListView;
   private NetMusicAdapter netMusicAdapter;
    private MusicAdapter musicAdapter;
    LoadingView loading;//加载View
    private String[] tabTitles = {"流行", "经典", "民谣", "古风", "日韩", "英文", "另类", "DJ", "轻音乐"};//歌曲分类
    private String[] getQuery = {"liu", "j", "m", "g", "r", "y", "li", "d", "q"};//查询歌曲分类的参数
    private List<String> tabTitleList;
    private Map<String, String> map = new ArrayMap<>();
    private int mCount = 1;//获取数据的页数
    private String type="liu";//当前页面的歌曲数据属于的类型
    private boolean isNoMore = false;
    private List<Music.ResultBean.ListBean> musicList = new ArrayList<>();
   private List<Music.ResultBean.ListBean> musicListBeen;//本地音乐数据集合
   private List<MusicBean.DataBean.SongBean.ListBean> netMusicListBeen;//网络音乐数据集合
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector__bgm);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        initTabLayout();
        setOnListener();
    }

    private void initTabLayout() {
        tabTitleList = Arrays.asList(tabTitles);
        bgmTab.setTabMode(TabLayout.MODE_SCROLLABLE);//支持超过屏幕可以滑动
        for (int i = 0; i < tabTitleList.size(); i++) {
            bgmTab.addTab(bgmTab.newTab().setText(tabTitleList.get(i)),i);
        }
        //显示第一个分类的数据
        getData(getQuery[0], String.valueOf(mCount));
        bgmTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type =  getQuery[tab.getPosition()];
                mCount = 1;//每次切换TabLayout类型时初始化页面为第一页
                isNoMore = false;//每次切换isNoMore初始化，可以下拉加载
                getData(type, String.valueOf(mCount));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    /**
     *   请求歌曲分类列表数据
     * @param str 分类参数
     * @param page 页数
     */
    public void getData(String str,String page){
        loading = new LoadingView(this, R.style.CustomDialog);
        map.clear();
        map.put("type", str);
        map.put("len", "l");
        map.put("page", page);
        mPresenter.getMusic(map);
        if (mCount==1){
            loading.show();
        }
    }

    /**
     * ListView的监听事件
     *
     */
    private void setOnListener() {


        bgmListView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                bgmListView.setRefreshing(true);
                mPresenter.getMusic(map);
            }

            @Override
            public void onLoadMore() {
                if (!isNoMore){
                    mCount++;
                    getData(type, String.valueOf(mCount));
                }else {
                    bgmListView.setPullLoadMoreCompleted();
                }
            }
        });
    }


    /**
     * 搜索歌曲
     *
     * @param view
     */
    public void findBgm(View view) {
        name = findMusicEt.getText().toString().trim();
        LogUtils.e("name", name);
        if (!EmptyUtils.isEmpty(name)) {
            loading.show();
            music_url = MUSIC_URL1 + "20" + MUSIC_URL2 + name;
            bgmListView.setVisibility(View.GONE);
            mPresenter.findBgm(music_url);

        } else {
            Toast.makeText(this, "输入要搜索的歌曲", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获得网络搜索的歌曲
     *
     * @param been
     */
    @Override
    public void getBgmInfo(MusicBean been) {
        bgmListView.setVisibility(View.VISIBLE);
        netMusicListBeen = been.getData().getSong().getList();
        bgmTab.setVisibility(View.GONE);
        bgmListView.setVisibility(View.VISIBLE);
        netMusicAdapter.setOnItemSelectListener(this);
        netMusicAdapter = new NetMusicAdapter(getApplicationContext(), netMusicListBeen);
        netMusicAdapter.notifyDataSetChanged();
        bgmListView.setLinearLayout();
        bgmListView.setPullRefreshEnable(false);
        bgmListView.setPushRefreshEnable(false);
        bgmListView.setAdapter(netMusicAdapter);
    }

    /**
     * 获得本地歌曲
     *
     * @param music
     */
    @Override
    public void getMusic(Music music) {
        musicListBeen = music.getResult().getList();
        LogUtils.e("size",musicListBeen.size());
        LogUtils.e("code",music.getCode());
       int a = Integer.parseInt(music.getResult().getCount());
        double v = a * 0.1;
        int b = (int)v;
        if (mCount>b){//上拉加载没有更多数据
            bgmListView.setPullLoadMoreCompleted();
            isNoMore = true;//没有更多数据
        }else {
            if (mCount>1){
                musicList.addAll(musicListBeen);
                musicAdapter.addAllData(musicListBeen);
            }else {
                musicList = musicListBeen;
                musicAdapter = new MusicAdapter(this, musicListBeen);
                musicAdapter.setOnItemSelectListener(this);
                bgmListView.setLinearLayout();
                bgmListView.setPullRefreshEnable(true);
                bgmListView. setFooterViewTextColor(R.color.black);
                bgmListView.setColorSchemeResources(android.R.color.holo_red_dark,android.R.color.holo_blue_dark);
                bgmListView.setFooterViewBackgroundColor(R.color.record_title_bg_selected);
                bgmListView.setAdapter(musicAdapter);
            }
            musicAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showErrorLog() {
        Toast.makeText(this, "搜索歌曲失败", Toast.LENGTH_SHORT).show();
        loading.dismiss();

    }

    @Override
    public void dismissDialog() {
        loading.dismiss();
        bgmListView.setPullLoadMoreCompleted();
    }


    /**
     * 点击音乐跳转到录音界面
     * @param position  选择的是金山服务器音乐
     */
    @Override
    public void onItemSelect(int position) {
        Intent intent = new Intent();
        intent.putExtra("data", musicList.get(position));
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * 跳转到录音界面
     * @param position 选择的是网络搜索音乐
     */
    @Override
    public void onItemSelect2(int position) {
        music_name = netMusicListBeen.get(position).getFsong();
        music_singer = netMusicListBeen.get(position).getFsinger();
        music_id = netMusicListBeen.get(position).getF().substring(0, netMusicListBeen.get(position).getF().indexOf("|"));
        Intent intent = new Intent();
        intent.putExtra("music_name", music_name);
        intent.putExtra("music_singer", music_singer);
        intent.putExtra("music_id", music_id);
        setResult(123,intent);
        finish();
    }
}
