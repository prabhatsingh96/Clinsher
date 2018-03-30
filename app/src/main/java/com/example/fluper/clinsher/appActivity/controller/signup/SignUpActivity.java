package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements AppUtil.setOnListener {

    private Button    signUpBtn;
    private Intent    signUpIntent;
    private EditText  etFirstName;
    private EditText  etLastName;
    private EditText  etEmail;
    private EditText  etPassword;
    private EditText  etConfirmPassword;
    private String    firstName;
    private String    lastName;
    private String    email;
    private String    password;
    private String    confirmPassword;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //find id of all layout
        gettingId();

        //All intent
       // signUpIntent = new Intent(this, KnowMoreCurrentJobActivity.class);
        //click on buttons
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettingDataFromLayout();

                if(isValidEmail(email)  ) {

                    if (isValidDetails(firstName, lastName, password, confirmPassword)) {
                        if (password.equals(confirmPassword)) {

                            registerUsertoServer(firstName, lastName, email, password);
                            addFirstFragment ();
                           // startActivity(signUpIntent);
                        } else
                            Toast.makeText(SignUpActivity.this, "password is not " +
                                    "matched", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }



    //Email Validation

    public boolean isValidEmail(String email){

        if (email.equals(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    //user detail validation
    public  boolean isValidDetails(String fName, String lName , String password,
                                   String confirmPassword) {
        boolean flag;

        if(fName.equals("")){
            flag = false;
            Toast.makeText(SignUpActivity.this, "Please enter first name",
                    Toast.LENGTH_SHORT).show();
        }
       else if(lName.equals("")){
            flag = false;
            Toast.makeText(SignUpActivity.this, "Please enter last name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(password .equals("")){
            flag = false;
            Toast.makeText(SignUpActivity.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        }

        else if(confirmPassword.equals("")){
            flag = false;
            Toast.makeText(SignUpActivity.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        }
        else
            return true;

        return flag;

    }



    public void gettingId() {

        signUpBtn = findViewById(R.id.btn_sign_up);
        etFirstName = findViewById(R.id.et_firstt_name);
        etLastName = findViewById(R.id.et_lastt_name);
        etEmail  = findViewById(R.id.et_signupp_email);
        etPassword = findViewById(R.id.et_signupp_password);
        etConfirmPassword = findViewById(R.id.et_signup_confirmm_password);
        Log.d("test","getting id");
    }

    //taking data from layout
    public void gettingDataFromLayout(){

        firstName = etFirstName.getText().toString();
        lastName  = etLastName.getText().toString();
        email     = etEmail.getText().toString();
        password  = etPassword.getText().toString();
        Log.d("test","getting layout");
        confirmPassword = etConfirmPassword.getText().toString().trim();
    }



    public void addFirstFragment(){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.fragment_container_signup, new GettingMobileNumberFragment ());
        ft.commit();
    }


    //Retrofit (API hit)
    private void registerUsertoServer(String firstName, String lastName,String email,
                                      String password) {

        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.registerUser(firstName,lastName,email, password);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Succeess full signup.",
                            Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body();
                    User user = serverResponse.user;

                    String logInAccessToken = user.getAccessToken ();
                    sharedPreferences = getSharedPreferences("logInAccessToken",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("acessToken", logInAccessToken);
                    editor.commit();



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

    @Override
    public void onBtnClick(int fragmentid, Fragment fragment, String str) {


        Bundle b = new Bundle();
        b.putString ("otp",str);
        fragment.setArguments (b);
        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.replace (fragmentid, fragment);
        ft.commit();
    }
}
