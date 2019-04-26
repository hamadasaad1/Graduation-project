package com.ibnsaad.thedcc.Network.RetrofitNetwork;

import com.ibnsaad.thedcc.Model.PhotoModel;
import com.ibnsaad.thedcc.Model.Users;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
    @GET("api/Users/{id}")
    Call<Users> getUser(@Path("id") int id);

    @POST("api/users/{userId}/photos")
    Call<PhotoModel> setPhoto(@Path("userId")int id,
                               String url,
                              @Field("description") String description,
                              File file,
                              @Field("dateAdded") String dateAdded,
                              @Field("publicId") String publicId


    );
}
