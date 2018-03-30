package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fluper.clinsher.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOppFragmentfour extends Fragment {

    private ShareOpportunityActivity sha;
    private AlertDialog alertDialog;
    private  View view;
    private ImageView moreImage;
    private Button btnPostNow;


    public ShareOppFragmentfour() {
        // Required empty public constructor
    }


    @Override

    public void onAttach(Context context) {

        super.onAttach(context);
        sha = (ShareOpportunityActivity) context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_share_opp_fragmentfour, container, false);

        gettingId();
         moreImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 displayDialog();
             }
         });


      btnPostNow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              sha.onBtnClick(R.id.share_opp_fragment_container , new ShareOppFragmentFive() , "Share Opprtunity");

          }
      });

        return  view;
    }

    // All Intent
    public  void allIntent() {

    }


    public void gettingId(){

     moreImage = view.findViewById(R.id.iv_share_opp_more);
     btnPostNow = view.findViewById(R.id.btn_share_opp_fragment_four_post_now);
    }

    public void displayDialog(){

        //AlertDialog alertDialog;

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.share_opp_edit_dialog, null);
            builder.setView(view1);
            alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        }
    }

