package com.erze.yqj.moudle.follow.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.moudle.comment.CommentActivity;
import com.erze.yqj.moudle.report.ReportActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 1227228155@qq.com on 2017/7/20.
 */

public class FollowAdapter extends BaseAdapter {
    private Context context;
   private List<FollowBean.ResultBean> resultBeanList;
    private  View popuwindowView;
    private PopupWindow popupWindow;
    private Button pp_btn1,pp_btn2,pp_btn3;
    private Window window;
    private String video_id;//资源的ID
    private String up_user_id;
    private Map<String, String> map = new ArrayMap<>();
    private SPUtils spUtils = SPUtils.getInstance("user");
    private boolean isDZ =false;//判断点赞和取消点赞的 标志位  false为没点赞状态
    private String isContext;//判断当前adapter是关注页面还是收藏页面的adapter
    private View popupView;
    public FollowAdapter(Context context,   List<FollowBean.ResultBean> resultBeanList, Window window,String isContext,View view) {
        this.context = context;
        this.resultBeanList = resultBeanList;
        this.window = window;
        this.isContext = isContext;
        popupView =view;
    }
    public void  addAdapter( List<FollowBean.ResultBean> resultBeanList){
        this.resultBeanList.addAll(resultBeanList);
        notifyDataSetChanged();
    }
    public void  deleteAdapter(){
        this.resultBeanList.clear();
        notifyDataSetChanged();
    }
    public void  initAdapter( List<FollowBean.ResultBean> resultBeanList1){
        resultBeanList.clear();
        resultBeanList = resultBeanList1;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return resultBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_follow, null);
            AutoUtils.auto(convertView);
            holder.other = (ImageView) convertView.findViewById(R.id.item_follow_more);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.item_follow_avatar);
            holder.fx = (ImageView) convertView.findViewById(R.id.item_follow_fx);
            holder.dz = (ImageView) convertView.findViewById(R.id.item_follow_dz);
            holder.dzCount = (TextView) convertView.findViewById(R.id.item_follow_dz_count);
            holder.xxCount = (TextView) convertView.findViewById(R.id.item_follow_xx_count);
            holder.username = (TextView) convertView.findViewById(R.id.item_follow_username);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.fm = (ImageView) convertView.findViewById(R.id.item_follow_video);
            popuwindowView = LayoutInflater.from(context).inflate(R.layout.popuwindow, null);
            pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
            pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
            pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
            if (isContext.equals("收藏")){
                pp_btn1.setText("取消收藏");
            }
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        up_user_id = resultBeanList.get(position).getUser_id();
        holder.username.setText(resultBeanList.get(position).getUser_nickname());
        holder.title.setText(resultBeanList.get(position).getVideo_description());
        Glide.with(context).load(resultBeanList.get(position).getUser_pic()).into(holder.avatar);
        Glide.with(context).load(resultBeanList.get(position).getVideo_img()).into(holder.fm);
        video_id = resultBeanList.get(position).getVideo_id();
        holder.dzCount.setText(resultBeanList.get(position).getVideo_upvote_num());
        holder.xxCount.setText(resultBeanList.get(position).getVideo_comment_num());
        String upvoteStatus = resultBeanList.get(position).getUpvote_status();
        if (upvoteStatus.equals("n")){
            holder.dz.setImageResource(R.mipmap.item_follow_dz);
        }else {
            holder.dz.setImageResource(R.mipmap.gz);
        }
        holder.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setOnPopupWindowListener(position);
            }
        });
        holder.fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                FollowBean.ResultBean resultBean = resultBeanList.get(position);
                intent.putExtra("video_url",  resultBean.getVideo_url());
                intent.putExtra("pic_url",  resultBean.getVideo_img());
                intent.putExtra("title",  resultBean.getVideo_description());
                intent.putExtra("video_id",  resultBean.getVideo_id());
                intent.putExtra("username", resultBean.getUser_nickname());
                context.startActivity(intent);
            }
        });
        holder.fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShare();
            }
        });

        holder.dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDZ){
                    holder.dz.setImageResource(R.mipmap.item_follow_dz);
                    map.clear();
                    map.put("video_id", video_id);
                    map.put("type", "u");
                    map.put("up_user_id", spUtils.getString("userid"));
                    map.put("user_id",up_user_id);
                    map.put("up_type", "n");
                    NetDao.getInstance().getCommonApi().getVideoTimes(map).compose(Rxschedulers.<Code>io_main());
                    isDZ = false;
                }else {
                    holder.dz.setImageResource(R.mipmap.gz);
                    map.clear();
                    map.put("video_id", video_id);
                    map.put("type", "u");
                    map.put("up_user_id", spUtils.getString("userid"));
                    map.put("user_id",up_user_id);
                    map.put("up_type", "y");
                    NetDao.getInstance().getCommonApi().getVideoTimes(map).compose(Rxschedulers.<Code>io_main());
                    isDZ = true;
                }
            }
        });
        return convertView;
    }

    private void initShare() {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions((Activity) context,mPermissionList,123);
        }

        UMWeb web = new UMWeb("http://www.yiqijun.com/");
        web.setTitle("艺气君");//标题
        //   web.setThumb(thumb);  //缩略图
        web.setDescription("哈哈哈哈哈");//描述
        new ShareAction((Activity) context).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context,"成功了",Toast.LENGTH_LONG).show();
            map.clear();
            map.put("video_id", video_id);
            map.put("type", "s");
            NetDao.getInstance().getCommonApi().getVideoTimes(map);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    private void setOnPopupWindowListener(final int positon1) {
        WindowManager.LayoutParams lp =window.getAttributes();
        lp.alpha = 0.5f;
         window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM,0,0);
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
                popupWindow.dismiss();
                if (isContext.equals("关注")){
                    cancelFollow(positon1);//取消关注
                }else {
                    cancelCollection(positon1);//取消收藏
                }
            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent( context, ReportActivity.class);
                FollowBean.ResultBean resultBean = resultBeanList.get(positon1);
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

    private void cancelCollection(final int positon1) {
        map.clear();
        map.put("user_id", spUtils.getString("userid"));
        map.put("video_id", resultBeanList.get(positon1).getVideo_id());
        map.put("type", "n");
        NetDao.getInstance().getCommonApi().getCollection(map).compose(Rxschedulers.<Code>io_main())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(@NonNull Code code) throws Exception {
                            if (code.getCode().equals("200")){
                                resultBeanList.remove(positon1);
                                notifyDataSetChanged();
                                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    private void cancelFollow(final int positon1) {
        map.clear();
        map.put("user_id", spUtils.getString("userid"));
        map.put("atten_user_id", resultBeanList.get(positon1).getUser_id());
        map.put("type", "n");
        NetDao.getInstance().getCommonApi().getFollowStatus(map).compose(Rxschedulers.<CodeBean>io_main())
                .subscribe(new Consumer<CodeBean>() {
                    @Override
                    public void accept(@NonNull CodeBean bean) throws Exception {
                        if (bean.getCode().equals("200")){
                            resultBeanList.remove(positon1);
                            notifyDataSetChanged();
                            Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, bean.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    class ViewHolder{
        TextView username;
        TextView dzCount;
        TextView xxCount;
        ImageView other; //弹出popupWindow。取消关注和举报选项
        ImageView fx;//分享按钮
        ImageView dz;//点赞按钮
        ImageView fm;//视频封面
        TextView title;//视频标题
        RelativeLayout rl;
        CircleImageView avatar;
    }
}
