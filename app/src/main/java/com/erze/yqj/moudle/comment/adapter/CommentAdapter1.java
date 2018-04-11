package com.erze.yqj.moudle.comment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.moudle.comment.reply.ReplyActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by 1227228155@qq.com on 2017/8/4.
 */

public class CommentAdapter1 extends RecyclerView.Adapter<CommentAdapter1.ViewHolder> {
    Context mContext;
    CommentBean.ResultBean bean;
    Map<String, String> map = new ArrayMap<>();
    SPUtils spUtils = SPUtils.getInstance("user");
    public CommentAdapter1(Context context, CommentBean.ResultBean bean) {
        this.mContext = context;
        this.bean = bean;
    }

    public void addAllData( List<CommentBean.ResultBean.ListBean> dataList) {
        this.bean.getList().addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.bean.getList().clear();
    }



    @Override
    public int getItemCount() {
        return bean.getList().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(mContext).inflate(R.layout.item_comment_list, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CommentBean.ResultBean.ListBean listBean = bean.getList().get(position);
        holder.name.setText(listBean.getUser_nickname());
        holder.other_name.setText(listBean.getReply_nickname());
        if (listBean.getReply_count().equals("0")){
            holder.iv1.setVisibility(View.GONE);
            holder.tv1.setVisibility(View.GONE);
        }else {
            holder.msg_count.setText("共"+listBean.getReply_count()+"条回复");
        }
        holder.context.setText(new String(EncodeUtils.base64Decode(listBean.getComments_contents())));
        holder.dz_count.setText(listBean.getComm_upvote_num());
        holder.msg_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( mContext, ReplyActivity.class);
                intent.putExtra("bean", listBean);
                mContext.startActivity(intent);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar,iv1;
        TextView name,context,other_name,msg_count,dz_count,tv1;
        public ViewHolder(View itemView) {
            super(itemView);
              name = (TextView) itemView.findViewById(R.id.name);
                context = (TextView) itemView.findViewById(R.id.context);
               other_name= (TextView) itemView.findViewById(R.id.other_name);
              msg_count = (TextView) itemView.findViewById(R.id.msg_count);
                 dz_count = (TextView) itemView.findViewById(R.id.dz_count);
             tv1 = (TextView) itemView.findViewById(R.id.tv1);
             iv1 = (ImageView) itemView.findViewById(R.id.iv1);
        }
    }
}
