package com.ibnsaad.thedcc.Network.RetrofitNetwork;

import android.content.Context;

import com.ibnsaad.thedcc.Network.AuthHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    static Context mContext;
    public static final String BASE_URL="http://thedccapp.com/";
    public static Retrofit retrofit=null;
     static AuthHelper mAuthHelper=AuthHelper.getInstance(mContext);
    static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + mAuthHelper.getIdToken())
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
