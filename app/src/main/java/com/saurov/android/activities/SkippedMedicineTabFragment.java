package com.saurov.android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.database.MedicineHistory;
import com.saurov.android.helpers.CustomMedicineHistoryListAdapter;
import com.saurov.android.helpers.MySharedPreference;

import java.util.Iterator;
import java.util.List;


public class SkippedMedicineTabFragment extends Fragment {


    private List<String> medicineSkipRecords;
    private long medicineId;
    private long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicineId = getArguments().getLong(MedicineDetailFragment.ARG_MEDICINE_ID);
        userId = MySharedPreference.getCurrentUserId(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_skipped_medicine, container, false);

        ListView skippedListView = (ListView) rootView.findViewById(R.id.skippedListView);


        for (Iterator<MedicineHistory> iter = MedicineHistory.findAll(MedicineHistory.class); iter.hasNext();) {

            MedicineHistory medicineHistory = iter.next();

            if (medicineHistory.getSkippedRecords() != null && !medicineHistory.getSkippedRecords().trim().isEmpty()) {

                if (medicineHistory.getUserId() == userId && medicineHistory.getMedicineId() == medicineId) {

                    medicineSkipRecords.add(medicineHistory.getSkippedRecords());

                }

            }
        }

        CustomMedicineHistoryListAdapter adapter = new CustomMedicineHistoryListAdapter(getContext(),
                Medicine.findById(Medicine.class, medicineId).getMedicineName(), medicineSkipRecords);

        skippedListView.setAdapter(adapter);

        return rootView;
    }
}
