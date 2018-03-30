package com.example.fluper.clinsher.appActivity.controller.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fluper.clinsher.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView toolText;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        inflateLayout(R.layout.forgot_tool_bar_layout);
        getting();
        toolText.setText("Change Password");
    }


    //inflate layout
    private void inflateLayout(int id){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(id,null,false);

    }


    // find layout id

     private void getting(){

         toolText = view.findViewById(R.id.tv_tool_text);
     }
}
