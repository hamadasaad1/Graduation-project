package com.ibnsaad.thedcc.network.RetrofitNetwork;

import com.ibnsaad.thedcc.model.PhotoModel;
import com.ibnsaad.thedcc.model.Users;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
    @GET("api/Users/{id}")
    Call<Users> getUser(@Path("id") int id);

    @POST("api/users/{userId}/photos")
    Call<PhotoModel> setPhoto(@Path("userId") int id,
                              String url,
                              @Field("description") String description,
                              File file,
                              @Field("dateAdded") String dateAdded,
                              @Field("publicId") String publicId
    );


}
