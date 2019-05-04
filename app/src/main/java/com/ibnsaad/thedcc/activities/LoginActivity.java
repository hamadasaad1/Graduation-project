package com.ibnsaad.thedcc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ibnsaad.thedcc.OldRegisterActivity;
import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.listeners.ConnectivityListener;
import com.ibnsaad.thedcc.model.LoginRespons;
import com.ibnsaad.thedcc.network.RetrofitNetwork.BaseClient;
import com.ibnsaad.thedcc.utils.Connectivity;
import com.ibnsaad.thedcc.utils.Dialogs;
import com.ibnsaad.thedcc.utils.Tools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ConnectivityListener {

    private static final String TAG = "LoginActivity";

    private EditText email, password;
    private View progress;
    private RelativeLayout rootView;

    // internet
    private Connectivity connectivity;
    private Dialog noInternetDialog;
    private Dialog worningDialog;
    private boolean internetConnected;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initToolbar();
        initViews();
    }

    private void initViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rootView = findViewById(R.id.root_view);
        progress = findViewById(R.id.progress);
        setListeners();
        connectivity = new Connectivity(this, this);
    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");
        toolbar.setTitle("");
        Tools.setSystemBarColorInt(this, Color.parseColor("#006ACF"));

    }

    public void goRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void setListeners() {
        (findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (internetConnected)
//                    login();
//                else
//                    noInternetDialog = Dialogs.getInstance().showWorningDialog(LoginActivity.this, getString(R.string.no_internet_connection));

                login();
            }
        });
    }

    private void login() {
        String email_ = email.getText().toString();
        String password_ = password.getText().toString();

//        if (!emailMatches(email_)) {
//            email.setError(getString(R.string.not_valid_email));
//            Dialogs.getInstance().showSnack(LoginActivity.this, getString(R.string.not_valid_email));
//            Log.d(TAG, "creatNewUser: " + getString(R.string.not_valid_email));
//            return;
//        } else

        if (password_.equals("")) {
            password.setError(getString(R.string.password_is_required));
            Dialogs.getInstance().showSnack(LoginActivity.this, getString(R.string.password_is_required));
            Log.d(TAG, "creatNewUser: " + getString(R.string.password_is_required));
            return;
        }

        showProgress();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("username", email_);
        jsonObject1.addProperty("password", password_);

        BaseClient.getApi().logIn(jsonObject1).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void getConnectionType(String connectionType) {

    }

    @Override
    public void isConnected(boolean isConnected) {

        if (worningDialog != null) {
            worningDialog.dismiss();
        }
        if (!isConnected) {
            internetConnected = false;
            noInternetDialog = Dialogs.getInstance().showWorningDialog(this, getString(R.string.no_internet_connection));

        } else {
            internetConnected = true;
            if (noInternetDialog != null) {
                if (noInternetDialog.isShowing()) {
                    noInternetDialog.dismiss();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectivity = null;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noInternetDialog != null) {
            if (noInternetDialog.isShowing())
                noInternetDialog.dismiss();
        }
        if (worningDialog != null) {
            if (worningDialog.isShowing())
                worningDialog.dismiss();
        }
    }

    private void showProgress() {
        rootView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        rootView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }
}