package com.example.fluper.clinsher.appActivity.controller.shareopp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.match.MatchActivity;
import com.example.fluper.clinsher.appActivity.controller.search.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOppFragmentFive extends Fragment {

    private ImageView ivMyPost;
    private ImageView ivSearch;
    private ImageView ivMatch;
    private Intent matchIntent;;
    private LinearLayout bottomLayout;
    private Intent searchIntent;
    private View view;
    private ShareOpportunityActivity sh;

    public ShareOppFragmentFive() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        sh = (ShareOpportunityActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_share_opp_fragment_five, container, false);
       gettingId();
       allIntent();
       allBtnClicks();

        return view;
    }


    //All button clicks
    public void allBtnClicks(){

        ivMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh.onBtnClick(R.id.share_opp_fragment_container , new ShareOppMyPostFragment() , "Share Opprtunity");

            }
        });



        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(searchIntent);

            }
        });
        ivMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(matchIntent);
            }
        });
    }


    //All Intent
    public void allIntent(){

        searchIntent = new Intent(getContext(), SearchActivity.class);
        matchIntent = new Intent(getContext(), MatchActivity.class);
    }

    //All getting
    public void gettingId(){

        bottomLayout = view.findViewById(R.id.include_bottom_layout_fragment_five_share_opp);
        ivMyPost = bottomLayout.findViewById(R.id.iv_my_post);
        ivSearch = bottomLayout.findViewById(R.id.bottom_search);
        ivMatch = bottomLayout.findViewById(R.id.iv_match);
    }

}
