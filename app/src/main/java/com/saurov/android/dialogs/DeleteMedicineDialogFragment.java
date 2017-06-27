package com.saurov.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.saurov.android.activities.MainActivity;
import com.saurov.android.activities.MedicineDetailFragment;
import com.saurov.android.database.Medicine;

//Dialog Fragment For Deleting Medicine
public class DeleteMedicineDialogFragment extends DialogFragment {

    private Medicine medicineItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        long medicineId = getArguments().getLong(MedicineDetailFragment.ARG_MEDICINE_ID);

        medicineItem = Medicine.findById(Medicine.class, medicineId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Are you sure want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getContext(), MainActivity.class);

                        medicineItem.delete();

                        getActivity().startActivity(i);

                        getActivity().finish();

                        //Toast.makeText(getContext(), "Medicine Deleted", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
