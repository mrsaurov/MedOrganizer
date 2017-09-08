package com.saurov.android.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.dialogs.DeleteDoctorDialogFragment;

import java.util.List;


public class DoctorDetailFragment extends Fragment {

    public static final String ARG_DOCTOR_ID = "doctor_id";

    private Doctor doctorItem;
    private ImageButton deleteButton;
    private ImageButton editButton;
    private ImageButton mailButton;
    private ImageButton callButton;
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

        Log.d("-----", "doctorId " + doctorId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.doctor_detail, container, false);

        deleteButton = (ImageButton) rootView.findViewById(R.id.deleteImageButtonDocFragment);
        editButton = (ImageButton) rootView.findViewById(R.id.editImageButtonDocFragment);
        callButton = (ImageButton) rootView.findViewById(R.id.callButton);
        mailButton = (ImageButton) rootView.findViewById(R.id.mailButton);

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

                Intent i = new Intent(getContext(), EditDoctorActivity.class);

                i.putExtra(ARG_DOCTOR_ID, doctorItem.getId());

                getActivity().startActivity(i);

                getActivity().finish();
            }
        });


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contactNumber = doctorItem.getContactNumber();

                if (contactNumber == null || contactNumber.trim().isEmpty()) {

                    Toast.makeText(getContext(), "No Contact Number Specified", Toast.LENGTH_SHORT).show();
                } else {

                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+contactNumber));
                    startActivity(i);
                }
            }
        });

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mailAddress = doctorItem.getDoctorEmail();

                if (mailAddress == null || mailAddress.trim().isEmpty()) {

                    Toast.makeText(getContext(), "No Email Adress Specified", Toast.LENGTH_SHORT).show();
                } else {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mailAddress});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Mail from your patient");
                    i.setType("text/plain");
                    final PackageManager pm = getContext().getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(i, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches)
                        if (info.activityInfo.packageName.endsWith(".gm") ||
                                info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                    if (best != null)
                        i.setClassName(best.activityInfo.packageName, best.activityInfo.name);

                    startActivity(i);
                }
            }
        });

        return rootView;
    }
}
