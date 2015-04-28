package edu.washington.ykim253.quizdroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizAnswer extends ActionBarActivity {

    private String topic;
    private int QuestionNumber;
    private int questions;
    private int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_answer);

        topic = getIntent().getStringExtra("topic");
        QuestionNumber = getIntent().getIntExtra("QuestionNumber", -1);
        questions = getIntent().getIntExtra("questions", -1);
        correct = getIntent().getIntExtra("correct", -1);
        String correctTxt = getIntent().getStringExtra("correctOne");
        String chosenTxt = getIntent().getStringExtra("chosenOne");



        String correctAnswer = "Correct Answer: " + correctTxt;
        String originalAnswer = "Selected Answer: " + chosenTxt;

        TextView OGAnswer = (TextView) findViewById(R.id.OGAnswer);
        TextView CorrectAnswer = (TextView) findViewById(R.id.CorrectAnswer);
        TextView howMany = (TextView) findViewById(R.id.howMany);
        Button NextButton = (Button) findViewById(R.id.NextButton);

        if(questions == QuestionNumber) {
            NextButton.setText("Finish");
        }

        CorrectAnswer.setText(correctAnswer);
        OGAnswer.setText(originalAnswer);
        howMany.setText("You have " + correct + " out of " + QuestionNumber + " possible.");
    }

    public void onNextClick(View view) {
        if(questions < 3) {
            Intent backToIt = new Intent(this, QuizQuestion.class);

            backToIt.putExtra("topic", topic);
            backToIt.putExtra("QuestionNumber", QuestionNumber);
            backToIt.putExtra("questions", questions + 1);
            backToIt.putExtra("correct", correct);
            startActivity(backToIt);
            finish();
        }
        else {
            Intent Restart = new Intent(QuizAnswer.this, MainActivity.class);
            startActivity(Restart);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}