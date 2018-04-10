package com.example.fluper.clinsher.appActivity.controller.retrofit;
import com.example.fluper.clinsher.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fluper on 13/3/18.
 */

/*public class APiClient {

  //  public static final String BASE_URL = "https://reqres.in/api/register/";
      public static final String BASE_URL = "https://18.221.67.234:3000/users/";
      private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}*/

public class APiClient {
    public static final String BASE_URL = "http://18.221.67.234:3000/users/";
   // public static final String BASE_URL = "http://18.216.14.216:3005/user/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout (60, TimeUnit.SECONDS);


        // add logging as last interceptor only in Development Mode.
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logging);
        }


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
