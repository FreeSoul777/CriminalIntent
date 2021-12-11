package com.example.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.util.Date;

public class CrimeDialogAlertFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    public static final String EXTRA_IS_CONDITION = "com.example.criminalintent.is_condition";
    public static final String FOR_RESULT_ALERT_DIALOG = "resultForAlertDialogFragment";
    public static final String OK_DIALOG = "ok_dialog";
    public static final String CANCEL_DIALOG = "cancel_dialog";
    private static final String TAG = "ALERT DIALOG: ";

    private Button positiveButton, negativeButton;
    private TextView mCrimeTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_alert);
        Log.d(TAG, "CREATE");

        String title = (String) getArguments().getSerializable(ARG_TITLE);
        positiveButton = (Button) dialog.findViewById(R.id.positive_button);
        negativeButton = (Button) dialog.findViewById(R.id.negative_button);
        mCrimeTitle = (TextView) dialog.findViewById(R.id.dialog_name_crime);
        mCrimeTitle.setText(title);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(OK_DIALOG);
                dialog.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CANCEL_DIALOG);
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static CrimeDialogAlertFragment newInstance(String title){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);

        CrimeDialogAlertFragment fragment = new CrimeDialogAlertFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setResult(String isDelete){
        Bundle result = new Bundle();
        result.putSerializable(EXTRA_IS_CONDITION, isDelete);
        getParentFragmentManager().setFragmentResult(FOR_RESULT_ALERT_DIALOG, result);
    }

}
