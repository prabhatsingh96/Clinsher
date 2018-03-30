package com.example.fluper.clinsher.appActivity.controller.profilemodule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fluper.clinsher.R;

public class IncompleteProfileActivity extends AppCompatActivity {

    private Button btnShareOpportunity;
    private Intent shareOppIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomplete_profile);
        gettingId();
        allIntent();


        btnShareOpportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(shareOppIntent);
            }
        });
    }

    public void gettingId(){
        btnShareOpportunity = findViewById(R.id.btn_share_opportunity_incomp_profile);
    }

    public void allIntent(){
        shareOppIntent = new Intent(this, ProfileActivity.class);
    }
}
