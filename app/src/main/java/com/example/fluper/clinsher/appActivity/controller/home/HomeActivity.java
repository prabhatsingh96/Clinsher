package com.example.fluper.clinsher.appActivity.controller.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.authentication.LogInActivity;
import com.example.fluper.clinsher.appActivity.controller.signup.SignUpActivity;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity {

   private ViewPager mViewPager;
   private CircleIndicator circleIndicator;
   private Button signUpBtn;
   private Button logInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gettindID();
        setViewPager();

    }

    public void setViewPager(){
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        mViewPager.setPageTransformer(false, new IntroPageTransfer());

        circleIndicator.setViewPager(mViewPager);

      /*  android.support.v4.app.FragmentManager  fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.layout_container, new HomeFragmentOne());
        ft.commit();
*/

    }

    public void gettindID(){
        signUpBtn = findViewById(R.id.sign_up_btn);
        logInBtn = findViewById(R.id.log_in_btn);
        mViewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.indicator);
    }

    public void signUpBtn(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logInBtn(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

}
