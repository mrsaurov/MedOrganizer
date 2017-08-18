package com.saurov.android.helpers;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.saurov.android.database.Medicine;
import com.saurov.android.database.MedicineHistory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NotificationActionHandler extends IntentService{

    public static final String ACTION_SNOOZE = "com.saurov.android.androidapp.SNOOZE";
    public static final String ACTION_SKIP = "com.saurov.android.androidapp.SKIP";
    public static final String ACTION_TAKE = "com.saurov.android.androidapp.TAKE";

    public static final String EXTRA_NOTIFICATION_ID = "com.saurov.android.androidapp.EXTRA_NOTIFICATION_ID";

    private NotificationManager notificationManager;

    public NotificationActionHandler() {
        super("NotificationActionHandler");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {

            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            final String action = intent.getAction();

            final int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);

            if (action.equals(ACTION_SKIP)) {
                handleActionSkip(notificationId);
            } else if (action.equals(ACTION_SNOOZE)) {
                handleActionSnooze(notificationId);
            } else if (action.equals(ACTION_TAKE)) {
                handleActionTake(notificationId);
            }
        }
    }

    private void handleActionTake(int notificationId) {
        Log.d("------", "inside handleActionTake");

        if (NotificationIntentService.mp.isPlaying()) {

            NotificationIntentService.mp.stop();
            NotificationIntentService.mp.release();
        }

        notificationManager.cancel(notificationId);


        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, dd-MM-yyyy", Locale.US);
        Calendar cal = Calendar.getInstance();

        for(int i=0; i<NotificationIntentService.medicineIdForNotification.size();i++){

            long medicineId = NotificationIntentService.medicineIdForNotification.get(i);

            Medicine medicineitem = Medicine.findById(Medicine.class, medicineId);

            MedicineHistory medicineHistory = new MedicineHistory(this, medicineId);

            medicineHistory.addDataToTakenRecord(medicineitem.getMedicineName()+" taken at "+ sdf.format(cal.getTime()));

            medicineHistory.save();
        }

    }

    private void handleActionSnooze(int notificationId) {
        Log.d("------", "inside handleActionSnooze");
        notificationManager.cancel(notificationId);
    }

    private void handleActionSkip(int notificationId) {
        Log.d("------", "inside handleActionSkip");

        if (NotificationIntentService.mp.isPlaying()) {

            NotificationIntentService.mp.stop();
            NotificationIntentService.mp.release();
        }


        notificationManager.cancel(notificationId);

        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, dd-MM-yyyy", Locale.US);
        Calendar cal = Calendar.getInstance();

        for(int i=0; i<NotificationIntentService.medicineIdForNotification.size();i++){

            long medicineId = NotificationIntentService.medicineIdForNotification.get(i);

            Medicine medicineitem = Medicine.findById(Medicine.class, medicineId);

            MedicineHistory medicineHistory = new MedicineHistory(this, medicineId);

            medicineHistory.addDataToSkippedRecord(medicineitem.getMedicineName()+" skipped at "+ sdf.format(cal.getTime()));

            medicineHistory.save();
        }
    }
}
