package com.ibnsaad.thedcc.Network.RetrofitNetwork;

import com.google.gson.JsonObject;
import com.ibnsaad.thedcc.Model.AllMessagesResponse;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseBodyAreas;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseBulletin;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseDrugs;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseGetSympotByBodyAreasId;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseUserHistory;
import com.ibnsaad.thedcc.Model.MessageForCreationDto;
import com.ibnsaad.thedcc.Model.PhotoModel;
import com.ibnsaad.thedcc.Model.ResponseMessagesWithId;
import com.ibnsaad.thedcc.Model.TokenResponse;
import com.ibnsaad.thedcc.Model.Users;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    // for login
    @POST("api/Auth/login")
    Call<TokenResponse> logIn(@Body JsonObject jsonObject);
//get all user
    @GET("api/Users")
    Call<List<Users>>getAllUser();

    //for message with user id and message id
    @GET("api/users/{userId}/Messages/{id}")
    Call<ResponseMessagesWithId> getMessages(@Path("userId") int userId,
                                             @Path("id") int idMessage);
    //for sent message with content
    @POST("api/users/{userId}/Messages")
    Call<MessageForCreationDto> makeMessageForCreation(@Path("userId") int userId,
                                                       @Body MessageForCreationDto message);

    @GET("api/users/{userId}/Messages/thread/{recipientId}")
    Call<List<AllMessagesResponse>>getAllMessages(@Path("userId") int userId,
                                            @Path("recipientId") int recipientId);


    //diagnosis
    //body areas
    @POST("api/Diagnosis/createBodyArea")
    Call<ResponseBodyAreas>createBodyAreas(@Body ResponseBodyAreas bodyAreas);

    @GET("api/Diagnosis/getBodyAreas")
    Call<List<ResponseBodyAreas>> getBodyAreas();

    //history user
    @POST("api/Diagnosis/GetUserHistory")
    Call<List<ResponseUserHistory>> getUserHistory(@Query("userId") int userId);
    //GetSympotByBodyAreasId
    @GET("api/Diagnosis/GetSympotByBodyAreasId")
    Call<List<ResponseGetSympotByBodyAreasId>> getSympotBYBody(@Query("BodyId") int bodyId);

    //Get drugs
    @GET("api/Diagnosis/GetAllDrugs")
    Call<List<ResponseDrugs>> getAllDrugs();
   //get Bulletin
    @GET("api/Diagnosis/getAllBulletin")
    Call<List<ResponseBulletin>> getBulletin();

}
