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
    private int QuestionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_overview);

        topic = getIntent().getStringExtra("topic");

        TextView Overview = (TextView) findViewById(R.id.Overview);
        TextView NumQuestion = (TextView) findViewById(R.id.NumQuestion);
        TextView description = (TextView) findViewById(R.id.Desc);
        Button Begin = (Button) findViewById(R.id.Begin);

        Overview.setText(topic + "Overview");
        String trim = topic.split(" ")[0];

        int descID = getResources().getIdentifier(trim + "_Overview", "string", getPackageName());
        String desc = getResources().getString(descID);
        description.setText(desc);

        int numQuestionsId = getResources().getIdentifier(trim + "_Num", "integer", getPackageName());
        QuestionNumber = getResources().getInteger(numQuestionsId);
        NumQuestion.setText("There are " + QuestionNumber + " questions in this topic");

    }

    public void onBeginClick(View view) {
        Intent onToTheNextOne = new Intent(this, QuizQuestion.class);

        onToTheNextOne.putExtra("topic", topic);
        onToTheNextOne.putExtra("QuestionNumber", QuestionNumber);
        onToTheNextOne.putExtra("questions", 1);
        onToTheNextOne.putExtra("correct", 0);

        startActivity(onToTheNextOne);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}