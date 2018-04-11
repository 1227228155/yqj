package com.erze.yqj.moudle.comment.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.moudle.comment.reply.ReplyActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 1227228155@qq.com on 2017/8/4.
 */

public class CommentAdapter extends BaseAdapter {
    Context mContext;
    CommentBean.ResultBean bean;
    List<CommentBean.ResultBean.ListBean> listBeen = new ArrayList<>();
    Map<String, String> map = new ArrayMap<>();
    SPUtils spUtils = SPUtils.getInstance("user");
    public CommentAdapter(Context context,CommentBean.ResultBean bean) {
        this.mContext = context;
        this.bean = bean;
       if (EmptyUtils.isNotEmpty(bean.getList())){
           listBeen = bean.getList();
       }
    }

    public void addAllData(CommentBean.ResultBean bean) {
        listBeen.addAll(bean.getList());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listBeen.size()==0?0:listBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final CommentBean.ResultBean.ListBean listBean = listBeen.get(position);
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_list, null);
            AutoUtils.auto(convertView);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.context = (TextView) convertView.findViewById(R.id.context);
            holder.other_name= (TextView) convertView.findViewById(R.id.other_name);
            holder.msg_count = (TextView) convertView.findViewById(R.id.msg_count);
            holder.dz_count = (TextView) convertView.findViewById(R.id.dz_count);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(listBean.getUser_nickname());
        holder.other_name.setText(listBean.getReply_nickname());
        if (listBean.getReply_count().equals("0")){

        }else {
            holder.msg_count.setText("共"+listBean.getReply_count()+"条回复");
            holder.iv1.setVisibility(View.VISIBLE);
            holder.tv1.setVisibility(View.VISIBLE);
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
        return convertView;
    }



    class ViewHolder{
        ImageView avatar,iv1;
        TextView name,context,other_name,msg_count,dz_count,tv1;
    }
}
