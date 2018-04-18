package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
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
import android.widget.Switch;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;
import com.example.fluper.clinsher.databinding.FragmentMobileOtpBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MObileOtpFragment extends Fragment implements View.OnClickListener {

    public View view;
    private String accessToken;
    private String otp;
    private String otpp;
    private FragmentMobileOtpBinding binding;

    public MObileOtpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view =  inflater.inflate (R.layout.fragment_mobile_otp, container, false);
        binding = DataBindingUtil.inflate (
                inflater, R.layout.fragment_mobile_otp, container, false);
        view = binding.getRoot ();

        AppUtil.dismiss (getApplicationContext ());
        gettingId ();
        setOpt ();
        // btnClicksEvents ();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btn_verify:
                //Sign up Access Token
                SharedPreferences sharedPreferences = getContext ().getSharedPreferences
                        ("logInAccessToken", Context.MODE_PRIVATE);
                accessToken = sharedPreferences.getString ("acessToken",
                        "data not found");

                AppUtil.showProgressDialog (getActivity ());
                verifyOtp (accessToken);
                break;
            case R.id.btn_resend_otp:
                break;
            case R.id.back_otp_mobile_fragment:
                getActivity ().onBackPressed ();
                break;

        }
    }

    public void gettingId() {

        binding.btnVerify.setOnClickListener (this);
        binding.btnResendOtp.setOnClickListener (this);
        binding.backOtpMobileFragment.setOnClickListener (this);
        binding.etOne.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               /* if (etOne.getText().toString().length() == 1)     //size as per your requirement
                {
                    etTwo.requestFocus();
                }*/

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etOne.getText ().toString ().length () == 1)//size as per your requirement
                {
                    binding.etTwo.requestFocus ();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etTwo.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etTwo.getText ().toString ().length () == 1)//size as per your requirement
                {
                    binding.etThree.requestFocus ();
                } else
                    binding.etOne.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etThree.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etThree.getText ().toString ().length () == 1)//size as per your requirement
                {
                    binding.etFour.requestFocus ();
                } else
                    binding.etTwo.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etFour.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etFour.getText ().toString ().length () == 1)//size as per your requirement
                {
                    binding.etFive.requestFocus ();
                } else
                    binding.etThree.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etFive.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etFive.getText ().toString ().length () < 1)
                    binding.etFour.requestFocus ();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void setOpt() {

        otp = getArguments ().getString ("otp");

        for (int i = 0; i < otp.length (); i++) {
            binding.etOne.setText ("" + otp.charAt (0));
            binding.etTwo.setText ("" + otp.charAt (1));
            binding.etThree.setText ("" + otp.charAt (2));
            binding.etFour.setText ("" + otp.charAt (3));
            binding.etFive.setText ("" + otp.charAt (4));
        }

    }

   /* public void btnClicksEvents(){


        verifyOtp.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                AppUtil.showProgressDialog (getActivity ());
                verifyOtp(accessToken);

            }
        });

        resendOtp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

            }
        });
    }*/

    // API for verify otp
    //Retrofit (API hit)
    private void verifyOtp(String accessToken) {

        final String manualOtp = "00000";
        ApiInterface apiService = APiClient.getClient ().create (ApiInterface.class);

        Call<ServerResponse> call = apiService.verifyOtp (accessToken, manualOtp);

        call.enqueue (new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful ()) {
                    Toast.makeText (getApplicationContext (), "your number is Valid ",
                            Toast.LENGTH_SHORT).show ();
                    ServerResponse serverResponse = response.body ();
                    User user = serverResponse.user;
                    otpp = user.getOtp ();
                    Log.d ("test", "otp = " + otpp);
                    //  if(otpp != null && manualOtp.equals (otpp)) {
                    Toast.makeText (getActivity (), "Valid", Toast.LENGTH_SHORT).show ();
                    startActivity (new Intent (getContext (), KnowMoreCurrentJobActivity.class));
                      /*}else
                      {

                      }
*/

                } else {
                    try {
                        String errorMessage = response.errorBody ().string ();
                        Log.d ("test", "Error : " + errorMessage);
                        AppUtil.dismiss (getActivity ());

                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d ("test", "error " + t.getMessage ());
                AppUtil.dismiss (getActivity ());

                t.printStackTrace ();
            }
        });

    }
}
