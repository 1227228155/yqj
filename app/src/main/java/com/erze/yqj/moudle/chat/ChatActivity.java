package com.erze.yqj.moudle.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.erze.yqj.R;
import com.erze.yqj.moudle.chat.ui.EaseConversationListFragment;
import com.erze.yqj.utils.AutoUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private FragmentTransaction transaction;
    EaseConversationListFragment conversationListFragment;
    private PopupWindow popupWindow;
    private View popuwindowView;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        initView();
        initPopupWindow();
    }

    private void initView() {
        initConversationList();
    }

    private void initConversationList() {
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(ChatActivity.this, EaseChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                intent.putExtra("hxid", conversation.conversationId());
                if (conversation.getLatestMessageFromOthers() != null) {
                    intent.putExtra("username", conversation.getLatestMessageFromOthers().getStringAttribute("nickname", conversation.conversationId()));
                } else {
                    intent.putExtra("username", "陌生人");
                }
                startActivity(intent);
            }
        });
        conversationListFragment.setConversationListLongItemClickListener(new EaseConversationListFragment.EaseConversationListItemLongClickListener() {
            @Override
            public void onListLongItemLongClicked(EMConversation conversation) {
                showPopupWindow(conversation);
            }
        });

        //通过getSupportFragmentManager启动fragment即可
        if (transaction == null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, conversationListFragment).commit();
        }

    }

    private void showPopupWindow(final EMConversation conversation) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(frameLayout, Gravity.BOTTOM, 0, 0);
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
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //删除和某个user会话，如果需要保留聊天记录，传false
                EMClient.getInstance().chatManager().deleteConversation(conversation.getLatestMessageFromOthers().getFrom(), false);
                conversationListFragment.refresh();
            }
        });
    }

    private void initPopupWindow() {
        window = getWindow();
        popuwindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn1.setVisibility(View.GONE);
        pp_btn2.setText("删除当前会话");
        pp_btn3.setText("取消");
    }

    @OnClick(R.id.chat_back)
    public void onViewClicked() {
        finish();
    }
}
