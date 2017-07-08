package com.saurov.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.saurov.android.activities.AddMedicationActivity;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.database.Medicine;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 123;

    public NotificationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AddMedicationActivity.TIME_FORMAT, Locale.US);

                StringBuilder stringBuilder;

                while (true)
                {
                    //int randomId = new Random().nextInt(200);

                    stringBuilder = new StringBuilder("");

                    String currentTime = simpleDateFormat.format(System.currentTimeMillis());

                    Log.d("--------", currentTime);

                    for(Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext();){

                        Medicine medicineItem = iter.next();

                        switch (medicineItem.getReminderTimes()){

                            case 1:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

//                                    showNotification(medicineItem.getMedicineName(), randomId);
                                }
                                break;
                            case 2:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())
                                        ){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

//                                    showNotification(medicineItem.getMedicineName(), randomId );
                                }
                                break;
                            case 3:
                                if(currentTime.equals(medicineItem.getTimeOneToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTwoToTakeMed())||
                                        currentTime.equals(medicineItem.getTimeTheeToTakeMed())
                                        ){

                                    stringBuilder.append(medicineItem.getMedicineName()+" ");

//                                    showNotification(medicineItem.getMedicineName(), randomId );
                                }
                                break;
                        }
                    }

                    if(stringBuilder.length()!=0){
                        showNotification(stringBuilder.toString());
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

        Intent i = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setTicker("New Medicine Notification")
                .setContentTitle(medicineName)
                .setContentText("Take your medicine")
                .setSmallIcon(R.mipmap.appicon)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
