package com.saurov.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.saurov.android.activities.AddMedicationActivity;

public class SelectDaysDialogFragment extends DialogFragment {

    char[] selectedItems = new char[7];

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = '0';
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        builder.setTitle("Select Days")
                .setMultiChoiceItems(days, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedItems[which] = '1' ;
                            //selectedItems.add(which);
                        } else if (selectedItems[which]=='1') {
                            selectedItems[which] = '0';
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddMedicationActivity.dayIsChecked = new String(selectedItems);
                        Log.d("TAG", "Inside Fragment: "+ AddMedicationActivity.dayIsChecked);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

}
