package com.example.fluper.clinsher.appActivity.controller.signup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fluper.clinsher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MObileOtpFragment extends Fragment {


    public View view;
    public EditText etOne;
    private EditText etTwo;
    private EditText etThree;
    private EditText etFour;
    private EditText etFive;

    public MObileOtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate (R.layout.fragment_mobile_otp, container, false);

        gettingId ();
        setOpt ();

    /*
        String otp = getArguments ().getString ("otp");

        for(int i = 0; i<otp.length (); i++)
        {
             etOne.setText (""+otp.charAt (0));
             etTwo.setText (""+otp.charAt (1));
             etThree.setText (""+otp.charAt (2));
             etFour.setText (""+otp.charAt (3));
             etFive.setText (""+otp.charAt (4));
        }
*/
        return view;
    }


    public void gettingId(){


        etOne = view.findViewById (R.id.et_one);
        etTwo = view.findViewById (R.id.et_two);
        etThree = view.findViewById (R.id.et_three);
        etFour = view.findViewById (R.id.et_four);
        etFive = view.findViewById (R.id.et_five);



        etOne.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               /* if (etOne.getText().toString().length() == 1)     //size as per your requirement
                {
                    etTwo.requestFocus();
                }*/

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etOne.getText().toString().length() == 1)     //size as per your requirement
                {
                    etTwo.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etTwo.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etTwo.getText().toString().length() == 1)     //size as per your requirement
                {
                    etThree.requestFocus();
                }else
                     etOne.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etThree.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etThree.getText().toString().length() == 1)     //size as per your requirement
                {
                    etFour.requestFocus();
                }else
                    etTwo.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFour.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etFour.getText().toString().length() == 1)     //size as per your requirement
                {
                    etFive.requestFocus();
                }else
                    etThree.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFive.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etFive.getText ().toString ().length () < 1)
                    etFour.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setOpt(){

        String otp = getArguments ().getString ("otp");

        for(int i = 0; i<otp.length (); i++)
        {
            etOne.setText (""+otp.charAt (0));
            etTwo.setText (""+otp.charAt (1));
            etThree.setText (""+otp.charAt (2));
            etFour.setText (""+otp.charAt (3));
            etFive.setText (""+otp.charAt (4));
        }


    }

}
