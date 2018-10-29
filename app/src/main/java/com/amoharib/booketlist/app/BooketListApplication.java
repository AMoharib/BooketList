package com.amoharib.booketlist.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.amoharib.booketlist.app.di.ApplicationComponent;
import com.amoharib.booketlist.app.di.DaggerApplicationComponent;
import com.amoharib.booketlist.app.service.NotificationService;
import com.amoharib.booketlist.ext.Constants;

import java.util.Calendar;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BooketListApplication extends DaggerApplication {

    private static final String TAG = "BooketListApplication";

    ApplicationComponent applicationComponent;

    public static BooketListApplication get(Activity activity) {
        return (BooketListApplication) activity.getApplication();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startReminderService();
    }

    private void startReminderService() {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.SHARED_FIRST_TIME, true)) {
            Intent intent = new Intent(this, NotificationService.class);
            AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.SHARED_FIRST_TIME, false);
            editor.apply();
        }


    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        applicationComponent = DaggerApplicationComponent.builder().application(this).build();
        return applicationComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
