package com.example.fluper.clinsher.appActivity.controller.retrofit;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by fluper on 13/3/18.
 */

public interface ApiInterface {

   /* @FormUrlEncoded
    @POST("manualSignup")
    Call<ServerResponse> registerUser(@Field("firstName") String firstName,
                                     @Field("lastName") String lastName ,
                                     @Field("email") String email,
                                     @Field("password") String password);
*/
   /*@Multipart
   @POST("manualSignup")
   Call<ServerResponse> registerUser(@Part MultipartBody.Part profileImage,
                                     @Part("firstName") RequestBody firstName,
                                     @Part("lastName") RequestBody lastName ,
                                     @Part("email") RequestBody email,
                                     @Part("password") RequestBody password);@Multipart*/
   @Multipart
   @POST("manualSignup")
   Call<ServerResponse> registerUser(@Part MultipartBody.Part profileImage,
                                     @PartMap Map<String, RequestBody> map
   );





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

    @FormUrlEncoded
    @POST("varifyOTP")
    Call<ServerResponse>  verifyOtp(@Header ("accesstoken") String acessToken,
                                    @Field ("otp" ) String otp);


    @FormUrlEncoded
    @POST("sendUserExperienceDetails")
    Call<ServerResponse> sendUserExperienceDetails(@Header("accessToken") String token,
                                                         @Field("companyName") String companyName,
                                                         @Field("jobTitle") String jobTitle,
                                                         @Field("countryId") String countryId,
                                                         @Field("countryName") String countryName,
                                                         @Field("city") String city,
                                                         @Field("sector") String sector,
                                                         @Field("contractType") String contractType,
                                                         @Field("startDate") String startDate,
                                                         @Field("endDate") String endDate);
    @FormUrlEncoded
    @POST("sendUserFresherDetails")
    Call<ServerResponse> sendUserFresherDetails(@Header ("accesstoken") String token,
                                                @Field ("position") String position,
                                                @Field ("school") String school,
                                                @Field ("schoolFrom") String fromYear,
                                                @Field ("schoolTo") String toYear,
                                                @Field ("countryId") String countryId,
                                                @Field ("countryName") String countryName,
                                                @Field ("city") String cityName,
                                                @Field ("degree") String degree,
                                                @Field ("studyField") String studyField);
}
