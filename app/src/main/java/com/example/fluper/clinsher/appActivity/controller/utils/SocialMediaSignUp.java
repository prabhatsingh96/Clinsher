/*
package com.example.fluper.clinsher.appActivity.controller.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.fluper.clinsher.appActivity.controller.authentication.LogInActivity;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

*
 * Created by fluper on 10/4/18.



public class SocialMediaSignUp {
    private static final String url = "https://api.linkedin.com/v1/people/~:(id,first-name," +
            "last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";
    private Context context;

    // log in linked in
    public void logInToLinkedIn(Context context) {
        this.context = context;


        final Activity thisActivity = (Activity) context;
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

        return Scope.build (Scope.R_BASICPROFILE, Scope.W_SHARE ,Scope.R_EMAILADDRESS );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance (getApplicationContext ()).onActivityResult ((Activity) getApplicationContext (),
                requestCode, resultCode, data);
    }



    public void linkededinApiHelper() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(getApplicationContext (), url, new ApiListener () {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    // GRAB PERSON DATA, WITH EMAIL ADDRESS!!
                    setprofile(result.getResponseDataAsJson());

                } catch (Exception e) {
                    e.printStackTrace();
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

            String  social_id = response.get("id").toString();
            String socialFName = response.get("firstName").toString();
            String socialLName = response.get("lastName").toString();
            String socialEmail = response.get("emailAddress").toString();
            // String socialName = response.get("formattedName").toString();
            Toast.makeText (getApplicationContext (), "Profile DAta :"+social_id+""+
                            socialFName+""+socialEmail+""+socialLName,
                    Toast.LENGTH_LONG).show ();
            try {
                JSONObject photo= response.getJSONObject("pictureUrls");
                JSONArray values=photo.getJSONArray("values");
                String socialPhotoUrl = values.getString(0);


                Toast.makeText (getApplicationContext (), "Profile picture url :"+socialPhotoUrl,
                        Toast.LENGTH_LONG).show ();
            }
            catch (Exception e)
            {
                Toast.makeText (getApplicationContext (), ""+e.toString (), Toast.LENGTH_SHORT).show ();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/
