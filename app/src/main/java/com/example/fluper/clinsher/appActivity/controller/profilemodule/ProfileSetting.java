 package com.example.fluper.clinsher.appActivity.controller.profilemodule;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.authentication.ChangePasswordActivity;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

 public class ProfileSetting extends AppCompatActivity {

     private TextView toolTextView;
     private CardView changePassword;
     private Intent changePasswordIntent;
     private RelativeLayout rl;
     private TextView changeUserName;
     private TextView btnPrivacyPolicy;
     private TextView btnTermsUses;
     private Intent intentPrivacyPolicy;
     private Intent intentTermsOfUse;
     private Button btnLogOut;
     private AlertDialog alertDialog;
     private AlertDialog.Builder builder;
     private Button deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        changeToolText();
        gettingId();
        allIntents();
       // LayoutInflater inflater = LayoutInflater.from(this);
       // View view = inflater.inflate(R.layout.forgot_tool_bar_layout,null);


        changePasswordIntent = new Intent(this,ChangePasswordActivity.class);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(changePasswordIntent);
            }
        });

        // change user name

        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserName();
                RelativeLayout rl = findViewById(R.id.aaaaaa);
                rl.setVisibility(View.GONE);

            }
        });

        btnTermsUses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTermsOfUse);

            }
        });


        btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentPrivacyPolicy);

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               inflateLayout(R.layout.log_out_dialog_layout);
            }
        });



        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateLayout(R.layout.delete_profile_layout);
            }
        });

    }



     //inflate layouts
     public View inflateLayout(int layout){

         builder  = new AlertDialog.Builder(this);
         LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
         View view = inflater.inflate(layout, null);
         builder.setView(view);
         alertDialog = builder.create();
         alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         alertDialog.show();
         return view;
     }


    public void gettingId(){


        changePassword = findViewById(R.id.card_change_password);

        changeUserName  = findViewById(R.id.change_user_name_profile_setting);
        btnPrivacyPolicy = findViewById(R.id.tv_setting_privacy_policy);
        btnTermsUses = findViewById(R.id.tv_setting_terms_use);
        btnLogOut = findViewById(R.id.logout_profile_change);
        deleteAccount = findViewById(R.id.delete_account);

    }

    public void changeToolText(){
        rl = findViewById(R.id.include_tool_profile_setting);
        toolTextView = rl.findViewById(R.id.tv_tool_text);
        toolTextView.setText("Setting");
    }


     public void changeUserName(){
         ChangeUserNameFragment frg = new ChangeUserNameFragment();
         android.support.v4.app.FragmentManager  fm = getSupportFragmentManager();
         android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
         ft.add(R.id.fragment_container_change_user_name, frg);
         ft.commit();

     }


     // Fiind All Intents

     public void allIntents(){

         intentPrivacyPolicy = new Intent(this, PricacyPolicyActivity.class);
         intentTermsOfUse = new Intent(this, TermsOfUseActivity.class);


     }


/*
     public void onBtnClickk(int fragmentId , android.support.v4.app.Fragment fragment, String str){

         android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
         FragmentTransaction ft = frg.beginTransaction();
         ft.replace(fragmentId, fragment);
         ft.commit();
           changeToolText(str);
     }*/

    /* public void jobResion(){
        BaseShareOppFragment baseOpp  = new BaseShareOppFragment();
         android.support.v4.app.FragmentManager  fm = getSupportFragmentManager();
         android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
         ft.replace(R.id.fragment_container_change_user_name, baseOpp);
         ft.commit();

     }*/


 }
