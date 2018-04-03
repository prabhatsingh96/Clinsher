package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fluper.clinsher.R;

import java.lang.reflect.Array;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectorFragment extends Fragment {

    private View view;
    private ListView sectorList;
    private SignUpActivity mSignUpActivity;

    public SectorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach (context);
       // mSignUpActivity = (SignUpActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate (R.layout.fragment_sector, container, false);
        sectorList = view.findViewById (R.id.lv_sector_list);
        String[] sectors  = getContext ().getResources().getStringArray(R.array.sector_list);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (getContext (),
                android.R.layout.simple_list_item_1,sectors);
        sectorList.setAdapter (arrayAdapter);

        return  view;
    }

}
