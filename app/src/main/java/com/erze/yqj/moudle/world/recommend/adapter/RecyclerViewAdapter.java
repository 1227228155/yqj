package com.erze.yqj.moudle.world.recommend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.moudle.comment.CommentActivity;
import com.erze.yqj.moudle.follow.OtherFollowActivity;
import com.erze.yqj.moudle.report.ReportActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;
import com.erze.yqj.utils.ConvertUtils;
import com.erze.yqj.utils.MFGT;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<WorldBean.ResultBean.VideoListBean> video_list ;//recyclerView数据集合
    private View popuwindowView;
    private PopupWindow popupWindow;
    private Button pp_btn1,pp_btn2,pp_btn3;
    private Window window;
    private Map<String, String> map = new ArrayMap<>();
    private SPUtils spUtils = SPUtils.getInstance("user");
    private String video_id;//资源ID
    private View popupView;
    boolean update = false;
    private  int type;
    public RecyclerViewAdapter(Context context, List<WorldBean.ResultBean.VideoListBean> video_list,Window window, View view) {
        this.context = context;
        this.video_list = video_list;
        this.window = window;
        this.popupView = view;
    }
    public void initAdapter( List<WorldBean.ResultBean.VideoListBean> video_list){
        this.video_list = video_list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        AutoUtils.auto(view);
        popuwindowView = LayoutInflater.from(context).inflate(R.layout.popuwindow, null);
        AutoUtils.auto(popuwindowView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        WindowManager windowManager = (WindowManager) context.
                getSystemService(context.WINDOW_SERVICE);
        int screenWidth = (int) (windowManager.getDefaultDisplay().getWidth()*0.5);
        int screenHeight = (int) (windowManager.getDefaultDisplay().getHeight()*0.5);
        int screenHeight1 = (int) (windowManager.getDefaultDisplay().getHeight()*0.4);
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(screenWidth,screenHeight);
        if (position%2==0){
            layoutParams.height = screenHeight1;
        }
        String userLabel = video_list.get(position).getUser_label();
        String[] split = userLabel.split(",");
        for (int i = 0; i < split.length; i++) {
            ArrayList<String> arrayList = ConvertUtils.array2List(split);
            holder.tagFlowLayout.setAdapter(new TagAdapter<String>(arrayList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_no_selector, holder.tagFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
        holder.seeCount.setText(video_list.get(position).getVideo_watch_times());
        holder.textView.setText(video_list.get(position).getUser_nickname());
        Glide.with(context).load(video_list.get(position).getUser_pic()).into(holder.avatar);
        holder.linearLayout.setLayoutParams(layoutParams);
        Glide.with(context).load(video_list.get(position).getVideo_img()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherFollowActivity.class);
                intent.setAction(video_list.get(position).getUser_id());
                context.startActivity(intent);
            }
        });
        holder.jubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnPopupWindowListener(position);
            }
        });
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                WorldBean.ResultBean.VideoListBean resultBean = video_list.get(position);
                intent.putExtra("video_url",  resultBean.getVideo_url());
                intent.putExtra("pic_url",  resultBean.getVideo_img());
                intent.putExtra("title",  resultBean.getVideo_description());
                intent.putExtra("video_id",  resultBean.getVideo_id());
                intent.putExtra("username", resultBean.getUser_nickname());
                intent.putExtra("userid", resultBean.getUser_id());
                MFGT.startActivity(context,intent);
            }
        });
    }
    private void setOnPopupWindowListener(final int positon1) {
        WindowManager.LayoutParams lp =window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp =window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        pp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                map.put("user_id", spUtils.getString("userid"));
                map.put("atten_user_id",video_list.get(positon1).getUser_id() );
                map.put("type", "y");
                popupWindow.dismiss();
                NetDao.getInstance().getCommonApi().getFollowStatus(map).compose(Rxschedulers.<CodeBean>io_main())
                        .subscribe(new Consumer<CodeBean>() {
                            @Override
                            public void accept(@NonNull CodeBean bean) throws Exception {
                                    if (bean.getCode().equals("200")){
                                        Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, "已经关注过啦", Toast.LENGTH_SHORT).show();
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

                            }
                        });
            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent( context, ReportActivity.class);
                WorldBean.ResultBean.VideoListBean resultBean = video_list.get(positon1);
                intent.putExtra("video_url",  resultBean.getVideo_url());
                intent.putExtra("pic_url",  resultBean.getVideo_img());
                intent.putExtra("title",  resultBean.getVideo_description());
                intent.putExtra("video_id",  resultBean.getVideo_id());
                intent.putExtra("username", resultBean.getUser_nickname());
                intent.putExtra("userid", resultBean.getUser_id());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return video_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;
        TextView textView,seeCount;
        RelativeLayout jubao;
        RelativeLayout play;
        ImageView imageView;
        ImageView avatar;
        TagFlowLayout tagFlowLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            seeCount = (TextView) itemView.findViewById(R.id.item_video_see_count);
            tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.flowlayout);
            avatar = (ImageView) itemView.findViewById(R.id.item_voide_avatar);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.video_ll);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.video_rl);
            textView = (TextView) itemView.findViewById(R.id.item_video_username);
            jubao = (RelativeLayout) itemView.findViewById(R.id.rl_more);
            play = (RelativeLayout) itemView.findViewById(R.id.play_rl);
            pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
            pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
            pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
            pp_btn1.setText("关注");
        }
    }
}
