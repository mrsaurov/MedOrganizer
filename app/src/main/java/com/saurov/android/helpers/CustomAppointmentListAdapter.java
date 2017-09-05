package com.saurov.android.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.database.DoctorAppointment;

import java.util.ArrayList;


public class CustomAppointmentListAdapter extends BaseAdapter {

    private ArrayList<Long> appointmentListId;
    private Context context;

    public CustomAppointmentListAdapter(Context context, ArrayList<Long> appointmentListId) {
        this.appointmentListId = appointmentListId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appointmentListId.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentListId.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.appointment_list_item, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        DoctorAppointment appointment =
                DoctorAppointment.findById(DoctorAppointment.class, appointmentListId.get(position));

        String date = appointment.getAppointmentTime()+" "+ appointment.getAppointmentDate();
        String details = Doctor.findById(Doctor.class, appointment.getDoctorId()).getDoctorName()+"-"+appointment.getAppointmentTitle();

        holder.appointmentDetails.setText(details);
        holder.appointmentDate.setText(date);
        //holder.imageView.setImageDrawable(R.drawable.account_header_image);
        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView appointmentDetails;
        private TextView appointmentDate;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.medicineImageViewItem);
            appointmentDetails = (TextView) v.findViewById(R.id.appointmentDetailListItem);
            appointmentDate = (TextView) v.findViewById(R.id.appointmentDateListItem);
        }
    }
}
