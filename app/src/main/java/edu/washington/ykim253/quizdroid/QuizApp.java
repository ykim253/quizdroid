package edu.washington.ykim253.quizdroid;

import android.app.Application;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application implements TopicRepository{

    private static QuizApp instance; //made QuizApp singleton
    public List<Topic> topic;

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

    @Override
    public List<Topic> getTopics() {
        return topic;
    }
}
