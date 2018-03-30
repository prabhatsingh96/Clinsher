package com.example.fluper.clinsher.appActivity.controller.startapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private Intent intentspalsh;
    private ImageView splashImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.
                LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);
       // splashImageView = findViewById(R.id.splash_icon);

        intentspalsh = new Intent(this, HomeActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(4000);
                    startActivity(intentspalsh);

                    finish();

                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }

            }
        }).start();
    }
}
