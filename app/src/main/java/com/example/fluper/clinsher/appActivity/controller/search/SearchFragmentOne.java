package com.example.fluper.clinsher.appActivity.controller.search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragmentOne extends Fragment {
    private SearchActivity searchActivity;
    private View view;
    private TextView tvSearch;


    public SearchFragmentOne() {
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
       view =  inflater.inflate(R.layout.fragment_search_fragment_one, container, false);
       gettingId();
      btnClickEvents();

    return view;
    }

    //All button Click Events
    public void btnClickEvents(){

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActivity.onBtnClick(R.id.search_fragment_container, new SearchFragmentTwo(),"Search");
            }
        });


    }



    //getting Id
    public void gettingId(){
        tvSearch = view.findViewById(R.id.btn_search_frag_one);
    }

}
