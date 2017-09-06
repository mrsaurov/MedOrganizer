package com.saurov.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.saurov.android.CommonConstants;
import com.saurov.android.activities.DoctorActivity;
import com.saurov.android.activities.DoctorDetailActivity;
import com.saurov.android.activities.DoctorDetailFragment;
import com.saurov.android.database.Doctor;
import com.saurov.android.database.DoctorAppointment;


public class DeleteAppointmentDialogFragment extends DialogFragment {

    private DoctorAppointment appointmentItem;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        long appointmentId = getArguments().getLong(CommonConstants.ARG_APPOINTMENT_ID);

        appointmentItem = DoctorAppointment.findById(DoctorAppointment.class, appointmentId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Are you sure want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getContext(), DoctorDetailActivity.class);

                        i.putExtra(DoctorDetailFragment.ARG_DOCTOR_ID,appointmentItem.getDoctorId());

                        i.putExtra(CommonConstants.VIEWPAGER_POSTION, 1);

                        appointmentItem.delete();

                        startActivity(i);

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
