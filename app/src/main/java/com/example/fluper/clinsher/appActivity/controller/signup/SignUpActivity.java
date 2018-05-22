package com.example.fluper.clinsher.appActivity.controller.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.authentication.LogInActivity;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.profilemodule.TermsOfUseActivity;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;
import com.example.fluper.clinsher.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PartMap;

public class SignUpActivity extends AppCompatActivity implements AppUtil.setOnListener,
        View.OnClickListener {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private MultipartBody.Part profileImage;
    private SharedPreferences sharedPreferences;
    private int GALLERY_CONSTANT = 67;
    private String path;
    private Map<String, RequestBody> userDetailMap;
    private Uri ui;
    private File file;
    private static final String url = "https://api.linkedin.com/v1/people/~:(id,first-name," +
            "last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";
    private Intent termsOfUseIntent;
    private Intent logInBottomIntent;
    private GoogleSignInClient mGoogleApiClient;
    private ActivitySignUpBinding binding;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = DataBindingUtil.setContentView (this, R.layout.activity_sign_up);
        //setContentView (R.layout.activity_sign_up);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ()
                    .permitAll ().build ();
            StrictMode.setThreadPolicy (policy);
        }
        gettingAllIntent ();

        binding.profilePlaceHolder.setOnClickListener (this);
        binding.loginAtHaveAnAcc.setOnClickListener (this);
        binding.signInWithLinkdin.setOnClickListener (this);
        binding.btnSignUp.setOnClickListener (this);
        binding.btnTerms.setOnClickListener (this);
        binding.signupBack.setOnClickListener (this);
        binding.signInWithGoogle.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.profile_place_holder:
                setProfileImage ();
                break;
            case R.id.btn_sign_up:
                gettingDataFromLayout ();
                if (isValidEmail (email.toString ())) {

                    if (isValidDetails (ui.toString (), firstName, lastName, password,
                            confirmPassword)) {
                        if (binding.etSignuppPassword.getText ().toString ().equals
                                (binding.etSignupConfirmmPassword.getText ().toString ())) {

                            registerUsertoServer (profileImage, userDetailMap);
                            AppUtil.showProgressDialog (SignUpActivity.this);
                        } else
                            Toast.makeText (SignUpActivity.this, "password is not " +
                                    "matched", Toast.LENGTH_SHORT).show ();
                    } else
                        Toast.makeText (SignUpActivity.this, "not validate",
                                Toast.LENGTH_SHORT).show ();
                } else
                    Toast.makeText (SignUpActivity.this, "email invalid",
                            Toast.LENGTH_SHORT).show ();
                break;
            case R.id.sign_in_with_linkdin:
                logInToLinkedIn ();
                break;
            case R.id.sign_in_with_google:
                signInToGoogle ();
                break;
            case R.id.btn_terms:
                startActivity (termsOfUseIntent);
                break;

            case R.id.login_at_have_an_acc:
                startActivity (logInBottomIntent);
                break;

            case R.id.signup_back:
                SignUpActivity.super.onBackPressed ();
                break;
        }
    }

    //getting all Intent
    public void gettingAllIntent() {
        termsOfUseIntent = new Intent (this, TermsOfUseActivity.class);
        logInBottomIntent = new Intent (this, LogInActivity.class);
    }

    //get Image from galary
    public void setProfileImage() {
        Intent intentGallery = new Intent ();
        intentGallery.setType ("image/*");
        intentGallery.setAction (Intent.ACTION_GET_CONTENT);
        startActivityForResult (intentGallery.createChooser (intentGallery,
                "Select picture"),
                GALLERY_CONSTANT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        try {
            if (requestCode == GALLERY_CONSTANT) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {

                        ui = data.getData ();
                        path = getRealPathFromURIPath (ui, this);
                        Picasso.get ().load (data.getData ()).into (binding.profilePlaceHolder);

                    } else
                        Toast.makeText (this, "data is not found",
                                Toast.LENGTH_SHORT).show ();
                }

            }
        } catch (Exception e) {

            Toast.makeText (this, "error" + e, Toast.LENGTH_SHORT).show ();
            Log.d ("test", "error: " + e);

        }

        LISessionManager.getInstance (getApplicationContext ()).onActivityResult (this,
                requestCode, resultCode, data);

        if (requestCode == 10) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent (data);
            handleSignInResult (task);
        }

        if (requestCode == 100) {

            signOut ();
        }
    }

    //Email Validation

    public boolean isValidEmail(String email) {

        if (email.equals ("")) {
            Toast.makeText (SignUpActivity.this, "Please enter email",
                    Toast.LENGTH_SHORT).show ();
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher (email).matches ();
        }

    }

    //user detail validation
    public boolean isValidDetails(String image, String fName,
                                  String lName, String password,
                                  String confirmPassword) {
        boolean flag;

        if (fName.equals ("")) {
            flag = false;
            Toast.makeText (SignUpActivity.this, "Please enter first name",
                    Toast.LENGTH_SHORT).show ();
        } else if (image.equals ("")) {
            flag = false;
            Toast.makeText (this, "Please Choose Image", Toast.LENGTH_SHORT).show ();
        } else if (lName.equals ("")) {
            flag = false;
            Toast.makeText (SignUpActivity.this, "Please enter last name",
                    Toast.LENGTH_SHORT).show ();
        } else if (password.equals ("")) {
            flag = false;
            Toast.makeText (SignUpActivity.this, "Please enter password",
                    Toast.LENGTH_SHORT).show ();
        } else if (confirmPassword.equals ("")) {
            flag = false;
            Toast.makeText (SignUpActivity.this, "Please enter password",
                    Toast.LENGTH_SHORT).show ();
        } else
            return true;

        return flag;

    }

    //taking data from layout
    public void gettingDataFromLayout() {

        firstName = binding.etFirsttName.getText ().toString ();
        lastName = binding.etLasttName.getText ().toString ();
        email = binding.etSignuppEmail.getText ().toString ();
        password = binding.etSignuppPassword.getText ().toString ();
        Log.d ("test", "getting layout");
        confirmPassword = binding.etSignupConfirmmPassword.getText ().toString ().trim ();

//pass it like this
        try {
            file = new File (path);

            RequestBody requestFile =
                    RequestBody.create (MediaType.parse ("image/*"), file);

// MultipartBody.Part is used to send also the actual file name
            profileImage =
                    MultipartBody.Part.createFormData ("profileImage", file.getName (),
                            requestFile);
            userDetailMap = new HashMap<> ();
            userDetailMap.put ("firstName", createPartFromString (firstName));
            userDetailMap.put ("lastName", createPartFromString (lastName));
            userDetailMap.put ("email", createPartFromString (email));
            userDetailMap.put ("password", createPartFromString (password));
        } catch (Exception e) {
            Toast.makeText (this, "Please choose your Profile Image",
                    Toast.LENGTH_SHORT).show ();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create (
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = null;
        cursor = activity.getContentResolver ().query (contentURI, null,
                null, null, null);
        if (cursor == null) {
            return contentURI.getPath ();
        } else {
            cursor.moveToFirst ();
            int idx = cursor.getColumnIndex (MediaStore.Images.ImageColumns.DATA);
            return cursor.getString (idx);
        }
    }

    public void addFirstFragment() {

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager ();
        FragmentTransaction ft = frg.beginTransaction ();
        ft.add (R.id.fragment_container_signup, new GettingMobileNumberFragment ());
        ft.commit ();
    }

    //Retrofit (API hit)
    private void registerUsertoServer(MultipartBody.Part profileImage, Map<String,
            RequestBody> userDetail) {
        ApiInterface apiService = APiClient.getClient ().create (ApiInterface.class);

        Call<ServerResponse> call = apiService.registerUser (profileImage, userDetail);
        call.enqueue (new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful ()) {
                    flag = true;
                    Toast.makeText (SignUpActivity.this, "Message : " +
                                    response.message (),
                            Toast.LENGTH_LONG).show ();
                    ServerResponse serverResponse = response.body ();
                    User user = serverResponse.user;

                    String logInAccessToken = user.getAccessToken ();
                    sharedPreferences = getSharedPreferences ("logInAccessToken",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit ();
                    editor.putString ("acessToken", logInAccessToken);
                    editor.commit ();

                    addFirstFragment ();

                } else {
                    try {
                        String errorMessage = response.errorBody ().string ();
                        Log.d ("test", "Error : " + errorMessage);
                        Toast.makeText (SignUpActivity.this, "error" + errorMessage,
                                Toast.LENGTH_SHORT).show ();
                        AppUtil.dismiss (SignUpActivity.this);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d ("test", "error " + t.getMessage ());
                Toast.makeText (SignUpActivity.this, "failureMessage" + t.getMessage (),
                        Toast.LENGTH_LONG).show ();
                AppUtil.dismiss (SignUpActivity.this);
                t.printStackTrace ();
            }
        });

    }

    @Override
    public void onBtnClick(int fragmentid, Fragment fragment, String str) {
        String backStateName = fragment.getClass ().getName ();

        Bundle b = new Bundle ();
        b.putString ("otp", str);
        fragment.setArguments (b);
        android.support.v4.app.FragmentManager frg = getSupportFragmentManager ();
        boolean fragmentPopped = frg.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped) {
            FragmentTransaction ft = frg.beginTransaction ();
            ft.replace (fragmentid, fragment);
            ft.addToBackStack (backStateName);
            ft.commit ();
        }
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
                        Log.d ("test", "error : " + error);
                        Toast.makeText (thisActivity, "Find Some error" + error,
                                Toast.LENGTH_SHORT).show ();

                    }
                }, true);
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {

        return Scope.build (Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance (getApplicationContext ()).onActivityResult (this,
                requestCode, resultCode, data);
    }*/

    public void linkededinApiHelper() {
        APIHelper apiHelper = APIHelper.getInstance (getApplicationContext ());
        apiHelper.getRequest (SignUpActivity.this, url, new ApiListener () {
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

    //sign in google account
    private void signInToGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail ()
                .build ();

        mGoogleApiClient = GoogleSignIn.getClient (this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount (this);

        //updateUI(account);
        Intent signInIntent = mGoogleApiClient.getSignInIntent ();
        startActivityForResult (signInIntent, 10);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult (ApiException.class);
            String name = account.getDisplayName ();
            String email = account.getEmail ();
            String url = account.getPhotoUrl ().toString ().trim ();
           /* //Log.d ("test","photo url : "+url);
            Intent gmailIntent = new Intent(this,GmailDetailActivity.class);
            gmailIntent.putExtra ("name",name);
            gmailIntent.putExtra ("email",email);
            gmailIntent.putExtra ("url",url);
            startActivityForResult (gmailIntent,100);

*/
            Toast.makeText (this, "LogIn SuccessFully", Toast.LENGTH_SHORT).show ();
            Toast.makeText (this, "user Detail:  Name = " + name + ",Email = " + email,
                    Toast.LENGTH_SHORT).show ();
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w ("test", "signInResult:failed code=" + e.getStatusCode ());
            // updateUI(null);
        }
    }

    private void signOut() {
        mGoogleApiClient.signOut ()
                .addOnCompleteListener (this, new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText (SignUpActivity.this, "you are logOut",
                                Toast.LENGTH_SHORT).show ();
                    }
                });
    }
}

