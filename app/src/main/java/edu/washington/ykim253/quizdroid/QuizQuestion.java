package edu.washington.ykim253.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizQuestion extends ActionBarActivity {

    private String topic;
    private int QuestionNumber;
    private int questions;
    private int correct;
    RadioGroup choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_questions);

        choices = (RadioGroup) findViewById(R.id.choices);
        topic = getIntent().getStringExtra("topic");
        QuestionNumber = getIntent().getIntExtra("QuestionNumber", -1);
        questions = getIntent().getIntExtra("questions", -1);
        correct = getIntent().getIntExtra("correct", -1);

        String trim = topic.split(" ")[0];

        int QuestionNum = getResources().getIdentifier(trim + "_Q" + questions, "string", getPackageName());
        String question = getResources().getString(QuestionNum);
        TextView QuestionTitle = (TextView) findViewById(R.id.TitleQ);
        QuestionTitle.setText(question);

        for (int i=1; i <= 4; i++) {
            int choices = getResources().getIdentifier(trim + "_Q" + questions + "C" + i, "string" , getPackageName());
            String choice =  getResources().getString(choices);
            int RadioId = getResources().getIdentifier("Radio" + i, "id", getPackageName());
            TextView radioChoice = (TextView) findViewById(RadioId);
            radioChoice.setText(choice);
        }
    }

    public void onRadioClick(View view) {
        Button submit = (Button) findViewById(R.id.SubmitButton);
        submit.setVisibility(View.VISIBLE);
        ((RadioButton)view).setChecked(true);
    }

    public void onSubmitClick(View view) {
        String trim = topic.split(" ")[0];

        int chosenOne = choices.indexOfChild(findViewById(choices.getCheckedRadioButtonId()));
        int chosenOneID = getResources().getIdentifier(trim + "_Q" + questions + "C" +
                (chosenOne + 1), "string", getPackageName());
        String chosenAnswer = getResources().getString(chosenOneID);

        int correctOneID = getResources().getIdentifier(trim + "_Q" +
                questions + "A", "integer", getPackageName());
        int correctOne = getResources().getInteger(correctOneID);
        int correctAnswerID = getResources().getIdentifier(trim + "_Q" + questions + "C" +
                correctOne, "string", getPackageName());
        String correctAnswer = getResources().getString(correctAnswerID);

        Intent onToTheNext = new Intent(this, QuizAnswer.class);

        onToTheNext.putExtra("topic", topic);
        onToTheNext.putExtra("chosenOne", chosenAnswer);
        onToTheNext.putExtra("correctOne", correctAnswer);
        onToTheNext.putExtra("QuestionNumber", QuestionNumber);
        onToTheNext.putExtra("questions", questions);
        if (chosenOne + 1 == correctOne) {
            onToTheNext.putExtra("correct", correct + 1);
        } else {
            onToTheNext.putExtra("correct", correct);
        }

        startActivity(onToTheNext);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}