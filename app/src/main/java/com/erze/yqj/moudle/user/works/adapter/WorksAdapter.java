package com.erze.yqj.moudle.user.works.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.entity.UserWorksBean;
import com.erze.yqj.moudle.comment.CommentActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/8/18.
 */

public class WorksAdapter extends RecyclerView.Adapter {
    Context context;
    List<UserWorksBean.ResultBean> list;
    SPUtils spUtils = SPUtils.getInstance("user");
    public WorksAdapter(Context context,List<UserWorksBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }
    public void initAdapter(List<UserWorksBean.ResultBean> list){
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }
    public void  addAdapter (List<UserWorksBean.ResultBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_other_follow, null);
        AutoUtils.auto(inflate);
        return new WorksViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            WorksViewHolder viewHolder = (WorksViewHolder) holder;
        Glide.with(context)
                .load(list.get(position).getVideo_img())
                .into((viewHolder.imageView));
        viewHolder.eye.setText(list.get(position).getVideo_watch_times());
        viewHolder.time.setText(list.get(position).getVideo_t());
        if (list.get(position).getVideo_type().equals("1")){
        }else {
            viewHolder.type.setImageResource(R.mipmap.type_voice);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserWorksBean.ResultBean resultBean = list.get(position);
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("video_url", resultBean.getVideo_url());
                intent.putExtra("pic_url", resultBean.getVideo_img());
                intent.putExtra("title", resultBean.getVideo_description());
                intent.putExtra("video_id", resultBean.getVideo_id());
                intent.putExtra("username", spUtils.getString("username"));
                intent.putExtra("userid", spUtils.getString("userid"));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WorksViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,type;
        TextView eye,time;
        public WorksViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            eye = (TextView) itemView.findViewById(R.id.eye);
            time = (TextView) itemView.findViewById(R.id.time);
            type = (ImageView) itemView.findViewById(R.id.type);
        }
    }
}
