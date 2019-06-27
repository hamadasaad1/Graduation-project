package com.ibnsaad.thedcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.util.Log;
import android.widget.Toast;

import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseBodyAreas;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseBulletin;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseDrugs;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseGetSympotByBodyAreasId;
import com.ibnsaad.thedcc.Model.DiagnosisModel.ResponseUserHistory;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Client;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosisActivity extends AppCompatActivity {

    String TAG=DiagnosisActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
       // getBodyAreas();
       // getHistoryUser(1);
       // getSympotBody(1);
       // createBodyArea(1,"Head");
      //  getBulletin();
      //  getDrugs();
    }


    //method for get body area
    private void getBodyAreas(){
        Client client=new Client();

        Service service=client.getClient().create(Service.class);
        Call<List<ResponseBodyAreas>> call=service.getBodyAreas();
        call.enqueue(new Callback<List<ResponseBodyAreas>>() {
            @Override
            public void onResponse(Call<List<ResponseBodyAreas>> call, Response<List<ResponseBodyAreas>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DiagnosisActivity.this, "Successful"
                            , Toast.LENGTH_SHORT).show();
                    for (ResponseBodyAreas bodyAreas : response.body()){
                        String area=bodyAreas.getNameArea();
                        Log.d(TAG,area);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ResponseBodyAreas>> call, Throwable t) {

                Toast.makeText(DiagnosisActivity.this, "Try again"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //method for post Body area
    private void createBodyArea(int id,String name){
        ResponseBodyAreas areas=new ResponseBodyAreas(id,name);
        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<ResponseBodyAreas> call=service.createBodyAreas(areas);
        call.enqueue(new Callback<ResponseBodyAreas>() {
            @Override
            public void onResponse(Call<ResponseBodyAreas> call, Response<ResponseBodyAreas> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DiagnosisActivity.this, "isSuccessful"
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBodyAreas> call, Throwable t) {
                Toast.makeText(DiagnosisActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //method for get history for user
    private void getHistoryUser(int id){
        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<List<ResponseUserHistory>> call=service.getUserHistory(id);
        call.enqueue(new Callback<List<ResponseUserHistory>>() {
            @Override
            public void onResponse(Call<List<ResponseUserHistory>> call, Response<List<ResponseUserHistory>> response) {
                if (response.isSuccessful()){
                    for (ResponseUserHistory userHistory : response.body()){
                        String name=userHistory.getDrugName();
                        Log.d(TAG,name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseUserHistory>> call, Throwable t) {

            }
        });
    }

    //method for get sympotByBodyAreaId
    private void getSympotBody(int bodyId){

        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<List<ResponseGetSympotByBodyAreasId>> call=service.getSympotBYBody(bodyId);
        call.enqueue(new Callback<List<ResponseGetSympotByBodyAreasId>>() {
            @Override
            public void onResponse(Call<List<ResponseGetSympotByBodyAreasId>> call, Response<List<ResponseGetSympotByBodyAreasId>> response) {
                if (response.isSuccessful()){
                    for (ResponseGetSympotByBodyAreasId sympot : response.body()){
                        String name=sympot.getSymptomName();
                        Log.d(TAG,name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGetSympotByBodyAreasId>> call, Throwable t) {

            }
        });
    }
    //method for get Bulletin
    private void getBulletin(){
        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<List<ResponseBulletin>> call=service.getBulletin();
        call.enqueue(new Callback<List<ResponseBulletin>>() {
            @Override
            public void onResponse(Call<List<ResponseBulletin>> call, Response<List<ResponseBulletin>> response) {
                if (response.isSuccessful()){
                    for (ResponseBulletin bulletin : response.body())
                    {

                        String name=bulletin.getDrugName();
                        Log.d(TAG,name);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ResponseBulletin>> call, Throwable t) {

            }
        });
    }
    private void getDrugs(){
        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<List<ResponseDrugs>> call=service.getAllDrugs();
        call.enqueue(new Callback<List<ResponseDrugs>>() {
            @Override
            public void onResponse(Call<List<ResponseDrugs>> call, Response<List<ResponseDrugs>> response) {
                if (response.isSuccessful()){
                    for (ResponseDrugs drugs : response.body()){
                        String name=drugs.getDrugName();
                        Log.d(TAG,"Drugs:"+name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDrugs>> call, Throwable t) {

            }
        });
    }


}
