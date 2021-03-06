package com.saurov.android.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.saurov.android.R;
import com.saurov.android.activities.AddMedicationActivity;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.database.Medicine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Handler;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 12883;

    private static boolean isNotificationShowing = false;


    public NotificationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        android.os.Handler handler = new android.os.Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AddMedicationActivity.TIME_FORMAT, Locale.US);
//
//                StringBuilder stringBuilder;
//
//                //while (true)
//                //{
//                    stringBuilder = new StringBuilder("");
//
//                    String currentTime = simpleDateFormat.format(System.currentTimeMillis());
//
//                    Log.d("--------", currentTime);
//
//                    for(Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext();){
//
//                        Medicine medicineItem = iter.next();
//
//                        switch (medicineItem.getReminderTimes()){
//
//                            case 1:
//                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())){
//
//                                    stringBuilder.append(medicineItem.getMedicineName()+" ");
//
//                                    medicineIdForNotification.add(medicineItem.getId());
//                                }
//                                break;
//                            case 2:
//                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
//                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())
//                                        ){
//
//                                    stringBuilder.append(medicineItem.getMedicineName()+" ");
//
//                                    medicineIdForNotification.add(medicineItem.getId());
//
//                                }
//                                break;
//                            case 3:
//                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
//                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())||
//                                        currentTime.equals(medicineItem.getTimeTheeToTakeMed())
//                                        ){
//
//                                    stringBuilder.append(medicineItem.getMedicineName()+" ");
//
//                                    medicineIdForNotification.add(medicineItem.getId());
//
//                                }
//                                break;
//                        }
//                    }
//
//                    if(stringBuilder.length()!=0 && !isNotificationShowing){
//                        showNotification(stringBuilder.toString());
//                        isNotificationShowing = true;
//
//                    }
//
//                    long futuretime = System.currentTimeMillis()+60000;
//
//                    while(System.currentTimeMillis()<futuretime){
//
//                        synchronized (this){
//                            try {
//                                wait(futuretime - System.currentTimeMillis());
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    isNotificationShowing = false;
//                }
//
//
//        }, 60000);


        new Thread(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AddMedicationActivity.TIME_FORMAT, Locale.US);

                StringBuilder stringBuilder;

                while (true)
                {
                    stringBuilder = new StringBuilder("");

                    String currentTime = simpleDateFormat.format(System.currentTimeMillis());

                    Log.d("--------", currentTime);

                    for(Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext();){

                        Medicine medicineItem = iter.next();

                        switch (medicineItem.getReminderTimes()){

                            case 1:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

                                    //medicineIdForNotification.add(medicineItem.getId());
                                }
                                break;
                            case 2:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())
                                        ){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

                                    //medicineIdForNotification.add(medicineItem.getId());

                                }
                                break;
                            case 3:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTheeToTakeMed())
                                        ){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

                                    //medicineIdForNotification.add(medicineItem.getId());

                                }
                                break;
                        }
                    }

                    if(stringBuilder.length()!=0 && !isNotificationShowing){
                        showNotification(stringBuilder.toString());
                        isNotificationShowing = true;

                    }

                    long futuretime = System.currentTimeMillis()+60000;

                    while(System.currentTimeMillis()<futuretime){

                        synchronized (this){
                            try {
                                wait(futuretime - System.currentTimeMillis());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    isNotificationShowing = false;
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showNotification(String medicineName){

        Log.d("-------", "inside showNotification");

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent skipIntent = new Intent(this, NotificationActionHandler.class);
        Intent snoozeIntent = new Intent(this, NotificationActionHandler.class);
        Intent takeIntent = new Intent(this, NotificationActionHandler.class);

        skipIntent.setAction(NotificationActionHandler.ACTION_SKIP);
        skipIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent skipPendingIntent = PendingIntent.getService(this, 0, skipIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        snoozeIntent.setAction(NotificationActionHandler.ACTION_SNOOZE);
        snoozeIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent snoozePendingIntent = PendingIntent.getService(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        takeIntent.setAction(NotificationActionHandler.ACTION_TAKE);
        takeIntent.putExtra(NotificationActionHandler.EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent takePendingIntent = PendingIntent.getService(this, 0, takeIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        Notification notification = new Notification.Builder(this)
                .setTicker("New Medicine Notification")
                .setContentTitle(medicineName)
                .setContentText("Take your medicine")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .addAction(R.drawable.ic_done_black_24dp, "TAKE", takePendingIntent)
                .addAction(R.drawable.ic_stat_ic_stat_snooze, "SNOOZE", snoozePendingIntent)
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
