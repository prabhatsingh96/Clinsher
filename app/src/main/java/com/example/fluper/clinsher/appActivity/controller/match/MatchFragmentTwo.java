package com.example.fluper.clinsher.appActivity.controller.match;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragmentTwo extends Fragment {

    private Button matchButton;
    private Intent matchButtonIntent;
    private View view;

    public MatchFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_match_fragment_two, container, false);
       gettingId();
       allIntent();
       onButtonClickEvents();

       return view;
    }


    //getting All id

    public void gettingId(){

        matchButton = view.findViewById(R.id.match_btn_match_profile_frg_two);
    }


    //All Intent
    public void allIntent(){

        matchButtonIntent = new Intent(getContext(), MatchProfileActivity.class);
    }


    //All button clicks Event
    public void onButtonClickEvents(){

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(matchButtonIntent);
            }
        });
    }
}
