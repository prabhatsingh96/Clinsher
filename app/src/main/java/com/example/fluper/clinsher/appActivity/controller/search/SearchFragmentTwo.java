package com.example.fluper.clinsher.appActivity.controller.search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragmentTwo extends Fragment {

    private ImageView doneImage;
    private View view;
    private SearchActivity searchActivity;


    public SearchFragmentTwo() {
        // Required empty public constructor
    }


    public void onAttach(Context context) {

        super.onAttach(context);
        searchActivity = (SearchActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_search_fragment_two, container, false);
         gettingId();
         clicksEvents();

         RelativeLayout rl = getActivity().findViewById(R.id.search_bottom_relative_layout);
         rl.setVisibility(View.GONE);

        return view;
    }

    //getting id
    public void gettingId(){
        doneImage = view.findViewById(R.id.iv_search_fragment_done);
    }

    //All button/Images/textView click events
    private void clicksEvents(){
        doneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchActivity.onBtnClick(R.id.search_fragment_container, new SearchFragmentThree(),
                        "Search");
            }
        });
    }

}
