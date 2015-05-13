package edu.washington.ykim253.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    private ListView Topics;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
