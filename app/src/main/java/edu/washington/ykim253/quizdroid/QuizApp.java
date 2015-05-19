package edu.washington.ykim253.quizdroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application implements TopicRepository{

    private static QuizApp instance;
    public List<Topic> topic;
    String url;
    int interval;
    private AlarmManager am;
    private PendingIntent pi;

    public QuizApp()
    {
        if (instance == null)
            instance = this;
        else {
            Log.e("QuizApp", "You made more than one!");
            throw new RuntimeException("Ran more than one");
        }
        topic = new ArrayList<Topic>();
    }

    public static QuizApp getInstance() {
        return instance;
    }

    @Override public void onCreate(){
        super.onCreate();
        Log.i("QuizApp", "created");
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        url = sharedPrefs.getString("location",
                "http://tednewardsandbox.site44.com/questions.json");
        interval = Integer.parseInt(sharedPrefs.getString("minutes", "5"));
        am = (AlarmManager) getSystemService(ALARM_SERVICE);

        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Intent downloadServiceIntent = new Intent(context, DownloadService.class);
                downloadServiceIntent.putExtra("url", url);
                context.startService(downloadServiceIntent);

                Toast.makeText(QuizApp.this, url, Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(alarmReceiver, new IntentFilter("edu.washington.ykim253.checkJSON"));
        Intent intent = new Intent();
        intent.setAction("edu.washington.ykim253.checkJSON");
        pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        startAlarm(interval, url);

        String json = null;
        try {
            InputStream inputStream = getAssets().open("questions.json");
            json = readJSONFile(inputStream);

            JSONArray jsonTopics = new JSONArray(json);
            Log.i("QuizApp", "now loading topics");
            for (int i=0; i<jsonTopics.length(); i++)
            {
                JSONObject topics = jsonTopics.getJSONObject(i);

                JSONArray qs = topics.getJSONArray("questions");
                List<Question> questions = new ArrayList<Question>();
                for (int j=0; j< qs.length(); j++)
                {
                    Log.d("QuizApp", "Adding Questions");
                    JSONObject q = qs.getJSONObject(j);
                    questions.add(new Question(q.getString("text"),
                            q.getJSONArray("answers").getString(0),
                            q.getJSONArray("answers").getString(1),
                            q.getJSONArray("answers").getString(2),
                            q.getJSONArray("answers").getString(3),
                            q.getInt("answer")));

                }
                String title = topics.getString("title");
                String desc = topics.getString("desc");
                topic.add(new Topic(title, desc, desc, questions));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public void startAlarm(int interval, String url) {
        this.interval = interval * 60000; //converts milliseconds to minutes
        this.url = url;

        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), this.interval,
                pi);
    }



    @Override
    public List<Topic> getTopics() {
        return topic;
    }
}
