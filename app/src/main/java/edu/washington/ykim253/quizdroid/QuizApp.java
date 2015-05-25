package edu.washington.ykim253.quizdroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application implements TopicRepository{

    private static QuizApp instance;
    public List<Topic> topic;
    private String url;
    private int interval;


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

        File myFile = new File(getFilesDir().getAbsolutePath(), "/question.json");
        String json = null;

        if (myFile.exists()) {
            Log.i("QuizApp", "question.json written successfully");

            try {
                FileInputStream fis = openFileInput("question.json");      // sweet we found it. openFileInput() takes a string path from your data directory. no need to put 'data/' in your path parameter
                json = readJSONFile(fis);
                try {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            // Can't find data.json file in files directory. Fetch data.json in assets
            Log.i("QuizApp", "questions.json DOESN'T exist. Fetch from backup");

            try {
                InputStream inputStream = getAssets().open("data.json");
                json = readJSONFile(inputStream);
                try {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        startAlarm(getApplicationContext(), interval, true);
    }

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public void writeToFile(String data) {
        try {
            Log.i("QuizApp", "writing downloaded to file");

            File file = new File(getFilesDir().getAbsolutePath(), "question.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void startAlarm(Context context,int interval, boolean alarm) {

        Intent alarmReceiverIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (alarm) {
            int refreshInterval = interval * 60000;

            Log.i("Alarm", "setting alarm to " + refreshInterval);

            // Start the alarm manager to repeat
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshInterval, pi);
        }
        else {
            am.cancel(pi);
            pi.cancel();

            Log.i("Alarm", "Stopping alarm");
        }
    }

    public void setInterval (int interval) {
        this.interval = interval;
    }

    public void setUrl(String loc) {
        url = loc;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public List<Topic> getTopics() {
        return topic;
    }
}
