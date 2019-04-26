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
import com.ibnsaad.thedcc.Network.AuthHelper;
import com.ibnsaad.thedcc.Network.Token;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        AndroidNetworking.post("http://thedccapp.com/api/Auth/login")
                .addJSONObjectBody(jsonObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                               String token=  response.getString("token");
                            //Token token1= (Token) response.get("token");
                               Log.d("username",token);
                               JSONObject object=response.getJSONObject("user");
                               int id=object.getInt("id");

                            //Log.d("id",String.valueOf(id));
                                saveSessionDetails( token,id);

                           // Toast.makeText(LoginActivity.this, ""+mAuthHelper.getId(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Login done... ",
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,
                                    "JSONException "+e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this, "Can't Login Now..."
                                        +anError.getErrorBody(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
