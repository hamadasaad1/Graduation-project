package com.ibnsaad.thedcc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.ibnsaad.thedcc.Model.TokenResponse;
import com.ibnsaad.thedcc.Model.Users;
import com.ibnsaad.thedcc.Network.AuthHelper;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Client;
import com.ibnsaad.thedcc.Network.RetrofitNetwork.Service;
import com.ibnsaad.thedcc.Network.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText mEmailText;
    @BindView(R.id.input_password)
    EditText mPasswordText;
    @BindView(R.id.btn_login)
    Button mLoginButton;
    @BindView(R.id.link_signup)
    TextView mSignupLink;

    private AuthHelper mAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuthHelper=AuthHelper.getInstance(this);


        if (mAuthHelper.isLoggedIn()) {
            startActivity(new Intent(this,MainActivity.class));
           // Toast.makeText(this, mAuthHelper.getUsername(), Toast.LENGTH_SHORT).show();
        }else {
            //for login when click buttom
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            mSignupLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this,
                            RegisterActivity.class);
                    startActivityForResult(intent, REQUEST_SIGNUP);
                    finish();
                    overridePendingTransition(R.anim.push_left,
                            R.anim.push_left_out);


                }
            });
        }

    }

    private void login() {

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();


        // TODO: Implement your own authentication logic here.
        loginWithNetworkFaster(email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed",
                Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() ||email.length()<4||email.length()>14) {
            mEmailText.setError("Enter a Valid Users Name Address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            mPasswordText.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    private void saveSessionDetails(@NonNull String token ,int id) {
        mAuthHelper.setIdToken(token);
        mAuthHelper.setIdUser(id);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        intent.putExtra("token",token);
        startActivity(intent);
    }
    private void loginWithNetworkFaster(String userName, String password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",password);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObject jsonObject1=new JsonObject();
        jsonObject1.addProperty("username",userName);
        jsonObject1.addProperty("password",password);

        Client client=new Client();
        Service service=client.getClient().create(Service.class);

        Call<TokenResponse> call=service.logIn(jsonObject1);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()){

                    String token=response.body().getToken();

                    Toast.makeText(LoginActivity.this, "Register done "+token
                            , Toast.LENGTH_SHORT).show();
                    Users users=response.body().getUser();
                    int id=users.getId();
                    List<Users> users1=new ArrayList<>();
                    users1.add(users);
                    saveSessionDetails( token,id);
                    for (Users use : users1) {

                        Log.d(TAG, "firstname : " + use.getUsername());

                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "try and try"
                        , Toast.LENGTH_SHORT).show();
            }
        });
        
        
//        AndroidNetworking.post("http://thedccapp.com/api/Auth/login")
//                .addJSONObjectBody(jsonObject)
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//
//                               String token=  response.getString("token");
//                            //TokenResponse token1= (TokenResponse) response.get("token");
//                               Log.d("username",token);
//                               JSONObject object=response.getJSONObject("user");
//                               int id=object.getInt("id");
//
//                            //Log.d("id",String.valueOf(id));
//                                saveSessionDetails( token,id);
//
//                           // Toast.makeText(LoginActivity.this, ""+mAuthHelper.getId(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(LoginActivity.this, "Login done... ",
//                                    Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            Toast.makeText(LoginActivity.this,
//                                    "JSONException "+e.getMessage(),
//                                    Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(LoginActivity.this, "Can't Login Now..."
//                                        +anError.getErrorBody(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}
