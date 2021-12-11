package com.example.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import static com.example.criminalintent.CrimeFragment.FOR_RESULT;

public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME = "time";
    public static final String EXTRA_DATE_TIME = "com.example.criminalintent.date";
    private static final String TAG = "TIME HOUR AND MINUTE: ";

    private TimePicker mTimePicker;
    private Button positiveButton, negativeButton, nativeButton;
    private int oldMinute, oldHoure, nowHour, nowMinute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_time);
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Log.d(TAG, " " + date.toString());

        Calendar calendar = Calendar.getInstance();
        nowMinute = calendar.get(Calendar.MINUTE);
        nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTime(date);
        oldHoure = calendar.get(Calendar.HOUR_OF_DAY);
        oldMinute = calendar.get(Calendar.MINUTE);

        mTimePicker = (TimePicker) dialog.findViewById(R.id.dialog_time_picker);
        positiveButton = (Button) dialog.findViewById(R.id.positive_button);
        negativeButton = (Button) dialog.findViewById(R.id.negative_button);
        nativeButton = (Button) dialog.findViewById(R.id.native_button);
        nativeButton.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(date));
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                nowHour = hourOfDay;
                nowMinute = minute;
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, nowHour);
                calendar.set(Calendar.MINUTE, nowMinute);
                Date date = calendar.getTime();
                setResult(date);
                dialog.dismiss();
            }
        });
        nativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, oldHoure);
                calendar.set(Calendar.MINUTE, oldMinute);
                Date date = calendar.getTime();
                setResult(date);
                dialog.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(date);
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setResult(Date date){
        Bundle result = new Bundle();
        result.putSerializable(EXTRA_DATE_TIME, date);
        getParentFragmentManager().setFragmentResult(FOR_RESULT, result);
    }
}
