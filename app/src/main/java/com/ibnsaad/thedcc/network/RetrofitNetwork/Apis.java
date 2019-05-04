package com.ibnsaad.thedcc.network.RetrofitNetwork;

import com.google.gson.JsonObject;
import com.ibnsaad.thedcc.model.LoginRespons;
import com.ibnsaad.thedcc.model.PhotoModel;
import com.ibnsaad.thedcc.model.User;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Apis {
    @GET("api/User/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("api/users/{userId}/photos")
    Call<PhotoModel> setPhoto(@Path("userId") int id,
                              String url,
                              @Field("description") String description,
                              File file,
                              @Field("dateAdded") String dateAdded,
                              @Field("publicId") String publicId
    );

    @Headers({"Content-Type: application/json"})
    @FormUrlEncoded
    @POST("api/Auth/login")
    Call<LoginRespons> logIn(@Field("username") String userName,@Field("password") String password);

    @POST("api/Auth/login")
    Call<LoginRespons> logIn(@Body JsonObject jsonObject);

}
