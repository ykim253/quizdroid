package edu.washington.ykim253.quizdroid;

/**
 * Created by pew pew the coon on 4/28/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TopicOverview extends ActionBarActivity {

    private String topic;
    private int numQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_overview);


        TextView Overview = (TextView) findViewById(R.id.Overview);
        TextView NumQuestion = (TextView) findViewById(R.id.NumQuestion);
        Button Start = (Button) findViewById(R.id.Start);

        topic = getIntent().getStringExtra("topic");

        Overview.setText(topic);

        NumQuestion.setText("There are 3 questions in this topic");
    }

    public void onBeginClick(View view) {
        Intent QuizQuestions = new Intent(this, QuizQuestion.class);

        QuizQuestions.putExtra("topic", topic);
        QuizQuestions.putExtra("questions", 1);
        QuizQuestions.putExtra("correct", 0);

        startActivity(QuizQuestions);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}