package com.saurov.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;


public class MedicineDetailFragment extends Fragment {

    public static final String ARG_MEDICINE_ID = "medicine_id";
    ImageButton deleteButton;
    ImageButton editButton;
    TextView daysToTakeMed;
    TextView startTime;
    TextView medicineName;

    private Medicine medicineItem;

    public MedicineDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing medicineItem To be Displayed Here

        long medicineId = getArguments().getLong(ARG_MEDICINE_ID);

        medicineItem = Medicine.findById(Medicine.class,medicineId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.medicine_detail, container, false);

        deleteButton = (ImageButton) rootView.findViewById(R.id.deleteImageButtonFragment);
        editButton = (ImageButton) rootView.findViewById(R.id.editImageButtonFragment);

        medicineName = (TextView) rootView.findViewById(R.id.medicineNameFragment);
        daysToTakeMed = (TextView) rootView.findViewById(R.id.daysToTakeMedFragment);
        startTime = (TextView) rootView.findViewById(R.id.startTimeFragment);

        //Setting start date and time text
        medicineName.setText(medicineItem.getMedicineName());
        startTime.setText(medicineItem.getStartTime());
        daysToTakeMed.setText(medicineItem.daysToTakeMedicineRetriever());

        //Delete Image Button Listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MainActivity.class);

                medicineItem.delete();

                getActivity().startActivity(i);

                getActivity().finish();
            }
        });

        //Edit Image Button Listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),EditMedicineActivity.class);

                i.putExtra(MedicineDetailFragment.ARG_MEDICINE_ID, medicineItem.getId());

                getActivity().startActivity(i);

                getActivity().finish();

                //Toast.makeText(getContext(),"Will implement later!!",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
