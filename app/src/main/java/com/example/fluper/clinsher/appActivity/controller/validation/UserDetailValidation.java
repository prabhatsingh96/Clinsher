package com.example.fluper.clinsher.appActivity.controller.validation;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.example.fluper.clinsher.appActivity.controller.signup.SignUpActivity;

/**
 * Created by fluper on 14/3/18.
 */

public class UserDetailValidation {

    Context context;

    public UserDetailValidation(Context context) {
        this.context = context;
    }
    //Email Validation

    public boolean isValidEmail(String email){

        if (email.equals(""))
        {
            Toast.makeText(context, "Please enter email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    //password validation
    public boolean passwordValidation(String password) {
        if (password.equals("")) {
            Toast.makeText(context, "Please enter password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }



  /*  //user detail validation
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
*/


}
