package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractFragment extends Fragment {

    private ListView mListView;
    private KnowMoreCurrentJobActivity mKnowMoreCurrentJobActivity;
    public ContractFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        mKnowMoreCurrentJobActivity = (KnowMoreCurrentJobActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate (R.layout.fragment_contract, container, false);
        mListView = view.findViewById (R.id.contract_fragment_list);

        final String contracatArray[] = getResources ().getStringArray (R.array.contract_type);
        ArrayAdapter adapter = new ArrayAdapter (getContext (),
                android.R.layout.simple_list_item_1,
                contracatArray);
        mListView.setAdapter (adapter);
        mListView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contract = contracatArray[position];
                mKnowMoreCurrentJobActivity.getContractType(contract);
                getView ().setVisibility (View.GONE);
            }
        });

        return view;
    }

      public interface setContractTypeListener{
         void getContractType(String contractTyp);
      }
}
