package com.example.criminalintent;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.criminalintent.CrimeFragment.DIALOG_TIME;
import static com.example.criminalintent.CrimeFragment.FOR_RESULT;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    private static final String TAG = "DATE HOUR AND MINUTE: ";

    private DatePicker mDatePicker;
    private Button positiveButton, negativeButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_date);
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Log.d(TAG, " " + date.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker) dialog.findViewById(R.id.dialog_date_picker);
        positiveButton = (Button) dialog.findViewById(R.id.positive_button);
        negativeButton = (Button) dialog.findViewById(R.id.negative_button);
        mDatePicker.init(year, month, day, null);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                Date date = calendar.getTime();
                Log.d(TAG, " " + date.toString());

                dialog.dismiss();

                TimePickerFragment dialog = TimePickerFragment.newInstance(date);
                dialog.show(getParentFragmentManager(), DIALOG_TIME);
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
