package com.example.fluper.clinsher.appActivity.controller.authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.profilemodule.ProfileActivity;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.signup.KnowMoreCurrentJobActivity;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;
import com.example.fluper.clinsher.databinding.ActivityLogInBinding;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private String email;
    private String password;
    private Intent forgotIntent;
    private Intent logInIntent;
    private Intent logInIntentTwo;
    private User user;
    private String email1;
    private String password1;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityLogInBinding binding;

    private static String APP_ID = "308180782571605"; // Replace your App ID here

    // Instance of Facebook Class
    //private Facebook facebook;
    private FacebookSdk facebook;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    private static final String url = "https://api.linkedin.com/v1/people/~:(id,first-name," +
            "last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";

    @Override
    protected void onPause() {
        super.onPause ();
        AppUtil.dismiss (LogInActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = DataBindingUtil.setContentView (this, R.layout.activity_log_in);
        // FacebookSdk.sdkInitialize(getApplicationContext());
       // setContentView (R.layout.activity_log_in);

        //
        //All intent
        allIntent ();
        //All button Clicks
        buttonClicksEvents ();

    }

    //All intent
    public void allIntent() {
        forgotIntent = new Intent (this, ForgotPasswordActivity.class);
        logInIntent = new Intent (this, ProfileActivity.class);
        logInIntentTwo = new Intent (this, KnowMoreCurrentJobActivity.class);
    }

    //getting details from layout
    public void gettingDetailsFromLayout() {

        email = binding.etLoginEmail.getText ().toString ().trim ();
        password = binding.etLogtinPassword.getText ().toString ().trim ();
    }

    //all button clicks Events
    public void buttonClicksEvents() {

        // logIn Button Clicks
        binding.btnLoginLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                gettingDetailsFromLayout ();
                if (!isValidEmail (email)) {
                    Toast.makeText (LogInActivity.this, "Invalid Email",
                            Toast.LENGTH_SHORT).show ();

                } else if (!passwordValidation (password)) {
                    Toast.makeText (LogInActivity.this, "Invalid Password",
                            Toast.LENGTH_SHORT).show ();

                } else {
                    AppUtil.showProgressDialog (LogInActivity.this);
                    logInUsertoServer (email, password);

                }
            }
        });

        // forgot button clicks event
        binding.forgotPasswordOnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity (forgotIntent);

            }
        });

        binding.logInWithLinkdin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //logInToFacebook();
                logInToLinkedIn ();
            }
        });

        binding.logInWithGoogle.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //googleSignIn();
            }
        });
    }

    //Appi hit for user login

    //Retrofit (API hit)
    private void logInUsertoServer(final String email, final String password) {

        ApiInterface apiService = APiClient.getClient ().create (ApiInterface.class);

        Call<ServerResponse> call = apiService.logInUser (email, password);

        call.enqueue (new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful ()) {
                    // Toast.makeText(LogInActivity.this, "Succeess full logIn",
                    //       Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body ();
                    user = new User ();
                    user = serverResponse.user;
                    email1 = user.getEmail ().trim ();
                    password1 = user.getPassword ().trim ();
                    String message = serverResponse.message;

                    if (email1.equals (email) && password1.equals (password)) {
                        if (user.getSignupStatus ().equals ("2")) {
                            startActivity (logInIntent);
                            Toast.makeText (LogInActivity.this, " " + message,
                                    Toast.LENGTH_SHORT).show ();
                        } else {
                            startActivity (logInIntentTwo);
                            AppUtil.dismiss (LogInActivity.this);
                        }
                    } else {
                        Toast.makeText (LogInActivity.this, "Invalid Cardinals", Toast.LENGTH_SHORT).show ();
                    }

                } else {
                    try {
                        String errorMessage = response.errorBody ().string ();
                        Log.d ("test", "Error : " + errorMessage);
                        AppUtil.dismiss (LogInActivity.this);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d ("test", "error " + t.getMessage ());
                AppUtil.dismiss (LogInActivity.this);
                t.printStackTrace ();
            }
        });

    }

    public boolean isValidEmail(String email) {

        if (email.equals ("")) {
            Toast.makeText (LogInActivity.this, "Please enter email",
                    Toast.LENGTH_SHORT).show ();
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher (email).matches ();
        }

    }

    //password validation
    public boolean passwordValidation(String password) {
        return !password.equals ("");
    }

    // log in linked in
    public void logInToLinkedIn() {

        final Activity thisActivity = this;
        LISessionManager.getInstance (getApplicationContext ()).init (thisActivity, buildScope (),
                new AuthListener () {
                    @Override
                    public void onAuthSuccess() {

                        // Authentication was successful.  You can now do
                        // other calls with the SDK.

                        Toast.makeText (thisActivity, "LogIn Success1",
                                Toast.LENGTH_SHORT).show ();
                        linkededinApiHelper ();
                        Toast.makeText (thisActivity, "LogIn Success11",
                                Toast.LENGTH_SHORT).show ();

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        // Handle authentication errors
                        Toast.makeText (thisActivity, "Find Some error",
                                Toast.LENGTH_SHORT).show ();

                    }
                }, true);
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {

        return Scope.build (Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance (getApplicationContext ()).onActivityResult (this,
                requestCode, resultCode, data);
    }

    public void linkededinApiHelper() {
        APIHelper apiHelper = APIHelper.getInstance (getApplicationContext ());
        apiHelper.getRequest (LogInActivity.this, url, new ApiListener () {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    // GRAB PERSON DATA, WITH EMAIL ADDRESS!!
                    setprofile (result.getResponseDataAsJson ());

                } catch (Exception e) {
                    e.printStackTrace ();
                }

            }

            @Override
            public void onApiError(LIApiError error) {
                // ((TextView) findViewById(R.id.error)).setText(error.toString());
            }
        });
    }

    public void setprofile(JSONObject response) {

        try {

            String social_id = response.get ("id").toString ();
            String socialFName = response.get ("firstName").toString ();
            String socialLName = response.get ("lastName").toString ();
            String socialEmail = response.get ("emailAddress").toString ();
            // String socialName = response.get("formattedName").toString();
            Toast.makeText (this, "Profile DAta :" + social_id + "" +
                            socialFName + "" + socialEmail + "" + socialLName,
                    Toast.LENGTH_LONG).show ();
            try {
                JSONObject photo = response.getJSONObject ("pictureUrls");
                JSONArray values = photo.getJSONArray ("values");
                String socialPhotoUrl = values.getString (0);

                Toast.makeText (this, "Profile picture url :" + socialPhotoUrl,
                        Toast.LENGTH_LONG).show ();
            } catch (Exception e) {
                Toast.makeText (this, "" + e.toString (), Toast.LENGTH_SHORT).show ();
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

}




