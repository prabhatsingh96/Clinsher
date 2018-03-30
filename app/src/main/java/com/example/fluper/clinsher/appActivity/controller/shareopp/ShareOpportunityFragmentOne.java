package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOpportunityFragmentOne extends Fragment {


    private Button btnContinoue;

    public ShareOpportunityFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view  =   inflater.inflate(R.layout.fragment_share_opportunity_fragment_one, container, false);

       btnContinoue = view.findViewById(R.id.share_opp_btn_continue_one);
       btnContinoue.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               FragmentTransaction ft = fm.beginTransaction();
               ft.replace(R.id.share_opp_fragment_container, new ShareOpportunityFragmentTwo());
               ft.commit();
           }
       });


      return view;
    }

}
