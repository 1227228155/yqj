package com.erze.yqj.moudle.user.followlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.moudle.follow.OtherFollowActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by 1227228155@qq.com on 2017/7/22.
 */

public class FollowListAdapter  extends BaseAdapter{
    Context context;
    List<FansListBean.ResultBean.ListBean> list;
    String followOrfans;
    Map<String, String> map = new ArrayMap<>();
    SPUtils spUtils = SPUtils.getInstance("user");
    public FollowListAdapter(Context context, List<FansListBean.ResultBean.ListBean> list,String followOrfans) {
        this.context = context;
        this.list = list;
        this.followOrfans=  followOrfans;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow_list, null);
            AutoUtils.auto(convertView);
            holder.name = (TextView) convertView.findViewById(R.id.item_follow_list_name);
            holder.age = (TextView) convertView.findViewById(R.id.item_follow_list_age);
            holder.sex = (ImageView) convertView.findViewById(R.id.item_follow_list_sex);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl);
            holder.cancel = (RelativeLayout) convertView.findViewById(R.id.item_follow_list_cancel);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv1);
            holder.text = (TextView) convertView.findViewById(R.id.item_follow_list_cancelFollow);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.item_follow_list_avatar);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherFollowActivity.class);
                intent.setAction(list.get(position).getUser_id());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(list.get(position).getUser_pic()).into(holder.avatar);
        holder.name.setText(list.get(position).getUser_nickname());
        holder.age.setText(list.get(position).getUser_age());
        if (list.get(position).getUser_sex().equals("男")){
            holder.sex.setImageResource(R.drawable.sex_nan);
        }else {
            holder.sex.setImageResource(R.drawable.sex_nv);
        }
        //如果是当作关注页面的adapter执行下面的监听，否则是粉丝列表的adapter执行else里的监听
        if (followOrfans.equals("follow")){
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.clear();
                    map.put("user_id", spUtils.getString("userid"));
                    map.put("atten_user_id",list.get(position).getUser_id());
                    map.put("type", "n");
                   NetDao.getInstance().getCommonApi().getFollowStatus(map).compose(Rxschedulers.<CodeBean>io_main()).subscribe(new Consumer<CodeBean>() {
                       @Override
                       public void accept(@NonNull CodeBean bean) throws Exception {
                            if (bean.getCode().equals("200")){
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                       }
                   });
                }
            });
        }else {
            if (list.get(position).getAtten_status().equals("y")){
                holder.imageView.setImageResource(R.mipmap.item_fanslist_iv2);
                holder.text.setText("已关注");
            }else {
                holder.imageView.setImageResource(R.mipmap.item_fanslist_iv1);
                holder.text.setText("关注");
            }
        }
        return convertView;
    }



    class ViewHolder{
        CircleImageView avatar;
        ImageView sex;
        TextView name;
        RelativeLayout relativeLayout;
        RelativeLayout cancel;
        TextView age;
        TextView text;//关注的文字
        ImageView imageView;//关注的图片

    }
}
