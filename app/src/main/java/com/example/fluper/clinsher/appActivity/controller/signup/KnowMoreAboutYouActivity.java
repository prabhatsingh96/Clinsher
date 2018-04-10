package com.example.fluper.clinsher.appActivity.controller.signup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.authentication.LogInActivity;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.profilemodule.ProfileActivity;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.utils.AppUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KnowMoreAboutYouActivity extends AppCompatActivity {

    private EditText applyPosition;
    private EditText collageName;
    private TextView yearTo;
    private TextView yearFrom;
    private TextView country;
    private TextView city;
    private TextView higherDegree;
    private TextView studyField;
    private Button takeMe;
    private Intent takeMeIntent;
    private String countryName[];
    private ArrayList<String>  cityList;
    private String code;
    private boolean flag = false;
    private boolean yearStatus;
    private RelativeLayout toolLayout;
    private TextView toolText;
    private SharedPreferences sharedPreferences;
    private String token;


    @Override
    protected void onPause() {
        super.onPause ();
        AppUtil.dismiss (KnowMoreAboutYouActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more_about_you);
        //All getting all id of layouts
        initView ();
        initControles ();
    }


    //getting All id for getting layout id
    public void initView(){

        applyPosition = findViewById (R.id.et_position_more_about_you);
        collageName = findViewById (R.id.et_school_more_about_you);
        yearTo = findViewById (R.id.et_to_year_more_about_you);
        yearFrom = findViewById (R.id.et_from_year_more_about_you);
        country = findViewById (R.id.et_country_more_about_you);
        city = findViewById (R.id.et_city_more_about_you);
        toolLayout = findViewById (R.id.include_tool_more_about_you);
        toolText = toolLayout.findViewById (R.id.tv_tool_textt);
        toolText.setText ("We need to  know more about you !");
        higherDegree = findViewById (R.id.et_higher_degree);
        studyField = findViewById (R.id.et_study_field);
        takeMe = findViewById (R.id.btn_take_me_more_about_you);
    }


    // getting data from layout and also check validation
    public void gettingDataFromLayouts(){

        String fPositin = applyPosition.getText ().toString ().trim ();
        String fcollageName = collageName.getText ().toString ().trim ();
        String fYearTo = yearTo.getText ().toString ().trim ();
        String fYearForm = yearFrom.getText ().toString ().trim ();
        String fCountry = country.getText ().toString ().trim ();
        String fCity = city.getText ().toString ().trim ();
        String fHigherDegree = higherDegree.getText ().toString ().trim ();
        String fStudyField = studyField.getText ().toString ().trim ();
        sharedPreferences = getSharedPreferences("logInAccessToken",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString ("acessToken","no data found");


        if(isValidDetails (fPositin,fcollageName,fCity,fYearTo,fYearForm,fCountry,
                fHigherDegree,fStudyField)){
            sendFresherUserDetail (token,fPositin,fcollageName,fYearForm,fYearTo,code,
                    fCountry,fCity,fHigherDegree,fStudyField);

            applyPosition.setText ("");
            collageName.setText ("");
            yearTo.setText ("");
            yearFrom.setText ("");
            country.setText ("");
            city.setText ("");
            higherDegree.setText ("");
            studyField.setText ("");
        }
     }



    //All click events
    public void initControles(){

        final ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1990; i <= 2050; i++) {
            years.add(Integer.toString(i));
        }


        higherDegree.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickerDegree (getResources ().getStringArray (R.array.degree),
                        "Select Your Degree",higherDegree);
            }
        });

        studyField.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickerDegree (getResources ().getStringArray (R.array.study_field),
                        "Select Your Study Field",studyField);
            }
        });

        country.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                countryPicker ();
            }
        });

        city.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(flag)
                  getCityFromAPI (code);
                else
                    Toast.makeText (KnowMoreAboutYouActivity.this, "First choose country",
                            Toast.LENGTH_SHORT).show ();
            }
        });

        yearFrom.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                yearStatus = true;
                pickerDialog (years,"Year");
            }
        });

        yearTo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(yearFrom.getText ().toString ().equals ("")) {
                    Toast.makeText (KnowMoreAboutYouActivity.this, "First Choose From " +
                            "Year", Toast.LENGTH_SHORT).show ();
                }else{
                    yearStatus = false;
                    pickerDialog (years, "Year");
                }

            }
        });

        takeMeIntent = new Intent (this, ProfileActivity.class);
        takeMe.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                gettingDataFromLayouts ();
                AppUtil.showProgressDialog (KnowMoreAboutYouActivity.this);
            }
        });

    }


     // country picker
     public void countryPicker(){


         AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                 KnowMoreAboutYouActivity.this);
         builderSingle.setTitle("Select Country");

        countryName = getResources ().getStringArray (R.array.countryName);
       final  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (
               this,android.R.layout.simple_list_item_1, countryName);

         builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });

         builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

                 String cntryName  = countryName[which];
                 country.setText (cntryName);
                  code =  getCountryCode (cntryName);
                  flag = true;

             }
         });
         builderSingle.show();

     }


    public String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String code : isoCountryCodes) {
            Locale locale = new Locale ("", code);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return code;
            }
        }
        return "";
    }





    //Retrofit (API hit)
    private ArrayList<String> getCityFromAPI(String countryId) {

        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.getCity (countryId);

        call.enqueue(new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(KnowMoreAboutYouActivity.this, "Succeess",
                            Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body();
                    //User user = serverResponse.user;
                    cityList = serverResponse.cityList;
                    cityPicker (cityList);

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

        return cityList;
    }


   // City Picker
    public void cityPicker(final ArrayList<String> cityList){


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                KnowMoreAboutYouActivity.this);
        builderSingle.setTitle("Select Your City");

        /* final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                 KnowMoreAboutYouActivity.this, android.R.layout.select_dialog_singlechoice);
        */
        final  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (
                this,android.R.layout.simple_list_item_1, cityList);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {

                String cityName  = cityList.get (pos);
                city.setText (cityName);

            }
        });
        builderSingle.show();

    }



    // custom picker

    public void pickerDialog(final ArrayList<String> list, String msg){


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                KnowMoreAboutYouActivity.this);
        builderSingle.setTitle(msg);

        final  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (
                this,android.R.layout.simple_list_item_1, list);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {

                String cityName  = list.get (pos);
                if(yearStatus)
                  yearFrom.setText (cityName);
                else {
                    String condition = yearFrom.getText ().toString ().trim ();
                    int i = cityName.compareTo (condition);
                    if(i  >= 0)
                        yearTo.setText (cityName);
                    else
                        Toast.makeText (KnowMoreAboutYouActivity.this,
                                "Please choose valid year",
                                Toast.LENGTH_SHORT).show ();

                }

            }
        });
        builderSingle.show();

    }


    // custom picker

    public void pickerDegree(final String[] list, String msg, final TextView tv){


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(KnowMoreAboutYouActivity.this);
        builderSingle.setTitle(msg);

        final  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> (
                this,android.R.layout.simple_list_item_1, list);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {

                String cityName  = list[pos];
                tv.setText (cityName);
            }
        });
        builderSingle.show();

    }


//Api for store detail of New(Fresher) user detial


    //Retrofit (API hit)
    private boolean sendFresherUserDetail(String accessToken,String position,String schoolName,
                                         String fromYear,String toYear,String countryId,String
                                                  countryNamee, String cityName,String degree,
                                          String studyField) {

        flag = false;
        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.sendUserFresherDetails (accessToken,position,
                schoolName,fromYear,toYear,countryId,countryNamee,cityName,degree,studyField);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    flag = true;
                    Toast.makeText(KnowMoreAboutYouActivity.this, "you are signUp " +
                                    "Suceessfully",
                            Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body();
                    User user = serverResponse.user;
                    startActivity (takeMeIntent);
                    AppUtil.dismiss (KnowMoreAboutYouActivity.this);


                }else{
                    try {
                        String errorMessage = response.errorBody().string();
                        Log.d("test", "Error : " + errorMessage);
                        AppUtil.dismiss (KnowMoreAboutYouActivity.this);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("test","error "+t.getMessage());
                AppUtil.dismiss (KnowMoreAboutYouActivity.this);

                t.printStackTrace();
            }
        });
        return flag;
    }




    //user detail validation
    public  boolean isValidDetails(String uTitle, String uCompanyName , String sector,
                                   String countryName,String cityName,String contractType,
                                   String stratDate,String endDate) {
        boolean flag;


        if(uTitle.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter Position",
                    Toast.LENGTH_SHORT).show();
        }
        else if(uCompanyName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter School/Collage Name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(sector.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter City",
                    Toast.LENGTH_SHORT).show();
        }

        else if(countryName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter ToYear",
                    Toast.LENGTH_SHORT).show();
        }

        else if(cityName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter fromYear",
                    Toast.LENGTH_SHORT).show();
        }

        else if(contractType.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter Country Name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(stratDate.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter Higher Degree",
                    Toast.LENGTH_SHORT).show();
        }


        else if(endDate.equals("")){
            flag = false;
            Toast.makeText(KnowMoreAboutYouActivity.this, "Please enter Study Field",
                    Toast.LENGTH_SHORT).show();
        }


        else
            return true;

        return flag;

    }

}
