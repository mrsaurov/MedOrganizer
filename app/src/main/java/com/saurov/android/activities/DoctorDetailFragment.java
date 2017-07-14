package com.saurov.android.activities;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
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

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.database.Medicine;
import com.saurov.android.dialogs.DeleteDoctorDialogFragment;
import com.saurov.android.dialogs.DeleteMedicineDialogFragment;


public class DoctorDetailFragment extends Fragment {

    public static final String ARG_DOCTOR_ID = "doctor_id";

    private Doctor doctorItem;
    private ImageButton deleteButton;
    private ImageButton editButton;
    private TextView doctorName;
    private TextView doctorSpeciality;
    private TextView doctorAddress;
    private TextView doctorContactNo;
    private TextView doctorEmail;

    public DoctorDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long doctorId = getArguments().getLong(ARG_DOCTOR_ID);

        doctorItem = Doctor.findById(Doctor.class, doctorId);

        Log.d("-----", "doctorId "+doctorId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.doctor_detail, container, false);

        deleteButton = (ImageButton) rootView.findViewById(R.id.deleteImageButtonDocFragment);
        editButton = (ImageButton) rootView.findViewById(R.id.editImageButtonDocFragment);

        doctorName = (TextView) rootView.findViewById(R.id.doctorNameFragment);
        doctorSpeciality = (TextView) rootView.findViewById(R.id.doctorSpecialityFragment);
        doctorAddress = (TextView) rootView.findViewById(R.id.doctorAdressFragment);
        doctorContactNo = (TextView) rootView.findViewById(R.id.doctorContactNoFragment);
        doctorEmail = (TextView) rootView.findViewById(R.id.doctorEmailFragment);


        doctorName.setText(doctorItem.getDoctorName());
        doctorAddress.setText(doctorItem.getAddress());
        doctorContactNo.setText(doctorItem.getContactNumber());
        doctorSpeciality.setText(doctorItem.getSpeciality());
        doctorEmail.setText(doctorItem.getDoctorEmail());


        //Delete Image Button Listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a alert dialog fragment
                DialogFragment deleteDialogFragment = new DeleteDoctorDialogFragment();

                deleteDialogFragment.setArguments(getArguments());

                deleteDialogFragment.show(getFragmentManager(), "delete_doctor");
            }
        });

        //Edit Image Button Listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;


    }
}
