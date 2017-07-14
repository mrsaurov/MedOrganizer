package com.saurov.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.saurov.android.activities.DoctorActivity;
import com.saurov.android.activities.DoctorDetailFragment;
import com.saurov.android.database.Doctor;


public class DeleteDoctorDialogFragment extends DialogFragment {

    private Doctor doctorItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        long doctorId = getArguments().getLong(DoctorDetailFragment.ARG_DOCTOR_ID);

        doctorItem = Doctor.findById(Doctor.class, doctorId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Are you sure want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getContext(), DoctorActivity.class);

                        doctorItem.delete();

                        getActivity().startActivity(i);

                        getActivity().finish();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}
