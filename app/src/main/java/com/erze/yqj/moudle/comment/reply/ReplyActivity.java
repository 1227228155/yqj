package com.erze.yqj.moudle.comment.reply;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.ReplyBean;
import com.erze.yqj.entity.ThirdReply;
import com.erze.yqj.moudle.comment.TextEditTextView;
import com.erze.yqj.moudle.comment.adapter.ReplyAdapter;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ReplyActivity extends BaseFrameActivity<ReplyPresenter, ReplyModel> implements ReplyContract.View {

    @BindView(R.id.reply_listView)
    ListView replyListView;
    CommentBean.ResultBean.ListBean bean;
    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.send_msg_rl)
    RelativeLayout sendMsgRl;
    @BindView(R.id.editText)
    TextEditTextView editText;
    private PopupWindow popupWindow;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private View popuwindowView;
    private Map<String, String> map = new ArrayMap<>();
    private SPUtils spUtils = SPUtils.getInstance("user");
    private List<ReplyBean.ResultBean> resultBeanList;
    private int position;
    private  Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        bean = (CommentBean.ResultBean.ListBean) getIntent().getSerializableExtra("bean");
        commonTitle.setText("查看回复");
        initData1();
        initPopupWindow();
        initListener1();
    }

    private void initData1() {
        map.put("page", "1");
        map.put("type", "r");
        map.put("comm_id", bean.getComments_id());
        map.put("b_reply_id", bean.getUser_id());
        mPresenter.getReply(map);
    }

    private void initPopupWindow() {
        popuwindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn2.setText("回复");
        pp_btn1.setVisibility(View.GONE);
        editText.setView(null, sendMsgRl);
    }

    private void initListener1() {
        replyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                setOnPopupWindowListener();
                position  = i;
                return false;
            }
        });
    }

    private void setOnPopupWindowListener() {
        final Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(replyListView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
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

        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ejectKeyboard();
                popupWindow.dismiss();

            }
        });
    }
    /**
     * 弹出键盘
     */
    private void ejectKeyboard() {
        sendMsgRl.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @Override
    public void getReplyBean(ReplyBean bean) {
        resultBeanList = bean.getResult();
        ReplyAdapter replyAdapter = new ReplyAdapter(this, resultBeanList);
        replyListView.setAdapter(replyAdapter);
    }

    @OnClick({R.id.common_back, R.id.send_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.send_comment:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        String str = editText.getText().toString().trim();
        if (EmptyUtils.isNotEmpty(str)){
            map.clear();
            map.put("type", "sr");
            map.put("uid", spUtils.getString("userid"));
            map.put("user_id", resultBeanList.get(position).getUser_id());
            map.put("reply_id", resultBeanList.get(position).getReply_id());
            map.put("content", str);
           disposable = NetDao.getInstance().getCommonApi().sendReply2(map).compose(Rxschedulers.<ThirdReply>io_main()).subscribe(new Consumer<ThirdReply>() {
                @Override
                public void accept(@NonNull ThirdReply thirdReply) throws Exception {
                    if (thirdReply.getCode().equals("200")) {
                        sendMsgRl.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(ReplyActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                        editText.setText("");
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
                    initData1();
                }
            });
        }else {
            Toast.makeText(this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null){
            disposable.dispose();
            disposable=null;
        }
    }
}
