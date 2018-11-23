package com.example.afikshani.notificationsservice;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {

    private static final String ACTION_INIT_ALARM = "ALARM_TAG";
    private static final String ACTION_NOTIFY = "NOTIFICATION_TAG";
    private static final String CHANNEL_ID = "NotifyChannel";

    private AlarmManager alarmMgr;
    private PendingIntent recurringAlarm;
    protected int mNotificationId = 1;
    public static final long QUICK_INTERVAL =  5 * 1000;

    public NotificationService() {
        super("NotificationService");
    }


    public static void startActionInitAlarm(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_INIT_ALARM);
        context.startService(intent);
    }


    public static void startActionNotify(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_NOTIFY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_ALARM.equals(action)) {
                handleActionInitAlarm();
            } else if (ACTION_NOTIFY.equals(action)) {
                handleActionNotify();
            }
        }
    }

    // Setting up alarm manager
    private void handleActionInitAlarm() {
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent myAlarm = new Intent(this, AlarmReceiver.class);
        recurringAlarm = PendingIntent.getBroadcast(this, 0, myAlarm, 0);
        registerNotificationChannel();
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis(), QUICK_INTERVAL, recurringAlarm);
    }

    // Creating notification with randomized quote
    private void handleActionNotify() {
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.quotes_array);
        String randomQuote = generateQoute(quotes);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        Notification notification = builder.setContentTitle("MY QUOTE")
                .setContentText(randomQuote).setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_round).setPriority(NotificationCompat.PRIORITY_HIGH).build();

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(mNotificationId++, notification);

    }
    // For API 26 and higher need to follow by channels
    private void registerNotificationChannel() {
        Log.i(ACTION_NOTIFY, "notification channel for API higher than 26");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NotificationManager.class);
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH));
        }
    }

    // Choose a random quote from resources
    private String generateQoute(String[] arrayOfQuotes){
        int lengthOfArray = arrayOfQuotes.length;
        int randomIndex = (int) (Math.random() * lengthOfArray);
        String randomQuote = arrayOfQuotes[randomIndex];
        return randomQuote;
    }
}
