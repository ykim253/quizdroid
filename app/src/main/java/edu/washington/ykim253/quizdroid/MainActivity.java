package edu.washington.ykim253.quizdroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    private ListView Topics;
    public static final int SETTINGS = 1;

    String downloadURL;
    int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        QuizApp quizApp = (QuizApp) getApplication();
        List<Topic> subjects = quizApp.getTopics();
        ArrayList<String> newSubject = new ArrayList<String>();

        //get each topic
        for(int i =0; i < subjects.size(); i++) {
            newSubject.add(subjects.get(i).getTitle() + " - " + subjects.get(i).getShort());
        }

        Topics = (ListView) findViewById(R.id.Topics);
        //added custom adapter for the icon/images to show for the listview
        MyCustomAdapter<String> items = new MyCustomAdapter<>(this, newSubject);
        Topics.setAdapter(items);

        Topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String value = String.valueOf(parent.getItemAtPosition(i));
                Intent chosenOne = new Intent(MainActivity.this, SecondActivity.class);
                chosenOne.putExtra("topic", i);
                startActivity(chosenOne);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String url = sharedPrefs.getString("location",
                    "http://tednewardsandbox.site44.com/questions.json");
            int interval = Integer.parseInt(sharedPrefs.getString("minutes", "5"));
            QuizApp.getInstance().startAlarm(interval, url);
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
