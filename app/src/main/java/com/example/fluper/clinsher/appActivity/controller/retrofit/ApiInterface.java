package com.example.fluper.clinsher.appActivity.controller.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by fluper on 13/3/18.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("manualSignup")
    Call<ServerResponse> registerUser(@Field("firstName") String firstName,
                                     @Field("lastName") String lastName ,
                                     @Field("email") String email,
                                     @Field("password") String password);

  /*  @FormUrlEncoded
    @POST("signup")
    Call<ServerResponse> registerUser(@Field("first_name") String firstName,
                                      @Field("last_name") String lastName ,
                                      @Field("email") String email,
                                      @Field("password") String password);
*/


    @FormUrlEncoded
    @POST("login")
    Call<ServerResponse> logInUser(@Field("email") String email,
                                   @Field("password") String password );


    @FormUrlEncoded
    @POST("getCity")
    Call<ServerResponse> getCity(@Field("countryId") String countryId);

    @FormUrlEncoded
    @POST("updateUserMobile")
    Call<ServerResponse> updateUserMobile(@Header ("accessToken") String acessToken,
                                          @Field ("mobileNumber") String mobileNumber,
                                          @Field ("mobileCode") String CountryCode
                                            );


}
