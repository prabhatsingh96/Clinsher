package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOpportunityFragmentThree extends Fragment {

    private AlertDialog alertDialog;
    private LinearLayout emoziLayout;
    private ShareOpportunityActivity shareOpportunityActivity;
    private View view;
    private Button btnDone;


    public ShareOpportunityFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        shareOpportunityActivity = (ShareOpportunityActivity)context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_share_opportunity_fragment_three, container,
                 false);
        // changeToolText();

         //getting id
        gettingId();
        showShareOppDialog();

        //all btn clicks
        btnClicksEvent();



        return view;
    }



  /* public void changeToolText(){

       LayoutInflater inflater = getLayoutInflater();
       View view1 = inflater.inflate(R.layout.share_opp_tool_layout,null);
      // rl = view.findViewById(R.id.include_tool_share_opp);
       tvToolText = view1.findViewById(R.id.share_opp_tool_text);
       tvToolText.setText("Rate Your Company");

   }*/




    //all button clicks action
    public void btnClicksEvent(){

        emoziLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareOpportunityActivity.onBtnClick(R.id.share_opp_fragment_container , new RateYourCompanyFragment(), "Rate Your Company");
               // ((ShareOpportunityActivity) getActivity()).tvToolText.setText("Rate your Company");
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.share_opp_fragment_container , new RateYourCompanyFragment());
                ft.commit();*/
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOpportunityActivity.onBtnClick(R.id.share_opp_fragment_container , new ShareOppFragmentfour(), "Share Opportunity");
            }
        });

    }


    // Alll intents
    public void allIntents(){

    }

    //getting id
    public void gettingId(){
        emoziLayout = view.findViewById(R.id.share_opp_emozi_layout);
        btnDone = view.findViewById(R.id.share_opp_btn_done);

    }

    //show share Dialog
    public void showShareOppDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.share_opp_dialog_layout, null);
        builder.setView(view1);
        alertDialog = builder.create();
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}
