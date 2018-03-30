package com.example.fluper.clinsher.appActivity.controller.profilemodule;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.shareopp.ShareOpportunityActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeUserNameFragment extends Fragment {

    private AlertDialog alertDialog;
    private Button btnPostNow;
    private Intent postNowIntent;

    public ChangeUserNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_user_name, container, false);

        // finding id
       // gettingId();
        btnPostNow = view.findViewById(R.id.btn_post_now);

        // All intents

        allIntent();

        //all button clicks
        allBtnClicks();


        showDialog();
        return view;
    }



    //All btn Clicks
    public void allBtnClicks(){

        btnPostNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(postNowIntent);
            }
        });
    }

    // All INtents
    public void allIntent(){
        postNowIntent = new Intent(getContext(), ShareOpportunityActivity.class);

    }

/*
    public void gettingId(){
        btnPostNow = getView().findViewById(R.id.btn_post_now);


    }*/

    private void showDialog() {

        //AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.base_share_layout, null);
        builder.setView(view1);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

}
