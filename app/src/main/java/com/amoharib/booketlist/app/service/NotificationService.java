package com.amoharib.booketlist.app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.ext.Constants;
import com.amoharib.booketlist.ui.MainActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerService;

import static com.amoharib.booketlist.ext.Constants.CHANNEL_ID;

public class NotificationService extends DaggerService {

    private static final String TAG = "NotificationService";

    @Inject
    DataRepositoryLayer model;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        createNotification();
        syncBooks();

        return super.onStartCommand(intent, flags, startId);
    }

    private void syncBooks() {
        if (model.isLoggedIn()) {
            model.syncBooks();
        }
    }

    private void createNotification() {
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.putExtra(Constants.INTENT_FROM_NOTIFICATION_KEY, true);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, startIntent, 0);
        createNotificationChannel();

        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        model.checkIfThereIsALongTimeUnreadBooks(found -> {
            if (found != null) {
                notificationManager.notify(0, notification.build());
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
