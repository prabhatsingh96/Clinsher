package com.example.fluper.clinsher.appActivity.controller.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.example.fluper.clinsher.appActivity.controller.retrofit.APiClient;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ApiInterface;
import com.example.fluper.clinsher.appActivity.controller.retrofit.ServerResponse;
import com.example.fluper.clinsher.appActivity.controller.search.SearchFragmentOne;
import com.example.fluper.clinsher.appActivity.controller.utils.CountryList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KnowMoreCurrentJobActivity extends AppCompatActivity {

    private RelativeLayout rl;
    private TextView toolText;
    private Switch workForceToggle;
    private Intent workForceToggleIntent;
    private TextView country;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog alertDialog;
    private ArrayList<CountryList.Country> countryArrayList;
    private Spinner spinner;
    private Spinner citySpinner;
    private String countryCode;
    private ArrayList<String>  cityList;
    private EditText etSector;
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






        workForceToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(workForceToggleIntent);
            }
        });

        etSector.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addSectorFragment ();
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


    //inflate layouts
    public View inflateLayout(int layout){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable (Color.TRANSPARENT));
        alertDialog.show();
        return view;
    }



    // All Intent
    public void allIntent(){
        workForceToggleIntent = new Intent(this, KnowMoreAboutYouActivity.class);

    }

    // find id
    @SuppressLint("WrongViewCast")
    public void gettingId(){
        rl = findViewById(R.id.include_tool_more_current_job);
        toolText = rl.findViewById(R.id.tv_tool_text);
        workForceToggle = findViewById(R.id.workforce_toogle);
        spinner = findViewById (R.id.et_country_more_current_job);
        citySpinner = findViewById (R.id.et_city_more_current_job);

        toolText.setText("We need to know more about your current job !");
        toolText.setTextSize(16);
        etSector = findViewById (R.id.et_sector_more_current_job);

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
    public void addSectorFragment(){
        android.support.v4.app.FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction ft = frg.beginTransaction();
        ft.add(R.id.fragment_container_know_more_about_job, new SectorFragment ());
        ft.commit();
    }


}



