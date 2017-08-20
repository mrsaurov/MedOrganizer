package com.saurov.android.helpers;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.saurov.android.R;
import com.saurov.android.activities.AddMedicationActivity;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.database.Medicine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class NotificationIntentService extends IntentService {

    public static final String NOTIFICATION_EXTRA = "notification_extra_array";
    private static final int NOTIFICATION_ID = 12883;
    public static ArrayList<String> medicineIdForNotification;

    public static MediaPlayer mp;

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            medicineIdForNotification = new ArrayList<>();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AddMedicationActivity.TIME_FORMAT, Locale.US);

            StringBuilder stringBuilder;

            stringBuilder = new StringBuilder("");

            String currentTime = simpleDateFormat.format(System.currentTimeMillis());

            Log.d("--------", currentTime);

            for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {

                Medicine medicineItem = iter.next();

                if (isAfterStartDate(medicineItem.getStartDate()) && isSelectedDayForMed(medicineItem.getDayChoice())) {
                    switch (medicineItem.getReminderTimes()) {

                        case 1:
                            if (currentTime.equals(medicineItem.getTimeOneToTakeMed())) {

                                stringBuilder.append(medicineItem.getMedicineName() + " ");

                                medicineIdForNotification.add(Long.toString(medicineItem.getId()));
                            }
                            break;
                        case 2:
                            if (currentTime.equals(medicineItem.getTimeOneToTakeMed()) ||
                                    currentTime.equals(medicineItem.getTimeTwoToTakeMed())
                                    ) {

                                stringBuilder.append(medicineItem.getMedicineName() + " ");

                                medicineIdForNotification.add(Long.toString(medicineItem.getId()));

                            }
                            break;
                        case 3:
                            if (currentTime.equals(medicineItem.getTimeOneToTakeMed()) ||
                                    currentTime.equals(medicineItem.getTimeTwoToTakeMed()) ||
                                    currentTime.equals(medicineItem.getTimeTheeToTakeMed())
                                    ) {

                                stringBuilder.append(medicineItem.getMedicineName() + " ");

                                medicineIdForNotification.add(Long.toString(medicineItem.getId()));

                            }
                            break;
                    }
                }
            }


            if (!stringBuilder.toString().trim().isEmpty()) {
                showNotification(stringBuilder.toString());

                //////////Setting up alarm sound

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        mp = MediaPlayer.create(NotificationIntentService.this, alert);
                        mp.start();

                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                            int count = 0;

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Log.d("TAG", "Count: " + count);

                                if (count < 3) {
                                    mp.start();
                                    count++;
                                } else {
                                    mp.stop();
                                    //mp.release();
                                }
                            }
                        });

                    }
                }).start();

                ///////////////
            }

        }
    }

    private boolean isAfterStartDate(String startDate) {

        if (startDate.trim().isEmpty() || startDate == null) {
            return true;
        }

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Date date = new Date();

        Date currentDate = calendar.getTime();

        try {
            date = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentDate.after(date)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSelectedDayForMed(String dayChoice) {

        if (dayChoice.equals("1111111")) {
            return true;
        }

        if (dayChoice.equals("")) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String currentDay = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());

        switch (currentDay) {

            case "Sat":
                if (dayChoice.charAt(0) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Sun":
                if (dayChoice.charAt(1) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Mon":
                if (dayChoice.charAt(2) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Tue":
                if (dayChoice.charAt(3) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Wed":
                if (dayChoice.charAt(4) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Thu":
                if (dayChoice.charAt(5) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
            case "Fri":
                if (dayChoice.charAt(6) == '1') {
                    return true;
                } else {
                    return false;
                }
                //break;
        }

        return true;
    }

    private void showNotification(String medicineName) {

        Log.d("-------", "inside showNotification");

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent skipIntent = new Intent(this, NotificationActionHandler.class);
        skipIntent.putStringArrayListExtra(NOTIFICATION_EXTRA, medicineIdForNotification);
        Intent snoozeIntent = new Intent(this, NotificationActionHandler.class);
        Intent takeIntent = new Intent(this, NotificationActionHandler.class);
        takeIntent.putStringArrayListExtra(NOTIFICATION_EXTRA, medicineIdForNotification);

        skipIntent.setAction(NotificationActionHandler.ACTION_SKIP);
        skipIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent skipPendingIntent = PendingIntent.getService(this, 0, skipIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        snoozeIntent.setAction(NotificationActionHandler.ACTION_SNOOZE);
//        snoozeIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
//        PendingIntent snoozePendingIntent = PendingIntent.getService(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        takeIntent.setAction(NotificationActionHandler.ACTION_TAKE);
        takeIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent takePendingIntent = PendingIntent.getService(this, 0, takeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this)
                .setTicker("New Medicine Notification")
                .setContentTitle(medicineName)
                .setContentText("Take your medicine")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .addAction(R.drawable.ic_done_black_24dp, "TAKE", takePendingIntent)
                //.addAction(R.drawable.ic_stat_ic_stat_snooze, "SNOOZE", snoozePendingIntent)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "SKIP", skipPendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(contentPendingIntent)
                .setStyle(new Notification.BigTextStyle().bigText("Take your medicines"))
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
