package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.match.MatchActivity;
import com.example.fluper.clinsher.appActivity.controller.match.MatchFragmentTwo;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOppMyPostFragment extends Fragment {


    private View view;

    public ShareOppMyPostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_share_opp_my_post, container, false);



        return view;
    }


}
