package com.ibnsaad.thedcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.ibnsaad.thedcc.Model.PhotoModel;
import com.ibnsaad.thedcc.Model.Users;
import com.ibnsaad.thedcc.Network.AuthHelper;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Client;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG=ProfileActivity.class.getSimpleName();
    private AuthHelper mAuthHelper;

    @BindView(R.id.name)
    TextView mTvName;
    @BindView(R.id.descrpition)
    EditText mETDescrpition;
    @BindView(R.id.image_select)
    ImageButton mBTImageSelect;
    String mImageUri;
    final int RESULT_LOAD_IMG=10;
    String description ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mAuthHelper=AuthHelper.getInstance(this);
        if (mAuthHelper.isLoggedIn()){
            //getProfileUser(mAuthHelper.getId());
            getUser(mAuthHelper.getId());
        }
        //to select image
        mBTImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                description=mETDescrpition.getText().toString();

            }
        });
    }

    //to get this user is current login
    private void getUser(int id){
        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<Users> call=service.getUser(id);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if (response.isSuccessful()){
                    Users user=response.body();
                    Toast.makeText(ProfileActivity.this,
                            " "+response.body(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "first : " + user.getUsername());
                    mTvName.setText(user.getUsername());
                    Log.d(TAG, "age : " + user.getAge());
                    Log.d(TAG, "id : " + user.getId());

                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "some think error",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mBTImageSelect.setImageBitmap(selectedImage);
                File file=new File(imageUri.getPath());
                mImageUri=imageUri.toString();
                Toast.makeText(ProfileActivity.this, mImageUri, Toast.LENGTH_LONG).show();

                uploadImage(description,mImageUri,file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImage(String Descrpition,String uri ,File file){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDateandTime = sdf.format(new Date());

        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<PhotoModel> call=service.setPhoto(mAuthHelper.getId(),uri,Descrpition,file,
               currentDateandTime,"12345" );
        call.enqueue(new Callback<PhotoModel>() {
            @Override
            public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                if (response.isSuccessful()){
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

    private void getProfileUser(int id){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.get("http://thedccapp.com//api/Users/{id}")
//                .addPathParameter("id", String.valueOf(id))
               // .addJSONObjectBody(jsonObject)
                .setTag(this)
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer "+mAuthHelper.getIdToken())
                .build()
                .getAsObject(Users.class, new ParsedRequestListener<Users>() {
                    @Override
                    public void onResponse(Users user) {
                        // do anything with response
                        Log.d(TAG, "id : " + user.getId());
                        Log.d(TAG, "firstname : " + user.getUsername());
                        Log.d(TAG, "lastname : " + user.getAge());
                    }



                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d(TAG,anError.getErrorDetail());
                        Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                        Log.d(TAG, "onError errorBody : " + anError.getErrorBody());

                    }
                });
    }
}
