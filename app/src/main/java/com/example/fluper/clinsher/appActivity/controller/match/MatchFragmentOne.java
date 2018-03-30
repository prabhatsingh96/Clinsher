package com.example.fluper.clinsher.appActivity.controller.match;


import android.content.Context;
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
public class MatchFragmentOne extends Fragment {

    private View view;
    private MatchActivity matchActivity;
    private Button btnMatchProfile;

    public MatchFragmentOne() {
        // Required empty public constructor
    }


    public void onAttach(Context context) {


        super.onAttach(context);
        matchActivity = (MatchActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_fragment_one, container, false);

        gettingId();

        btnMatchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchActivity.onBtnClick(R.id.match_fragment_container, new MatchFragmentTwo(),"Match");
            }
        });




        return view;
    }


    //getting id

    public void gettingId(){

        btnMatchProfile = view.findViewById(R.id.btn_match_profile_match_frag_one);
    }
}
