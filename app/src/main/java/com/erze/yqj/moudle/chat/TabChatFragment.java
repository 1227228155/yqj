package com.erze.yqj.moudle.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erze.yqj.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabChatFragment extends Fragment {

    public TabChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tab_chat, container, false);

        return inflate;
    }


}
