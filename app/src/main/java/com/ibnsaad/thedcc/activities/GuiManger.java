package com.ibnsaad.thedcc.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public class GuiManger {

    private Activity activity;
    private static GuiManger gui_manger = new GuiManger();
    private Fragment currFragment;
    private FragmentManager fragmentManager;

    private GuiManger() {

    }

    public static GuiManger getInstance() {
        return gui_manger;
    }


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Fragment getCurrFragment() {
        return currFragment;
    }

    public void setCurrFragment(Fragment curr_fragment) {
        currFragment = curr_fragment;


    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
