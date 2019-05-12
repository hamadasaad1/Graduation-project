package com.ibnsaad.thedcc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibnsaad.thedcc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosisFragment extends Fragment {


    public static DiagnosisFragment diagnosisFragment;

    public DiagnosisFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (diagnosisFragment != null) {
            return diagnosisFragment;
        } else return new DiagnosisFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagnosis, container, false);
    }

}
