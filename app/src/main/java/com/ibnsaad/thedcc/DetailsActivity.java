package com.ibnsaad.thedcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_user_details)
    TextView mUserName;
    @BindView(R.id.tv_birthday)
    TextView mBirthday;
    @BindView(R.id.tv_city)
    TextView mCity;
    @BindView(R.id.imageView_details)
    ImageView mProfile;

    private String name,profile,city,birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intentStartActivity=getIntent();
        if (intentStartActivity.hasExtra("name")){
            name=intentStartActivity.getStringExtra("name");
            profile=intentStartActivity.getStringExtra("profile");
            city=intentStartActivity.getStringExtra("city");
            birthday=intentStartActivity.getStringExtra("birthday");

            if (profile==null){

            }else {
                Picasso.get()
                        .load(profile)
                        .placeholder(R.drawable.profile)
                        .into(mProfile);
            }
            
            mUserName.setText(name);
            mCity.setText(city);
            mBirthday.setText(birthday);

        }else {
            Toast.makeText(this, "Not Data", Toast.LENGTH_SHORT).show();
        }
    }
}
