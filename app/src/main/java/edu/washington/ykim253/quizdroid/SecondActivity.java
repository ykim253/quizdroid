package edu.washington.ykim253.quizdroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class SecondActivity extends ActionBarActivity {

    public static final int SETTINGS = 1;
    private String topic;
    private Topic topics;
    private int QuestionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        int i = getIntent().getIntExtra("topic", -1);
        topics = QuizApp.getInstance().getTopics().get(i);
        topic = topics.getTitle();
        QuestionNumber = topics.getQuestions().size();
        overviewTime();
    }

    public void overviewTime(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle topicBundle = new Bundle();
        topicBundle.putString("topic", topic);
        topicBundle.putInt("QuestionNumber", QuestionNumber);
        FrOverview overview = new FrOverview(topics);
        overview.setArguments(topicBundle);

        ft.replace(R.id.secondmain, overview);
        ft.commit();
    }

    public void questionTime(Bundle bundle){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FrQuestion question = new FrQuestion(topics);
        question.setArguments(bundle);

        ft.replace(R.id.secondmain, question);
        ft.commit();
    }

    public void answerTime(Bundle bundle){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FrAnswer answers = new FrAnswer();
        answers.setArguments(bundle);

        ft.replace(R.id.secondmain, answers);
        ft.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String url = sharedPrefs.getString("location",
                    "http://tednewardsandbox.site44.com/questions.json");
            int interval = Integer.parseInt(sharedPrefs.getString("minutes", "5"));
            QuizApp.getInstance().setInterval(interval);
            QuizApp.getInstance().setUrl(url);
            QuizApp.getInstance().startAlarm(SecondActivity.this, interval, true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void openPreferences() {
        Intent prefs = new Intent(getApplicationContext(), Preferences.class);
        startActivityForResult(prefs, SETTINGS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                openPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}