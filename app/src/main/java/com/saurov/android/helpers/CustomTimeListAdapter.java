package com.saurov.android.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.saurov.android.R;

//Custom Adapter for medicine time selection list
public class CustomTimeListAdapter extends BaseAdapter {

    //Interface for passing time Data to Another Activity
    public interface ChangeButtonListener {
        void onChangeButtonClickListener(int position, TimeHolder timeHolder);
    }

    private ChangeButtonListener changeButtonListener;

    public void setChangeButtonClickListener(ChangeButtonListener listener) {
        this.changeButtonListener = listener;
    }


    private Context context;
    private int contentCount;

    public CustomTimeListAdapter(Context context, int contentCount) {
        this.context = context;
        this.contentCount = contentCount;
    }

    @Override
    public int getCount() {
        return contentCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.time_selector_layout, null);

        final TimeHolder timeHolder = new TimeHolder();

        timeHolder.changeButton = (Button) view.findViewById(R.id.changeButton);
        timeHolder.timeText = (TextView) view.findViewById(R.id.timeTextView);

        //timeHolder.timeText.setText("--:-- --");
        timeHolder.timeText.setText("Not Specified");

        timeHolder.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (changeButtonListener != null) {
                    changeButtonListener.onChangeButtonClickListener(position, timeHolder);
                }

            }
        });


        view.setTag(timeHolder);

        return view;
    }

    //Holder Class for timeData
    public class TimeHolder {
        public Button changeButton;
        public TextView timeText;
    }
}
