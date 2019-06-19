package com.ibnsaad.thedcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnsaad.thedcc.Model.AllMessagesResponse;
import com.ibnsaad.thedcc.Model.MessageForCreationDto;
import com.ibnsaad.thedcc.Model.ResponseMessagesWithId;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Client;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_user_details)
    TextView mUserName;

    @BindView(R.id.imageView_details)
    ImageView mProfile;

    @BindView(R.id.bt_message)
    Button mSend;
    @BindView(R.id.bt_make_message)
    Button mMakeMeaage;

    @BindView(R.id.bt_getAllMessage)
    Button mBtAllMessage;

    private String name,profile;
            private int mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intentStartActivity=getIntent();
        if (intentStartActivity.hasExtra("name")){
            name=intentStartActivity.getStringExtra("name");
            profile=intentStartActivity.getStringExtra("profile");
            mId=intentStartActivity.getIntExtra("id",0);
            //Toast.makeText(this, "id= "+mId, Toast.LENGTH_SHORT).show();


            if (profile==null){

            }else {
                Picasso.get()
                        .load(profile)
                        .placeholder(R.drawable.profile)
                        .into(mProfile);
            }
            
            mUserName.setText(name);


        }else {
            Toast.makeText(this, "Not Data", Toast.LENGTH_SHORT).show();
        }
        //bt to get message
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMessage(mId,1);
            }
        });

        //bt to make message
        mMakeMeaage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(mId);
            }
        });
        //bt for all message
        mBtAllMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllMessages(mId,1);
            }
        });

    }

    //get message for this user is login now
    private void getMessage(int userId,int messageId){

        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<ResponseMessagesWithId> call=service.getMessages(userId,messageId);

        call.enqueue(new Callback<ResponseMessagesWithId>() {
            @Override
            public void onResponse(Call<ResponseMessagesWithId> call,
                                   Response<ResponseMessagesWithId> response) {

                if(response.isSuccessful()){
                    String senderName=response.body().getSenderId().toString();
                    Toast.makeText(DetailsActivity.this, "message done"+senderName
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessagesWithId> call, Throwable t) {

                Toast.makeText(DetailsActivity.this, "try again",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    //for make message with content
    private void makeMessage(int userId){

        //make object for class messageForCreation
        int senderId =1; //for user to need send message
        int recipientId=2;
        String  messageSent="2019-06-19T12:31:14.072Z"; //this time for message
        String messageContent="hi How Are ?"; //message content

        MessageForCreationDto message=new MessageForCreationDto(senderId,recipientId,messageSent,messageContent);

        Client client=new Client();
        Service service=client.getClient().create(Service.class);
        Call<MessageForCreationDto> call=service.makeMessageForCreation(userId,message);
        call.enqueue(new Callback<MessageForCreationDto>() {
            @Override
            public void onResponse(Call<MessageForCreationDto> call, Response<MessageForCreationDto> response) {

                if (response.isSuccessful()){
                    String content=response.body().getContent();
                    Toast.makeText(DetailsActivity.this, "Content : "+content,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageForCreationDto> call, Throwable t) {

                Toast.makeText(DetailsActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //for get all messages
    private void getAllMessages(int userId, final int recipientId){

        Client client =new Client();

        Service service=client.getClient().create(Service.class);
        Call<List<AllMessagesResponse>> call=service.getAllMessages(userId,recipientId);
        call.enqueue(new Callback<List<AllMessagesResponse>>() {
            @Override
            public void onResponse(Call<List<AllMessagesResponse>> call, Response<List<AllMessagesResponse>> response) {
                if (response.isSuccessful()){
                    for (AllMessagesResponse messagesResponse : response.body()){
                        Toast.makeText(DetailsActivity.this, ""+
                                messagesResponse.getContent(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AllMessagesResponse>> call, Throwable t) {

                Toast.makeText(DetailsActivity.this, "try Again"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

}
