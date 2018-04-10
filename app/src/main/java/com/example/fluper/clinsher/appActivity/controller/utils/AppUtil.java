package com.example.fluper.clinsher.appActivity.controller.utils;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;

/**
 * Created by fluper on 7/3/18.
 */

public class AppUtil {

    private static ProgressDialog prd;

    public interface setOnListener{

        public void onBtnClick(int fragmentid, android.support.v4.app.Fragment fragment , String str);
    }


    public static void showProgressDialog(final Context context){
        prd = new ProgressDialog(context);
        prd.setTitle("Loading");
        prd.setMessage("Please wait");
        prd.show();
    }

    public static void dismiss(Context context){
        prd.dismiss ();
    }

}
