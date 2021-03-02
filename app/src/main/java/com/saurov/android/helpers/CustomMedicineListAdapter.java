package com.saurov.android.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.saurov.android.R;
import com.saurov.android.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

//Custom Adapter for medicineList with TextDrawable
public class CustomMedicineListAdapter extends BaseAdapter {

    private Context context;
    private List<String> medicineList;

    public CustomMedicineListAdapter(Context context, List<String> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }

    @Override
    public int getCount() {
        return medicineList.size();
    }

    @Override
    public Object getItem(int position) {
        return medicineList.get(position);
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

        String medicineName = (String) getItem(position);

        char firstLetter = medicineName.charAt(0);

        if (Character.isLowerCase(firstLetter)) {

            firstLetter = Character.toUpperCase(firstLetter);
        }

        holder.medicineName.setText(medicineName);

        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(Character.toString(firstLetter), color);

//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .fontSize(Math.round(convertDpToPixel(26, context)))
//                .height(Math.round(convertDpToPixel(52, context)))
//                .width(Math.round(convertDpToPixel(52, context)))
//                .endConfig()
//                .buildRound("Mechanical", color);



        holder.imageView.setImageDrawable(drawable);
        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView medicineName;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.medicineImageViewItem);
            medicineName = (TextView) v.findViewById(R.id.medicineNameListItem);
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
