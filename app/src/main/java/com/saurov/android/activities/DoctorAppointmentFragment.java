package com.saurov.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.database.DoctorAppointment;
import com.saurov.android.helpers.CustomAppointmentListAdapter;
import com.saurov.android.helpers.MySharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class DoctorAppointmentFragment extends Fragment {


    private ListView pastAppointmentListView;
    private ListView upcomingAppointmentListView;
    private FloatingActionButton addappointmentFAB;
    ArrayList<Long> pastAppointmentIDList;
    ArrayList<Long> upcomingAppointmentIDList;
    private Doctor doctorItem;

    public DoctorAppointmentFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long doctorId = getArguments().getLong(DoctorDetailFragment.ARG_DOCTOR_ID);

        doctorItem = Doctor.findById(Doctor.class, doctorId);

        pastAppointmentIDList = new ArrayList<>();
        upcomingAppointmentIDList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_doctor_appointment, container, false);

        pastAppointmentListView = (ListView) rootView.findViewById(R.id.pastAppointmentList);
        upcomingAppointmentListView = (ListView) rootView.findViewById(R.id.upcomingAppointmentList);

        addappointmentFAB = (FloatingActionButton) rootView.findViewById(R.id.addAppointmentFAB);


        addappointmentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), AddAppointmentActivity.class);

                i.putExtra(DoctorDetailFragment.ARG_DOCTOR_ID, getArguments().getLong(DoctorDetailFragment.ARG_DOCTOR_ID));

                startActivity(i);
            }
        });


        for (Iterator<DoctorAppointment> iter = DoctorAppointment.findAll(DoctorAppointment.class); iter.hasNext(); ) {

            DoctorAppointment appointment = iter.next();

            if (appointment.getDoctorId() == doctorItem.getId()
                    && appointment.getUserId() == MySharedPreference.getCurrentUserId(getContext())) {

                addDataToList(appointment);
            }
        }

        CustomAppointmentListAdapter adapterPast = new CustomAppointmentListAdapter(getContext(), pastAppointmentIDList);
        CustomAppointmentListAdapter adapterUpcoming = new CustomAppointmentListAdapter(getContext(), upcomingAppointmentIDList);

        pastAppointmentListView.setAdapter(adapterPast);
        upcomingAppointmentListView.setAdapter(adapterUpcoming);

        ListUtils.setDynamicHeight(pastAppointmentListView);
        ListUtils.setDynamicHeight(upcomingAppointmentListView);


        return rootView;
    }

    private void addDataToList(DoctorAppointment appointment) {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        String appDateString = appointment.getAppointmentDate() + " " + appointment.getAppointmentTime();
        Date appointmentDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.US);

        try {
            appointmentDate = sdf.parse(appDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (appointmentDate.before(currentDate)) {

            pastAppointmentIDList.add(appointment.getId());

        } else {
            upcomingAppointmentIDList.add(appointment.getId());
        }
    }




    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }


}
