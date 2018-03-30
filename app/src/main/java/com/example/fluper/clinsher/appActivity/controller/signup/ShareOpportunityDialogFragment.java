package com.example.fluper.clinsher.appActivity.controller.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.profilemodule.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOpportunityDialogFragment extends Fragment {

    private Button shareOpportunity;
    private RelativeLayout rl;
  // private Intent shareOpportunityIntent;

    public ShareOpportunityDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_opportunity_dialog_fragment, container, false);
        rl = view.findViewById(R.id.include_signup_user_status);
        shareOpportunity = rl.findViewById(R.id.btn_share_opportunity_alert_dialog);

      //  shareOpportunityIntent = new Intent(getContext(), ProfileActivity.class);
        shareOpportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().onBackPressed();
            }
        });


        return view;
    }

}
