package com.ibnsaad.thedcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.ibnsaad.thedcc.activities.LoginActivity;
import com.ibnsaad.thedcc.adapter.UsersAdapter;
import com.ibnsaad.thedcc.model.User;
import com.ibnsaad.thedcc.network.AuthHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static String TAG=MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<User> mUserList =new ArrayList<>();
    private UsersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private    String token;
    private AuthHelper mAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuthHelper=AuthHelper.getInstance(this);


//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//          token = extras.getString("token");
//            Log.d(TAG,token);
//        }
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (mAuthHelper.isLoggedIn()){
            getAllUser();
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }


    }

    private void getAllUser(){

        AndroidNetworking.get("http://thedccapp.com/api/User")
                .setTag(this)
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer "+mAuthHelper.getIdToken())
                .build()
                .getAsObjectList(User.class, new ParsedRequestListener<List<User>>() {
                    @Override
                    public void onResponse(List<User> users) {
                        // do anything with response
                        Log.d(TAG, "userList size : " + users.size());
                        mUserList =users;
                        adapter=new UsersAdapter(mUserList,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        for (User user : users) {

                            Log.d(TAG, "firstname : " + user.getUsername());

                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d(TAG,"error"+anError.getErrorDetail());
                        Toast.makeText(MainActivity.this, "error"
                                +anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile_activity:
                //newGame();
               startActivity( new Intent(this, EditProfileActivity.class));
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //method for logout
    private void logout() {

        mAuthHelper.clear();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
