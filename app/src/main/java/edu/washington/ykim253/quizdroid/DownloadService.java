package edu.washington.ykim253.quizdroid;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String url = intent.getExtras().getString("url");
        Log.i("DownloadService", url);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}