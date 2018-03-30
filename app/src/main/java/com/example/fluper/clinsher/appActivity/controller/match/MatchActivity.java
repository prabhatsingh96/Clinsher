package com.example.fluper.clinsher.appActivity.controller.match;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

public class MatchActivity extends AppCompatActivity implements AppUtil.setOnListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        addFirstFragment();

    }



    @Override
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
        ft.add(R.id.match_fragment_container, new MatchFragmentOne());
        ft.commit();
    }


}
