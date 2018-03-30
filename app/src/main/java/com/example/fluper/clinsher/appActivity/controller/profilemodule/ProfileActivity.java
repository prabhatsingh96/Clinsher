package com.example.fluper.clinsher.appActivity.controller.profilemodule;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.signup.ShareOpportunityDialogFragment;

import java.util.concurrent.TimeoutException;

public class ProfileActivity extends AppCompatActivity {

    private ImageView settingImage;
    private RelativeLayout rl;
    private TextView userName;
    private TextView addSummary;
    private TextView selceDiscussionTopic;
    private TextView textViewWorkExperience;
    private TextView tvEducationText;
    private TextView tvValues;
    private Intent settingImageIntent;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ImageView cancelImageCurrentLocation;
    private  View view;
    private Button discussionBtnDone;
    private Intent discussionIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.current_position_layout, null);
        gettingId();

        userStatusDialog();

        // inflateLayout(R.layout.signup_user_status_screen_layout);
        discussionIntent = new Intent(this, IncompleteProfileActivity.class);

        cancelImageCurrentLocation.setVisibility(View.GONE);


        //All intent
        settingImageIntent = new Intent(this, ProfileSetting.class);


        textViewWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateLayout(R.layout.work_experience_layout);
            }
        });

        tvEducationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateLayout(R.layout.education_layout);
            }
        });

        addSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateLayout(R.layout.add_your_summary);
            }
        });

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelImageCurrentLocation.setVisibility(View.VISIBLE);
                inflateLayout(R.layout.current_position_layout);
            }
        });



      /*  selceDiscussionTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               View v =  inflateLayout(R.layout.select_your_top_three_discussion_topic_layout);
               discussionBtnDone = v.findViewById(R.id.btn_done_on_discussion_topic);
               discussionBtnDone.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                   }
               });
               startActivity(discussionIntent);
            }
        });*/


        tvValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateLayout(R.layout.select_top_three_values_layout);
            }
        });

        //click on setting button on profile


        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(settingImageIntent);
            }
        });
    }


    //all methods

    public void gettingId(){
        rl = findViewById(R.id.include_profile_tool);
        settingImage = rl.findViewById(R.id.setting_icon);
        userName = rl.findViewById(R.id.profile_name);
        addSummary = rl.findViewById(R.id.add_summury_btn);
        selceDiscussionTopic = findViewById(R.id.discussion_text);
        textViewWorkExperience = findViewById(R.id.work_exp_text);
        tvValues = findViewById(R.id.values_text);
        tvEducationText = findViewById(R.id.education_text);

        cancelImageCurrentLocation = view.findViewById(R.id.cancel_current_position);

    }

    //inflate layouts
    public View inflateLayout(int layout){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return view;
    }

    //fragment of user status dialog
    public void userStatusDialog(){

        ShareOpportunityDialogFragment sh =  new ShareOpportunityDialogFragment();
        android.support.v4.app.FragmentManager  fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction().addToBackStack(null);
        ft.add(R.id.fragment_container_alert_dialog , sh);
        ft.commit();

    }

    public void selectDiscssionTopic(View view) {
        View v =  inflateLayout(R.layout.select_your_top_three_discussion_topic_layout);
        discussionBtnDone = v.findViewById(R.id.btn_done_on_discussion_topic);
        discussionBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(discussionIntent);
            }
        });

    }
}
