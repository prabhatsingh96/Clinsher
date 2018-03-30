package com.example.fluper.clinsher.appActivity.controller.signup;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.fluper.clinsher.R;

/**
 * Created by fluper on 27/3/18.
 */


    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private EditText etOne;
        private EditText etTwo;
        private EditText etThree;
        private EditText etFour;
        private EditText etFive;

        public GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            etOne = view.findViewById (R.id.et_one);
            etTwo = view.findViewById (R.id.et_two);
            etThree = view.findViewById (R.id.et_three);
            etFour = view.findViewById (R.id.et_four);
            etFive = view.findViewById (R.id.et_five);

            switch(view.getId())
            {

                case R.id.et_one:
                    if(text.length()==1)
                        etTwo.requestFocus();
                    break;
                case R.id.et_two:
                    if(text.length()==1)
                        etThree.requestFocus();
                    break;
                case R.id.et_three:
                    if(text.length()==1)
                        etFour.requestFocus();
                    break;
                case R.id.et_four:
                    if(text.length()==1)
                        etFive.requestFocus();

                    break;
                case R.id.et_five:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }



