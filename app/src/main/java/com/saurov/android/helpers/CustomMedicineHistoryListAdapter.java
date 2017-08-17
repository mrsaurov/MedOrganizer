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

public class CustomMedicineHistoryListAdapter extends BaseAdapter {

    private Context context;
    private List<String> skipHistory;
    private String medicineName;

    public CustomMedicineHistoryListAdapter(Context context, String medicineName, List<String> skipHistory) {
        this.context = context;
        this.skipHistory = skipHistory;
        this.medicineName = medicineName;
    }

    @Override
    public int getCount() {
        return skipHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return skipHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.medicine_history_list_item, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(Integer.toString(position), color);

        holder.medicineDetail.setText(medicineName);
        holder.skipDetail.setText(skipHistory.get(position));
        holder.imageView.setImageDrawable(drawable);


        return convertView;
    }


    private class ViewHolder {
        private ImageView imageView;
        private TextView skipDetail;
        private TextView medicineDetail;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.medicineHistoryImageViewItem);
            skipDetail = (TextView) v.findViewById(R.id.medicineHistoryDetail);
            medicineDetail = (TextView) v.findViewById(R.id.medicineDetailHistoryList);
        }
    }
}
