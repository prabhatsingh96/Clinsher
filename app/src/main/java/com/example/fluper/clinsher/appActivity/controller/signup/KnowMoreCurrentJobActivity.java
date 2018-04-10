package com.example.fluper.clinsher.appActivity.controller.signup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.example.fluper.clinsher.appActivity.controller.utils.CountryList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KnowMoreCurrentJobActivity extends AppCompatActivity implements
        SectorFragment.setOnDataListener,ContractFragment.setContractTypeListener{

    private RelativeLayout rl;
    private TextView toolText;
    private Switch workForceToggle;
    private Intent workForceToggleIntent;
    private TextView country;
    private ArrayList<CountryList.Country> countryArrayList;
    private Spinner spinner;
    private Spinner citySpinner;
    private String countryCode;
    private ArrayList<String>  cityList;
    private TextView etSector;
    private Button takeMeButton;
    private Intent takeMeIntent;
    private RelativeLayout jobEntryLayout;
    private SharedPreferences sharedPreferences;
    private String   token;
    private EditText jobTitle;
    private EditText company;
    private TextView sector;
    private TextView contractType;
    private static TextView startDate;
    private static TextView endDate;
    private static boolean flag ;
    private Switch stillEmployedSwitch;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog alertDialog;
    private View endDateView;
    private LinearLayout endDateLinearLayout;


    @Override
    protected void onPause() {
        super.onPause ();
        AppUtil.dismiss (KnowMoreCurrentJobActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more_current_job);

        //find All id
        gettingId();

        // all Intent
        allIntent();



        CountryList countryList = new CountryList ();
        countryArrayList = countryList.gettingCountry ();
        countrySpinner ();


      /*  LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.country_list_view, null);
        ListView lv = view.findViewById (R.id.list_view_country);
        ArrayAdapter arrayAdapter = new ArrayAdapter (this,
                android.R.layout.simple_list_item_1,countryArrayList);
        lv.setAdapter (arrayAdapter);

        //inflateLayout (R.layout.country_list_view);*/



        stillEmployedSwitch.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(stillEmployedSwitch.isChecked ()){
                    endDateLinearLayout.setVisibility (View.INVISIBLE);
                }
                else
                    endDateLinearLayout.setVisibility (View.VISIBLE);

            }
        });

        workForceToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startActivity(workForceToggleIntent);
                workForceToggle.setChecked (false);

            }
        });



       /* workForceToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(workForceToggleIntent);
            }
        });*/

        etSector.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addFragment (new SectorFragment ());
               }
        });

        contractType.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addFragment (new ContractFragment());
                }
        });


        startDate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                flag = true;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        endDate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                flag = false;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });




        // click on take me button and go to profile
        takeMeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String uTitle = jobTitle.getText ().toString ().trim ();
                String uCompanyName = company.getText ().toString ().trim ();
                String uSector = sector.getText ().toString ().trim ();
                String uCountryName = spinner.getSelectedItem ().toString ().trim ();
                String uCityName = citySpinner.getSelectedItem ().toString ().trim ();
                String uContractType = contractType.getText ().toString ().trim ();
                String uStartDate = startDate.getText ().toString ().trim ();
                String uEndDate = endDate.getText ().toString ().trim ();
                sharedPreferences = getSharedPreferences("logInAccessToken",
                        Context.MODE_PRIVATE);
                token = sharedPreferences.getString ("acessToken","no data found");

                if(isValidDetails (uTitle,uCompanyName,uSector,uCountryName,uCityName,uContractType,
                        uStartDate,uEndDate)){

                    experienceUserDetail (token,uCompanyName,uTitle,countryCode,uCountryName,
                            uCityName,uSector,uContractType,uStartDate,uEndDate);
                    AppUtil.showProgressDialog (KnowMoreCurrentJobActivity.this);
                    jobTitle.setText ("");
                    sector.setText ("");
                    company.setText ("");
                    spinner.setSelected (false);
                    citySpinner.setSelected (false);
                    contractType.setText ("");
                    startDate.setText ("");
                    endDate.setText ("");

                }

            }
        });




       /* spinner.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateSpinner ();
            }
        });*/


       /* spinner.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

               stateSpinner ();
                *//* CountryList countryList = new CountryList ();
                countryArrayList = countryList.gettingCountry ();

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.country_list_view, null);
                ListView lv = view.findViewById (R.id.list_view_country);
                ArrayAdapter arrayAdapter = new ArrayAdapter (getApplicationContext (),
                        android.R.layout.simple_list_item_1,countryArrayList);
                lv.setAdapter (arrayAdapter);*//*


            }
        });*/
    }



    //user detail validation
    public  boolean isValidDetails(String uTitle, String uCompanyName , String sector,
                                   String countryName,String cityName,String contractType,
                                   String stratDate,String endDate) {
        boolean flag;

        if(uTitle.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter Job Title",
                    Toast.LENGTH_SHORT).show();
        }
        else if(uCompanyName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter Company Name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(sector.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter sector",
                    Toast.LENGTH_SHORT).show();
        }

        else if(countryName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter country Name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(cityName.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter city Name",
                    Toast.LENGTH_SHORT).show();
        }

        else if(contractType.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter contract type",
                    Toast.LENGTH_SHORT).show();
        }

        else if(stratDate.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter start date",
                    Toast.LENGTH_SHORT).show();
        }


        else if(endDate.equals("")){
            flag = false;
            Toast.makeText(KnowMoreCurrentJobActivity.this, "Please enter end date",
                    Toast.LENGTH_SHORT).show();
        }


        else
            return true;

        return flag;

    }


  /*  //inflate layouts
    public View inflateLayout(int layout){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        ListView lv = view.findViewById (R.id.contract_list_view_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1,getResources ().getStringArray
                (R.array.contract_type));
        lv.setAdapter (adapter);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable (Color.TRANSPARENT));
        alertDialog.show();
        return view;
    }*/



    // All Intent
    public void allIntent(){
        workForceToggleIntent = new Intent(this, KnowMoreAboutYouActivity.class);
        takeMeIntent = new Intent (this, ProfileActivity.class);

    }

    // find id
    @SuppressLint("WrongViewCast")
    public void gettingId(){
        rl = findViewById(R.id.include_tool_more_current_job);
        toolText = rl.findViewById(R.id.tv_tool_textt);
        workForceToggle = findViewById(R.id.workforce_toogle);
        spinner = findViewById (R.id.et_country_more_current_job);
        citySpinner = findViewById (R.id.et_city_more_current_job);

        toolText.setText("We need to know more about your current job !");
        toolText.setTextSize(16);
        etSector = findViewById (R.id.et_sector_more_current_job);
        takeMeButton = findViewById (R.id.btn_take_me_more_current_job);
        jobEntryLayout = findViewById (R.id.job_detail_rel_layout);

        jobTitle = findViewById (R.id.et_job_title_more_cuurent_job);
        company = findViewById (R.id.et_compnay_more_cuurent_job);
        sector = findViewById (R.id.et_sector_more_current_job);
        contractType = findViewById (R.id.et_contract_type_more_cuurent_job);
        startDate = findViewById (R.id.et_starting_date_more_current_job);
        endDate = findViewById (R.id.et_ending_date_more_current_job);
        stillEmployedSwitch = findViewById (R.id.still_employed_toogle);
        endDateView = findViewById (R.id.ending_date_vieww);
        endDateLinearLayout = findViewById (R.id.end_date_view_linear_layout);
    }


    //spinner
    public void countrySpinner(){
        ArrayAdapter<ArrayList> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, countryArrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select your Country");

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0) {
                    CountryList.Country country = countryArrayList.get (i-1);
                    countryCode = country.getCode ();
                    getCityFromAPI (countryCode);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void citySpinner(ArrayList<String> clist){

        //ArrayList<String> clist = getCityFromAPI (countryCode);
        ArrayAdapter<ArrayList> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, clist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setPrompt("Select your City");

        citySpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //CountryList.Country country = countryArrayList.get (i);
               // String countryCode  =  country.getCode ();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }



    //Retrofit (API hit)
    private ArrayList<String> getCityFromAPI(String countryId) {

        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.getCity (countryId);

        call.enqueue(new Callback<ServerResponse> () {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(KnowMoreCurrentJobActivity.this, "Succeess",
                            Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body();
                    //User user = serverResponse.user;
                    cityList = serverResponse.cityList;
                    citySpinner (cityList);



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


    //Add Fragment
    public void addFragment(android.support.v4.app.Fragment fr){
        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.fragment_container_know_more_about_job, fr);
        ft.commit();
    }


    //Api for store detail of experiencd user detial


    //Retrofit (API hit)
    private boolean experienceUserDetail(String accessToken,String companyName,String jobTitle,
                                      String countryId,String countryName,String city,String sector,
                                      String contractType,String startDate,String endDate) {

         flag = false;
        ApiInterface apiService = APiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiService.sendUserExperienceDetails (accessToken,companyName,
                jobTitle,countryId,countryName,city,sector,contractType,startDate,endDate);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    flag = true;
                    Toast.makeText(KnowMoreCurrentJobActivity.this, "you are signUp Suceessfully",
                            Toast.LENGTH_SHORT).show();
                    ServerResponse serverResponse = response.body();
                    User user = serverResponse.user;
                  //  AppUtil.showProgressDialog (KnowMoreCurrentJobActivity.this);
                    startActivity (takeMeIntent);
                    AppUtil.dismiss (KnowMoreCurrentJobActivity.this);


                }else{
                    try {
                        String errorMessage = response.errorBody().string();
                        Log.d("test", "Error : " + errorMessage);
                        AppUtil.dismiss (KnowMoreCurrentJobActivity.this);

                    } catch (IOException e) {
                        AppUtil.dismiss (KnowMoreCurrentJobActivity.this);

                        //e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("test","error "+t.getMessage());
                AppUtil.dismiss (KnowMoreCurrentJobActivity.this);

                t.printStackTrace();
            }
        });
      return flag;
    }


    @Override
    public void sendDataFromFragment(String s) {
        sector.setText (s);
    }

    @Override
    public void getContractType(String contractTyp) {
        contractType.setText (contractTyp);
    }

    //Date Picker Dialog
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String date = day+"/"+month+"/"+year;
            if(flag)
              startDate.setText (date);
            else
                endDate.setText (date);


        }
    }

    //change  relative layout tool text
    public void changeToolText(int includeLayout,int textView, String text){
        RelativeLayout rl = findViewById (includeLayout);
        TextView tv = rl.findViewById (textView);
        tv.setText (text);

    }
}




