package com.erze.yqj.moudle.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erze.yqj.R;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFriendsFragment extends Fragment {

    EaseContactListFragment contactListFragment;
    Map<String, EaseUser> map = new HashMap<>();
    FragmentTransaction transaction;
    FragmentManager supportFragmentManager;
    public TabFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tab_friends, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //需要设置联系人列表才能启动fragment
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                for (int i = 0; i < usernames.size(); i++) {
                    emitter.onNext(usernames.get(i));
                }
                emitter.onComplete();
            }
        }).compose(Rxschedulers.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        map.put(string, new EaseUser(string));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        contactListFragment= new EaseContactListFragment();
                        contactListFragment.setContactsMap(map);
                        //设置item点击事件
                        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

                            @Override
                            public void onListItemClicked(EaseUser user) {
                                //   startActivity(new Intent(this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
                            }
                        });

                        //通过getSupportFragmentManager启动fragment即可
                        supportFragmentManager = getActivity().getSupportFragmentManager();
                        if (supportFragmentManager!=null){
                            transaction = supportFragmentManager.beginTransaction();
                            transaction.add(R.id.frameLayout1, contactListFragment).commit();
                        }

                    }
                });
    }


}
