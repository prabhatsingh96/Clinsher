package com.example.fluper.clinsher.appActivity.controller.profilemodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;

public class PricacyPolicyActivity extends AppCompatActivity {

    private RelativeLayout rl;
    private TextView toolTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricacy_policy);
        changeToolText();
    }




    public void changeToolText(){
        rl = findViewById(R.id.include_privacy_tool);
        toolTextView = rl.findViewById(R.id.tv_tool_text);
        toolTextView.setText("Privacy Policy");
    }
}
