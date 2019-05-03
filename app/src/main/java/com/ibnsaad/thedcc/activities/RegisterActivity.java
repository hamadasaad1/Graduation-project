package com.ibnsaad.thedcc.activities;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.listeners.ConnectivityListener;
import com.ibnsaad.thedcc.utils.Connectivity;
import com.ibnsaad.thedcc.utils.Dialogs;
import com.ibnsaad.thedcc.utils.Tools;
import com.ibnsaad.thedcc.widget.EslamDatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class RegisterActivity extends AppCompatActivity implements ConnectivityListener {

    private static final String TAG = "RegisterActivity";
    Location location;
    boolean gpsReq = false;
    private EditText email, password, rePassword, userName, userAge, userGender, address, city, country, state, phone;
    private View progress;
    private RelativeLayout rootView;
    private NestedScrollView mNestedScrollView;
    // internet
    private Connectivity connectivity;
    private boolean internetConnected;
    //dialogs
    private DatePickerDialog datePicker;
    private Dialog noInternetDialog;
    private Dialog worningDialog;
    private AlertDialog alertDialog;
    private ActionBar actionBar;
    private int REQUEST_PHONE_VERIFICATION = 1258;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initToolbar();
        initViews();
        initComponent();
    }

    private void scrollToTop() {
        int targetScroll = mNestedScrollView.getScrollY() + 1000;
        mNestedScrollView.scrollTo(0, targetScroll);
        mNestedScrollView.setSmoothScrollingEnabled(true);
        ViewCompat.setNestedScrollingEnabled(mNestedScrollView, false);
        final int currentScrollY = mNestedScrollView.getScrollY();
        ViewCompat.postOnAnimationDelayed(mNestedScrollView, new Runnable() {
            int currentY = currentScrollY;

            @Override
            public void run() {
                if (currentScrollY == mNestedScrollView.getScrollY()) {
                    ViewCompat.setNestedScrollingEnabled(mNestedScrollView, true);
                    return;
                }
                currentY = mNestedScrollView.getScrollY();
                ViewCompat.postOnAnimation(mNestedScrollView, this);
            }
        }, 5);
    }

    private void initViews() {


        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.re_password);
        userName = findViewById(R.id.user_name);
        userAge = findViewById(R.id.user_age);
        userGender = findViewById(R.id.user_gender);
        rootView = findViewById(R.id.root_view);
        progress = findViewById(R.id.progress);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        mNestedScrollView = findViewById(R.id.scroller);

    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Tools.setSystemBarColorInt(this, Color.parseColor("#006ACF"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitle(R.string.create_new_account);
    }

    private void initComponent() {
        (findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnected)
                    creatNewUser();
                else
                    noInternetDialog = Dialogs.getInstance().showWorningDialog(RegisterActivity.this, getString(R.string.no_internet_connection));
            }
        });
        (findViewById(R.id.user_age)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAgeDialog();
            }
        });
        (findViewById(R.id.user_gender)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDialog(v);
            }
        });

        (findViewById(R.id.sign_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });
        connectivity = new Connectivity(this, this);
    }

    private void showProgress() {
        rootView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        rootView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    private void creatNewUser() {
        final String name = userName.getText().toString();
        final String age = userAge.getText().toString();
        String passwordTesxt = password.getText().toString();
        String rePasswordTesxt = rePassword.getText().toString();
        final String user_gender = userGender.getText().toString();
        final String email_ = email.getText().toString();

    }

    private void scrollToView(final View view) {
        mNestedScrollView.scrollBy(0, 1);
        ObjectAnimator.ofInt(mNestedScrollView, "scrollY", view.getTop()).setDuration(700).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHONE_VERIFICATION && resultCode == RESULT_OK) {

        }
    }


    private void showAgeDialog() {

        EslamDatePickerDialog dialogfragment = new EslamDatePickerDialog();
        dialogfragment.setTextView(userAge);
        dialogfragment.show(getFragmentManager(), "Eslam");

    }

    private void showGenderDialog(final View v) {
        final String[] array = new String[]{
                getString(R.string.male), getString(R.string.female)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(getString(R.string.gender));
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void getConnectionType(String connectionType) {

    }

    @Override
    public void isConnected(boolean isConnected) {

        if (worningDialog != null) {
            worningDialog.dismiss();
        }
        if (datePicker != null) {
            datePicker.dismiss();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
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

    @Override
    public void onBackPressed() {
        goLogIn();
    }

    private void goLogIn() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
