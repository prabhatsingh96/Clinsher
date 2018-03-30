package com.example.fluper.clinsher.appActivity.controller.myjob;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobFragmentOne extends Fragment {


    public MyJobFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_opp_my_job, container, false);
    }

}
