package com.saurov.android.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.saurov.android.R;

import java.util.List;


public class CustomDoctorListAdapter extends BaseAdapter {

    private Context context;
    private List<String> doctorList;

    public CustomDoctorListAdapter(Context context, List<String> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
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

            convertView = layoutInflater.inflate(R.layout.medicine_list_item, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        String doctorName = (String) getItem(position);

        char firstLetter = doctorName.charAt(0);

        if(Character.isLowerCase(firstLetter)){

            firstLetter = Character.toUpperCase(firstLetter);
        }

        holder.doctorName.setText(doctorName);

        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(Character.toString(firstLetter), color);

        holder.imageView.setImageDrawable(drawable);

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView doctorName;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.medicineImageViewItem);
            doctorName = (TextView) v.findViewById(R.id.medicineNameListItem);
        }
    }
}
