package com.example.fluper.clinsher.appActivity.controller.authentication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.fluper.clinsher.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button resetBtn;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog alertDialog;
    private Intent okIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetBtn = findViewById(R.id.forgot_reset_btn);

        okIntent = new Intent(this, LogInActivity.class);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confimationResetEmail();
            }
        });
    }



    public void confimationResetEmail(){


        builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.reset_dialog_layout, null);
        builder.setView(view);
        // b.show();
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOk = view.findViewById(R.id.reset_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(okIntent);
            }
        });alertDialog.show();

    }


}
