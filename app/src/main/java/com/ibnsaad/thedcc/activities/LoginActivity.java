package com.ibnsaad.thedcc.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.fragments.LogInFragment;
import com.ibnsaad.thedcc.utils.Tools;

public class SignInActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initToolbar();
        GuiManger.getInstance().setActivity(this);
        GuiManger.getInstance().setFragmentManager(getSupportFragmentManager());
        GuiManger.getInstance().setCurrFragment(new LogInFragment());
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");
        toolbar.setTitle("");
        Tools.setSystemBarColorInt(this, Color.parseColor("#006ACF"));

    }
    //GUIManger Methods
    public static void update() {
        GuiManger.getInstance().getFragmentManager().beginTransaction().addToBackStack("")
                .replace(R.id.handle_Frame, GuiManger.getInstance().getCurrFragment(), null).commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        if (GuiManger.getInstance().getFragmentManager().getBackStackEntryCount() > 1) {
            GuiManger.getInstance().getFragmentManager().popBackStack();
        } else
            finish();
    }

}
