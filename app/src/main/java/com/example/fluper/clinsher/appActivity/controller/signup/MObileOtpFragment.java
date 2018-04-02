package com.example.fluper.clinsher.appActivity.controller.signup;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

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
    private Button verifyOtp;
    private Button resendOtp;
    private String accessToken;
    private String otp;
    private  String otpp;

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
        btnClicksEvents ();

        return view;
    }


    public void gettingId(){


        etOne = view.findViewById (R.id.et_one);
        etTwo = view.findViewById (R.id.et_two);
        etThree = view.findViewById (R.id.et_three);
        etFour = view.findViewById (R.id.et_four);
        etFive = view.findViewById (R.id.et_five);
        verifyOtp = view.findViewById (R.id.btn_verify);
        resendOtp = view.findViewById (R.id.btn_resend_otp);



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

        otp = getArguments ().getString ("otp");

        for(int i = 0; i<otp.length (); i++)
        {
            etOne.setText (""+otp.charAt (0));
            etTwo.setText (""+otp.charAt (1));
            etThree.setText (""+otp.charAt (2));
            etFour.setText (""+otp.charAt (3));
            etFive.setText (""+otp.charAt (4));
        }


    }

    public void btnClicksEvents(){

        //Sign up Access Token
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences
                ("logInAccessToken", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("acessToken","data not found");

        verifyOtp.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                 verifyOtp(accessToken);
            }
        });

        resendOtp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

            }
        });
    }


      // API for verify otp
      //Retrofit (API hit)
      private void verifyOtp(String accessToken) {

          final String manualOtp = "00000";
          ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

          Call<ServerResponse> call = apiService.verifyOtp (accessToken,manualOtp);

          call.enqueue(new Callback<ServerResponse> () {
              @Override
              public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                  if (response.isSuccessful()) {
                      Toast.makeText(getApplicationContext (), "your number is Valid ",
                              Toast.LENGTH_SHORT).show();
                      ServerResponse serverResponse = response.body();
                      User user = serverResponse.user;
                      otpp = user.getOtp ();
                      Log.d ("test","otp = "+otpp);
                    //  if(otpp != null && manualOtp.equals (otpp)) {
                          Toast.makeText (getContext (), "Valid", Toast.LENGTH_SHORT).show ();
                          startActivity (new Intent (getContext (),KnowMoreCurrentJobActivity.class));
                      /*}else
                      {

                      }
*/

                  }else{
                      try {
                          String errorMessage = response.errorBody().string();
                          Log.d("test", "Error : " + errorMessage);
                      } catch (IOException e) {
                          //e.printStackTrace();
                      }
                  }
              }

              @Override
              public void onFailure(Call<ServerResponse> call, Throwable t) {
                  Log.d("test","error "+t.getMessage());
                  t.printStackTrace();
              }
          });

      }




}
