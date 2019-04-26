package com.ibnsaad.thedcc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ibnsaad.thedcc.Network.Api;
import com.ibnsaad.thedcc.Network.AuthHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.input_name)
    EditText mNameText;

    @BindView(R.id.input_city)
    EditText mCityText;
    @BindView(R.id.input_Country)
    EditText mCountryText;
    @BindView(R.id.input_password)
    EditText mPasswordText;
    @BindView(R.id.input_reEnterPassword)
    EditText mReEnterPasswordText;

    @BindView(R.id.input_know_As)
    EditText mKnowAs;
    @BindView(R.id.btn_signup)
    Button mSignupButton;
    @BindView(R.id.link_login)
    TextView mLoginLink;
    @BindView(R.id.input_birthday)
    EditText mBirthDay;
    @BindView(R.id.radio_group_gander)
    RadioGroup mRadioGroupGender;

    private RadioButton mRadioSexButton;
    Calendar myCalendar;

    private AuthHelper mAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mAuthHelper=AuthHelper.getInstance(this);

        AndroidNetworking.initialize(getApplicationContext());

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();


            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left, R.anim.push_left_out);
            }
        });

        getBirthDay();
    }


    private void signup() {

        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mSignupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = mNameText.getText().toString();

        String city = mCityText.getText().toString();
        String password = mPasswordText.getText().toString();
        String country=mCountryText.getText().toString();
        String birthday = mBirthDay.getText().toString();
        String knowAs=mKnowAs.getText().toString();
        int selectedId = mRadioGroupGender.getCheckedRadioButtonId();
        mRadioSexButton = (RadioButton) findViewById(selectedId);
        String gender = mRadioSexButton.getText().toString();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDateandTime = sdf.format(new Date());
        //register method

        registerWithNetworkFaster(name,password,gender,knowAs,
                birthday,city,country,currentDateandTime,currentDateandTime);



        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        mSignupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed",
                Toast.LENGTH_LONG).show();

        mSignupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String city = mCityText.getText().toString();
        String country=mCountryText.getText().toString();
        String password = mPasswordText.getText().toString();
        String reEnterPassword = mReEnterPasswordText.getText().toString();
        String birthday = mBirthDay.getText().toString();
        String knowAs=mKnowAs.getText().toString();

        if (name.isEmpty() || name.length() < 3 || name.length() > 14) {
            mNameText.setError("at least 3 characters");
            valid = false;
        } else {
            mNameText.setError(null);
        }

        if (city.isEmpty()) {
            mCityText.setError("Enter Valid City");
            valid = false;
        } else {
            mCityText.setError(null);
        }

        if (country.isEmpty()) {
            mCountryText.setError("Enter Valid Country");
            valid = false;
        } else {
            mCountryText.setError(null);
        }

        if (knowAs.isEmpty()) {
            mKnowAs.setError("Enter Valid Know As");
            valid = false;
        } else {
            mKnowAs.setError(null);
        }

        if (birthday.isEmpty()) {
            mBirthDay.setError("Enter Your Birthday");
            valid = false;
        } else {
            mBirthDay.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10
                || !(reEnterPassword.equals(password))) {
            mReEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            mReEnterPasswordText.setError(null);
        }
        if (mRadioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select Gender",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;

    }

    private void getBirthDay()
    {
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        mBirthDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBirthDay.setText(sdf.format(myCalendar.getTime()));

    }

    private void saveSessionDetails(@NonNull String token,int id) {
        mAuthHelper.setIdToken(token);
        mAuthHelper.setIdUser(id);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        intent.putExtra("token",token);
        startActivity(intent);
    }

    private void registerWithNetworkFaster(final String userName, final String password, String gender, String KnowAs,
                                           String birthday, String city, String country

                                           , String created, String lastSeen) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",password);
            jsonObject.put("gender",gender);
            jsonObject.put("knownAs",KnowAs);
            jsonObject.put("dateOfBirth",birthday);
            jsonObject.put("city",city);
            jsonObject.put("country",country);
            jsonObject.put("created",created);
            jsonObject.put("lastActive",lastSeen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("http://thedccapp.com/api/Auth/register")
                .addJSONObjectBody(jsonObject)// posting json
                .setTag("test")
                .addHeaders("Bearer","hamadasaad")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

//                        Toast.makeText(RegisterActivity.this, "Done Register",
//                                Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                        loginWithNetworkFaster(userName, password);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("hamada",error.getErrorBody());
                        Log.d("hamadade",error.getErrorDetail());
                    }
                });

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
                            //set token between activity
                            String token=response.getString("token");
                            Log.d(TAG,token);
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            intent.putExtra("token",token);
//                            startActivity(intent);
                            JSONObject object=response.getJSONObject("user");
                            int id=object.getInt("id");
                            saveSessionDetails( token,id);
                            Toast.makeText(RegisterActivity.this, "Done Register",
                                Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this,
                                    "some thing happen register later  ",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                       Log.d(TAG,anError.getErrorDetail());
                    }
                });
    }
}
