package com.example.fluper.clinsher.appActivity.controller.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Toast;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;
import com.linkedin.platform.LISessionManager;
import com.squareup.picasso.Picasso;

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

public class SignUpActivity extends AppCompatActivity implements AppUtil.setOnListener {

    private Button    signUpBtn;
    private EditText  etFirstName;
    private EditText  etLastName;
    private EditText  etEmail;
    private EditText  etPassword;
    private EditText  etConfirmPassword;
    private  String    firstName;
    private  String    lastName;
    private  String    email;
    private  String    password;
    private  String    confirmPassword;
    private  MultipartBody.Part    profileImage;
    private SharedPreferences sharedPreferences;
    private CircleImageView profileImageView;
    private int GALLERY_CONSTANT = 67;
    private String path ;
    private boolean flag = false;
    private  Map<String,RequestBody> userDetailMap;
    private  Uri ui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //find id of all layout
        gettingId();

        //All intent
       // signUpIntent = new Intent(this, KnowMoreCurrentJobActivity.class);
        //profile Image
        profileImageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                setProfileImage();
            }
        });

        //click on buttons
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettingDataFromLayout();
                if(isValidEmail(email.toString ())  ) {

                    if (isValidDetails(ui.toString (),firstName, lastName, password, confirmPassword)) {
                        if (etPassword.getText ().toString ().equals
                                (etConfirmPassword.getText ().toString ())) {

                            registerUsertoServer(profileImage,userDetailMap);
                            AppUtil.showProgressDialog (SignUpActivity.this);
                        } else
                            Toast.makeText(SignUpActivity.this, "password is not " +
                                    "matched", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText (SignUpActivity.this, "not validate", Toast.LENGTH_SHORT).show ();
                }else
                    Toast.makeText (SignUpActivity.this, "email invalid", Toast.LENGTH_SHORT).show ();

            }
        });

    }

     //get Image from galary
     public void setProfileImage(){
         Intent intentGallery = new Intent();
         intentGallery.setType("image/*");
         intentGallery.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(intentGallery.createChooser(intentGallery,
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
                        path = getRealPathFromURIPath (ui,this);
                       Picasso.get().load (data.getData ()).into (profileImageView);

                    }
                    else
                        Toast.makeText (this, "data is not found", Toast.LENGTH_SHORT).show ();
                }

            }
        } catch (Exception e) {

            Toast.makeText (this, "error"+e, Toast.LENGTH_SHORT).show ();
            Log.d ("test","error: "+e);

        }
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
    public  boolean isValidDetails(String image,String fName,
                                   String lName , String password,
                                   String confirmPassword) {
        boolean flag;

        if(fName.equals("")){
            flag = false;
            Toast.makeText(SignUpActivity.this, "Please enter first name",
                    Toast.LENGTH_SHORT).show();
        }
        else if(image.equals ("")){
            flag = false;
            Toast.makeText (this, "Please Choose Image", Toast.LENGTH_SHORT).show ();
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
        profileImageView = findViewById (R.id.profile_place_holder);
       // Log.d("test","getting id");
    }

    //taking data from layout
    public void gettingDataFromLayout(){

        firstName = etFirstName.getText().toString();
        lastName  = etLastName.getText().toString();
        email     = etEmail.getText().toString();
        password  = etPassword.getText().toString();
        Log.d("test","getting layout");
        confirmPassword = etConfirmPassword.getText().toString().trim();


//pass it like this
        File file = new File (path);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

// MultipartBody.Part is used to send also the actual file name
         profileImage =
                MultipartBody.Part.createFormData("profileImage", file.getName(), requestFile);
        userDetailMap = new HashMap<> ();
        userDetailMap.put ("firstName",createPartFromString (firstName));
        userDetailMap.put ("lastName",createPartFromString (lastName));
        userDetailMap.put ("email", createPartFromString (email));
        userDetailMap.put ("password",createPartFromString (password));


// add another part within the multipart request
/*

        firstName =
                RequestBody.create(
                        MediaType.parse("text/plain"), etFirstName.getText().toString());
        lastName =
                RequestBody.create(
                        MediaType.parse("text/plain"), etLastName.getText().toString());
        email =
                RequestBody.create(
                        MediaType.parse("text/plain"), etEmail.getText().toString());
        password =
                RequestBody.create(
                        MediaType.parse("text/plain"), etPassword.getText().toString());
*/



    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null,
                null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }



    public void addFirstFragment(){

        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.fragment_container_signup, new GettingMobileNumberFragment ());
        ft.commit();
    }


    //Retrofit (API hit)
    private void registerUsertoServer(MultipartBody.Part profileImage, Map<String, RequestBody> userDetail){
        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.registerUser(profileImage, userDetail);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    flag = true;
                    Toast.makeText(SignUpActivity.this, "Message : "+ response.message (),
                            Toast.LENGTH_LONG).show();
                    ServerResponse serverResponse = response.body();
                    User user = serverResponse.user;

                    String logInAccessToken = user.getAccessToken ();
                    sharedPreferences = getSharedPreferences("logInAccessToken",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("acessToken", logInAccessToken);
                    editor.commit();

                    addFirstFragment ();



                }else{
                    try {
                        String errorMessage = response.errorBody().string();
                        Log.d("test", "Error : " + errorMessage);
                        Toast.makeText (SignUpActivity.this, "error"+errorMessage, Toast.LENGTH_SHORT).show ();
                        AppUtil.dismiss (SignUpActivity.this);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("test","error "+t.getMessage());
                Toast.makeText (SignUpActivity.this, "failureMessage"+t.getMessage (),
                        Toast.LENGTH_LONG).show ();
                AppUtil.dismiss (SignUpActivity.this);
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onBtnClick(int fragmentid, Fragment fragment, String str) {
        String backStateName = fragment.getClass().getName();

        Bundle b = new Bundle();
        b.putString ("otp",str);
        fragment.setArguments (b);
        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        boolean fragmentPopped = frg.popBackStackImmediate (backStateName, 0);
        if(!fragmentPopped) {
            FragmentTransaction ft = frg.beginTransaction ();
            ft.replace (fragmentid, fragment);
            ft.addToBackStack (backStateName);
            ft.commit ();
        }
    }
}
