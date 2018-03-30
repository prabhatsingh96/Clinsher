package com.example.fluper.clinsher.appActivity.controller.match;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

public class MatchProfileActivity extends AppCompatActivity /*implements AppUtil.setOnListener*/{

    private RelativeLayout rl;
    private RelativeLayout nextToolLayout;
    private ImageView toolDrawerImage;
    private ImageView rightArrowImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_profile);
        gettingId();
        buttonClicksEvent();
    }

    //getting id
    public void gettingId(){
        nextToolLayout = findViewById(R.id.match_filter_tool_layout);
        rl = findViewById(R.id.include_match_profile_tool);
        toolDrawerImage = rl.findViewById(R.id.match_profile_drawer_layout);
        rightArrowImage = findViewById(R.id.btn_mm);
    }

    //All button clicks events
    public  void buttonClicksEvent(){

        toolDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextToolLayout.setVisibility(View.VISIBLE);
            }
        });

        rightArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nextToolLayout.setVisibility(View.GONE);
            }
        });
    }

   /* @Override
    public void onBtnClick(int fragmentId , android.support.v4.app.Fragment fragment, String str){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.replace(fragmentId, fragment);
        ft.commit();
        //  changeToolText(str);
    }

    public void addFirstFragment(){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.match_profile_fragment_container, new MatchProfileFragmmentOne());
        ft.commit();
    }*/
}
