package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOpportunityFragmentTwo extends Fragment  {

    private Button btnContinoue;
    private ShareOpportunityActivity shareOpportunityActivity;

    public ShareOpportunityFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        shareOpportunityActivity = (ShareOpportunityActivity)context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_share_opportunity_fragment_two, container, false);

        btnContinoue = view.findViewById(R.id.share_opp_btn_continue);
        btnContinoue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                shareOpportunityActivity.onBtnClick(R.id.share_opp_fragment_container, new ShareOpportunityFragmentThree(),"Share Opportunity");

               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.share_opp_fragment_container, new ShareOpportunityFragmentThree());
                ft.commit();*/
            }
        });

       return view;
    }

}
