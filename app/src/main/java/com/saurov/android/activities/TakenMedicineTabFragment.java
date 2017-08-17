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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TakenMedicineTabFragment extends Fragment {

    private List<String> medicineTakenRecords;
    private long medicineId;
    private long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicineId = getArguments().getLong(MedicineDetailFragment.ARG_MEDICINE_ID);
        userId = MySharedPreference.getCurrentUserId(getContext());
        medicineTakenRecords = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_taken_medicine, container, false);

        ListView takenListView = (ListView) rootView.findViewById(R.id.takenListView);


        for (Iterator<MedicineHistory> iter = MedicineHistory.findAll(MedicineHistory.class); iter.hasNext();) {

            MedicineHistory medicineHistory = iter.next();

            if (medicineHistory.getTakenRecords() != null && !medicineHistory.getTakenRecords().trim().isEmpty()) {

                if (medicineHistory.getUserId() == userId && medicineHistory.getMedicineId() == medicineId) {

                    medicineTakenRecords.add(medicineHistory.getTakenRecords());

                }

            }
        }

        CustomMedicineHistoryListAdapter adapter = new CustomMedicineHistoryListAdapter(getContext(), medicineTakenRecords);

        takenListView.setAdapter(adapter);

        return rootView;
    }
}
