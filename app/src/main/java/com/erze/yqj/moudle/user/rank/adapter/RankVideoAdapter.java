package com.erze.yqj.moudle.user.rank.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
import com.erze.yqj.entity.Code;
import com.erze.yqj.entity.HotVideoBean;
import com.erze.yqj.moudle.comment.CommentActivity;
import com.erze.yqj.moudle.report.ReportActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;
import com.github.czy1121.view.CornerLabelView;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/8/21.
 */

public class RankVideoAdapter extends RecyclerView.Adapter<RankVideoAdapter.ViewHolder> {
    Context context;
    List<HotVideoBean.ResultBean>  video_list ;//recyclerView数据集合
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
    public RankVideoAdapter(Context context,List<HotVideoBean.ResultBean> result) {
        this.context = context;
        video_list = result;
    }
    public void initAdapter( List<HotVideoBean.ResultBean>  video_list){
        this.video_list = video_list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_follow, null);
        AutoUtils.auto(view);
        popuwindowView = LayoutInflater.from(context).inflate(R.layout.popuwindow, null);
        AutoUtils.auto(popuwindowView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int color;
        switch (position){
            case 0:
                color = Color.parseColor("#ff7272");
                break;
            case 1:
                color = Color.parseColor("#f8b551");
                break;
            case 2:
                color = Color.parseColor("#cea8fb");
                break;
            default:
                color = Color.parseColor("#c5c2c6");
                break;
        }
        initSlantedTextView(holder, position,color);
        HotVideoBean.ResultBean resultBean = video_list.get(position);
        Glide.with(context).load(resultBean.getUser_pic()).into(holder.avatar);
        Glide.with(context).load(resultBean.getVideo_img()).into(holder.fm);
        holder.username.setText(resultBean.getUser_nickname());
        holder.xxCount.setText(resultBean.getVideo_comment_num());
        holder.dzCount.setText(resultBean.getVideo_upvote_num());
        if (resultBean.getVideo_type().equals("1")){
        }else {
            holder.type.setImageResource(R.mipmap.type_voice);
        }

        holder.see.setText(resultBean.getVideo_watch_times());
        holder.fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotVideoBean.ResultBean resultBean1 = video_list.get(position);
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("video_url", resultBean1.getVideo_url());
                intent.putExtra("pic_url", resultBean1.getVideo_img());
                intent.putExtra("video_id", resultBean1.getVideo_id());
                intent.putExtra("username", spUtils.getString("username"));
                intent.putExtra("userid", spUtils.getString("userid"));
                context.startActivity(intent);
            }
        });
    }

    private void initSlantedTextView(ViewHolder holder, int position,int color) {
        int number = position+1;
        holder.slantedTextView.setText1("No." + number);
        holder.slantedTextView.setFillColor(color);
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
                map.put("video_id", video_id);
                map.put("type", "y");
                popupWindow.dismiss();
                NetDao.getInstance().getCommonApi().getCollection(map).compose(Rxschedulers.<Code>io_main()).subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(@NonNull Code code) throws Exception {
                        if (code.getCode().equals("200")){
                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent( context, ReportActivity.class);
                intent.putExtra("bean", video_list.get(positon1));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return video_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView dzCount;
        TextView xxCount;
        ImageView other; //弹出popupWindow。取消关注和举报选项
        ImageView fx;//分享按钮
        ImageView dz;//点赞按钮
        ImageView fm;//视频封面
        TextView title;//视频标题
        ImageView type;
        RelativeLayout rl;
        CircleImageView avatar;
        CornerLabelView slantedTextView;
        TextView see;
        public ViewHolder(View itemView) {
            super(itemView);
            see = (TextView) itemView.findViewById(R.id.item_video_see_count);
            type = (ImageView) itemView.findViewById(R.id.item_video_type);
            slantedTextView = (CornerLabelView) itemView.findViewById(R.id.cornerLabel);
            slantedTextView.setVisibility(View.VISIBLE);
            other = (ImageView) itemView.findViewById(R.id.item_follow_more);
            avatar = (CircleImageView) itemView.findViewById(R.id.item_follow_avatar);
            fx = (ImageView) itemView.findViewById(R.id.item_follow_fx);
            dz = (ImageView) itemView.findViewById(R.id.item_follow_dz);
             dzCount = (TextView) itemView.findViewById(R.id.item_follow_dz_count);
             xxCount = (TextView) itemView.findViewById(R.id.item_follow_xx_count);
             username = (TextView) itemView.findViewById(R.id.item_follow_username);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            title = (TextView) itemView.findViewById(R.id.title);
            fm = (ImageView) itemView.findViewById(R.id.item_follow_video);
            popuwindowView = LayoutInflater.from(context).inflate(R.layout.popuwindow, null);
            pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
            pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
            pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        }
    }
}
