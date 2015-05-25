package edu.washington.ykim253.quizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "entered onReceive() from AlarmReceiver");


        // This is where we start our DownloadService class! aka tell our IntentService to start the download!
        Intent downloadServiceIntent = new Intent(context, DownloadService.class);
        context.startService(downloadServiceIntent);
    }
}
