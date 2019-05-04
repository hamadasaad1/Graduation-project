package com.ibnsaad.thedcc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.ibnsaad.thedcc.model.PhotoModel;
import com.ibnsaad.thedcc.model.User;
import com.ibnsaad.thedcc.network.AuthHelper;
import com.ibnsaad.thedcc.network.RetrofitNetwork.Client;
import com.ibnsaad.thedcc.network.RetrofitNetwork.Apis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getSimpleName();
    private AuthHelper mAuthHelper;


    private AppCompatEditText email, userName, userAge, userGender, address, city, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mAuthHelper = AuthHelper.getInstance(this);
        if (mAuthHelper.isLoggedIn()) {
            //getProfileUser(mAuthHelper.getId());
            getUser(mAuthHelper.getId());
        }

    }

    //to get this user is current login
    private void getUser(int id) {
        Client client = new Client();
        Apis apis = client.getClient().create(Apis.class);
        Call<User> call = apis.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(ProfileActivity.this,
                            " " + response.body(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "first : " + user.getUsername());
                    Log.d(TAG, "age : " + user.getAge());
                    Log.d(TAG, "id : " + user.getId());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "some think error",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadImage(String Descrpition, String uri, File file) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDateandTime = sdf.format(new Date());

        Client client = new Client();
        Apis apis = client.getClient().create(Apis.class);
        Call<PhotoModel> call = apis.setPhoto(mAuthHelper.getId(), uri, Descrpition, file,
                currentDateandTime, "12345");
        call.enqueue(new Callback<PhotoModel>() {
            @Override
            public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Image Successful done "
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhotoModel> call, Throwable t) {

                Toast.makeText(ProfileActivity.this, "try....",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getProfileUser(int id) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.get("http://thedccapp.com//api/User/{id}")
//                .addPathParameter("id", String.valueOf(id))
                // .addJSONObjectBody(jsonObject)
                .setTag(this)
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer " + mAuthHelper.getIdToken())
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {
                    @Override
                    public void onResponse(User user) {
                        // do anything with response
                        Log.d(TAG, "id : " + user.getId());
                        Log.d(TAG, "firstname : " + user.getUsername());
                        Log.d(TAG, "lastname : " + user.getAge());
                    }


                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d(TAG, anError.getErrorDetail());
                        Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                        Log.d(TAG, "onError errorBody : " + anError.getErrorBody());

                    }
                });
    }
}
