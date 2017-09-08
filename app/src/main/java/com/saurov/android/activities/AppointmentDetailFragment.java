package com.saurov.android.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.saurov.android.CommonConstants;
import com.saurov.android.R;
import com.saurov.android.database.DoctorAppointment;
import com.saurov.android.dialogs.DeleteAppointmentDialogFragment;

import static com.mikepenz.iconics.Iconics.TAG;

public class AppointmentDetailFragment extends Fragment {

    private DoctorAppointment appointmentItem;

    private TextView appointmentName;
    private TextView appointmentLocation;
    private TextView appointmentNote;
    private TextView appointmentTime;
    private TextView appointmentDate;

    private ImageButton deleteButton;
    private ImageButton editButton;

    public AppointmentDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "onCreate: ");

        long appointmentId = getArguments().getLong(CommonConstants.ARG_APPOINTMENT_ID);

        Log.d(TAG, "onCreate: "+ appointmentId);

        appointmentItem = DoctorAppointment.findById(DoctorAppointment.class, appointmentId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointment_detail, container, false);

        appointmentName = (TextView) rootView.findViewById(R.id.appointNameFragmentTextView);
        appointmentLocation = (TextView) rootView.findViewById(R.id.appointmentLocationFragmentTextView);
        appointmentNote = (TextView) rootView.findViewById(R.id.appointmentNoteFragmentTextView);
        appointmentTime = (TextView) rootView.findViewById(R.id.appointmentTimeFragmentTextView);
        appointmentDate = (TextView) rootView.findViewById(R.id.appointmentDateFragmentTextView);

        deleteButton = (ImageButton) rootView.findViewById(R.id.deleteImageButtonAppointmentFragment);
        editButton = (ImageButton) rootView.findViewById(R.id.editImageButtonAppointmentFragment);


        appointmentName.setText(appointmentItem.getAppointmentTitle());
        appointmentLocation.setText(appointmentItem.getAppointmentLocation());
        appointmentNote.setText(appointmentItem.getAppointmentNotes());
        appointmentTime.setText(appointmentItem.getAppointmentTime());
        appointmentDate.setText(appointmentItem.getAppointmentDate());


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment deleteDialogFragment = new DeleteAppointmentDialogFragment();

                deleteDialogFragment.setArguments(getArguments());

                deleteDialogFragment.show(getFragmentManager(), "delete_appointment");


            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), EditAppointmentActivity.class);

                i.putExtra(CommonConstants.ARG_APPOINTMENT_ID, appointmentItem.getId());

                startActivity(i);
            }
        });

        return rootView;
    }

}
