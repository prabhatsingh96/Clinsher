package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;
import com.example.fluper.clinsher.appActivity.controller.validation.UserDetailValidation;
import com.example.fluper.clinsher.databinding.FragmentGettingMobileNumberBinding;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class GettingMobileNumberFragment extends Fragment implements View.OnClickListener {

    private String countryName;
    private String countryPhoneCode;
    private String countryCode;
    private View view;
    private String accessToken;
    private SignUpActivity signUpActivity;
    private String otp;
    private FragmentGettingMobileNumberBinding binding;

    public GettingMobileNumberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach (context);
        signUpActivity = (SignUpActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate (
                inflater, R.layout.fragment_getting_mobile_number, container, false);
        view = binding.getRoot ();
        AppUtil.dismiss (getContext ());
        settingDataOnLayout ();

        return view;
    }

    //setDataOn Layout
    public void settingDataOnLayout() {

        //getting log in Access token from shared preferences

        SharedPreferences sharedPreferences = getContext ().getSharedPreferences
                ("logInAccessToken", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString ("acessToken", "data not found");

        binding.etCountryCodeText.setOnClickListener (this);
        binding.btnContinoueGettingMobile.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId ()) {

            case R.id.et_country_code_text:
                getCountryCode ();
                break;
            case R.id.btn_continoue_getting_mobile:
                AppUtil.showProgressDialog (getActivity ());
                String mobileNumber = binding.etMobileNumber.getText ().toString ().trim ();
                if (mobileNumber.equals ("")) {
                    Toast.makeText (signUpActivity, "Please enter mobile number",
                            Toast.LENGTH_SHORT).show ();
                    AppUtil.dismiss (getContext ());
                }
                else if(mobileNumber.length () != 10){
                    Toast.makeText (signUpActivity, "Please enter valid mobile number",
                            Toast.LENGTH_SHORT).show ();
                    AppUtil.dismiss (getContext ());
                    }
                else {
                    updateUserMobile (accessToken, mobileNumber, countryPhoneCode);
                }
                break;

        }
    }

    public void getCountryCode() {
        final CountryPicker picker = CountryPicker.newInstance ("Select Country");
        picker.setListener (new CountryPickerListener () {
            @Override
            public void onSelectCountry(String name, String code) {
                // Log.i ("phone code", GetCountryPhoneCode (code));
                countryPhoneCode = "+" + GetCountryPhoneCode (code);
                binding.etCountryCode.setText (countryPhoneCode);
                Locale locale = new Locale ("", "" + countryCode);
                countryName = locale.getDisplayCountry ();
                binding.etCountryCodeText.setText (countryName);
                // country_btn.setText(country_code);
                picker.dismiss ();
            }
        });
        picker.show (getActivity ().getSupportFragmentManager (), "COUNTRY_PICKER");
    }

    String GetCountryPhoneCode(String CountryID) {
        String CountryZipCode = "";
        //Log.e("CountryID", "=" + CountryID);
        String[] rl = this.getResources ().getStringArray (R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split (",");
            if (g[1].trim ().equals (CountryID.trim ())) {
                countryCode = g[1];
                CountryZipCode = g[0];
                return CountryZipCode;
            }
        }
        return "";
    }

    //Retrofit (API hit)
    private void updateUserMobile(String accessToken, String mobileNumber, String mobileCode) {

        ApiInterface apiService = APiClient.getClient ().create (ApiInterface.class);

        Call<ServerResponse> call = apiService.updateUserMobile (accessToken, mobileNumber, mobileCode);

        call.enqueue (new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful ()) {
                    Toast.makeText (getApplicationContext (), "your number is Valid ",
                            Toast.LENGTH_SHORT).show ();
                    ServerResponse serverResponse = response.body ();
                    User user = serverResponse.user;
                    otp = user.getOtp ();
                    Log.d ("test", "otp = " + otp);
                    if (otp != null) {
                        signUpActivity.onBtnClick (R.id.fragment_container_signup,
                                new MObileOtpFragment (), otp);
                    } else
                        Toast.makeText (signUpActivity, "otp not found",
                                Toast.LENGTH_SHORT).show ();

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




