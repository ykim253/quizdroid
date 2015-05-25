package edu.washington.ykim253.quizdroid;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends IntentService {
    private DownloadManager dm;
    private long enqueue;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPrefs.getString("location",
                "http://tednewardsandbox.site44.com/questions.json");
        Log.i("DownloadService", url);

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            dm.enqueue(request);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), url + " is invalid url", Toast.LENGTH_LONG).show();
        }
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