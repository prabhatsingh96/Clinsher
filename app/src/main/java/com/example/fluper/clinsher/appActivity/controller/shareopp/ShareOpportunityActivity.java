package com.example.fluper.clinsher.appActivity.controller.shareopp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

public class ShareOpportunityActivity extends AppCompatActivity implements AppUtil.setOnListener{

    private RelativeLayout rl;
    public TextView tvToolText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_opportunity);

        addFirstFragment();

    }

    @Override
    public void onBtnClick(int fragmentId , android.support.v4.app.Fragment fragment, String str){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.replace(fragmentId, fragment);
        ft.commit();
        changeToolText(str);
    }

    public void addFirstFragment(){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.share_opp_fragment_container, new ShareOpportunityFragmentOne());
        ft.commit();
    }

    public void changeToolText(String str)
    {
        rl = findViewById(R.id.include_tool_share_opp);
        tvToolText = rl.findViewById(R.id.share_opp_tool_text);
        tvToolText.setText(str);
    }

    }

