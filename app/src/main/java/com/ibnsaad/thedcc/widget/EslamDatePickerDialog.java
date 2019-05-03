package com.ibnsaad.thedcc.widget;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ibnsaad.thedcc.utils.Tools;

import java.util.Calendar;

public class EslamDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

        return datepickerdialog;
    }

    public EditText getTextView() {
        return textView;
    }

    public void setTextView(EditText textView) {
        this.textView = textView;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long date_ship_millis = calendar.getTimeInMillis();
        textView.setText(Tools.getFormattedDateSimple(date_ship_millis));

        //p  textView.setText(day + ":" + (month + 1) + ":" + year);

    }
}