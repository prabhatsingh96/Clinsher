package com.example.fluper.clinsher.appActivity.controller.search;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.myjob.MyJobActivity;
import com.example.fluper.clinsher.appActivity.controller.shareopp.ShareOpportunityFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

public class SearchActivity extends AppCompatActivity implements AppUtil.setOnListener{

    private RelativeLayout rl;
    private TextView tvMyJob;
    private Intent intentMyJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        gettingId();

        addFirstFragment();
        allIntent();

        tvMyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentMyJob);
            }
        });


    }

    //all Intent
    public void allIntent(){

      intentMyJob = new Intent(this, MyJobActivity.class);
    }


    //getting id
    public void gettingId(){
        rl = findViewById(R.id.include_search_tool_layout);
        tvMyJob = rl.findViewById(R.id.tv_search_my_job);
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
        ft.add(R.id.search_fragment_container, new SearchFragmentOne());
        ft.commit();
    }

  /*  public void changeToolText(String str)
    {
        rl = findViewById(R.id.include_tool_share_opp);
        tvToolText = rl.findViewById(R.id.share_opp_tool_text);
        tvToolText.setText(str);
    }*/

}
