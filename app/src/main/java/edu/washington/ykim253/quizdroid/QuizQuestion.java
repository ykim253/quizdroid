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
import java.util.ArrayList;

public class QuizQuestion extends ActionBarActivity {

    private int radioButtonClicked = 0;
    private String currentTopicName;
    private int currentQuestionNumber;
    private int numberOfCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_questions);


        currentTopicName = getIntent().getStringExtra("topic");
        currentQuestionNumber = getIntent().getIntExtra("questions", 0);
        numberOfCorrect = getIntent().getIntExtra("correct", 0);

        String[] mathQuestionTitles = new String[] {"What is 2 to the power of 2?", "What is x+y?", "What is 2+6"};
        String[] physicsQuestionTitles = new String[] {"Who made 3 laws of motion?", "E = ___", "What goes up must come ___?"};
        String[] marvelQuestionTitles = new String[] {"Iron Man is played by ?", "Upcoming Marvel Movie is?", "Where is Captain America from?"};

        String[] mathQuestionAnswers1 = new String[] {"8", "4", "2", "6"};
        String[] physicsQuestionAnswers1 = new String[] {"Bacon", "Newton", "Einstein", "Nobel"};
        String[] marvelQuestionAnswers1 = new String[] {"Iron Man", "RDJR", "Gary Oldman", "Ultron"};

        String[] mathQuestionAnswers2 = new String[] {"IDK", "Unsolvable", "x=2, y=2", "11"};
        String[] physicsQuestionAnswers2 = new String[] {"??", "MC2", "e", "Hammertime"};
        String[] marvelQuestionAnswers2 = new String[] {"Daredevil", "Age Of Ultron", "Infinity War", "Money"};

        String[] mathQuestionAnswers3 = new String[] {"10", "8", "2", "6"};
        String[] physicsQuestionAnswers3 = new String[] {"be shot", "come down", "idc", "I can't tell"};
        String[] marvelQuestionAnswers3 = new String[] {"Murica!", "North America", "South America", "All America"};

        ArrayList<String[]> mathQuestions = new ArrayList<String[]>();
        ArrayList<String[]> physicsQuestions = new ArrayList<String[]>();
        ArrayList<String[]> marvelQuestions = new ArrayList<String[]>();

        mathQuestions.add(mathQuestionAnswers1);
        mathQuestions.add(mathQuestionAnswers2);
        mathQuestions.add(mathQuestionAnswers3);

        physicsQuestions.add(physicsQuestionAnswers1);
        physicsQuestions.add(physicsQuestionAnswers2);
        physicsQuestions.add(physicsQuestionAnswers3);

        marvelQuestions.add(marvelQuestionAnswers1);
        marvelQuestions.add(marvelQuestionAnswers2);
        marvelQuestions.add(marvelQuestionAnswers3);

        TextView questionTitle = (TextView) findViewById(R.id.questionTitle);
        RadioGroup questionRadioGroup = (RadioGroup) findViewById(R.id.questionRadioGroup);
        RadioButton firstRadio = (RadioButton) findViewById(R.id.firstRadio);
        RadioButton secondRadio = (RadioButton) findViewById(R.id.secondRadio);
        RadioButton thirdRadio = (RadioButton) findViewById(R.id.thirdRadio);
        RadioButton fourthRadio = (RadioButton) findViewById(R.id.fourthRadio);
        Button questionSubmitButton = (Button) findViewById(R.id.questionSubmitButton);

        if(currentTopicName.equalsIgnoreCase("Marvel Super Heroes")) {
            questionTitle.setText(marvelQuestionTitles[currentQuestionNumber - 1]);

            firstRadio.setText(marvelQuestions.get(currentQuestionNumber - 1)[0]);
            secondRadio.setText(marvelQuestions.get(currentQuestionNumber - 1)[1]);
            thirdRadio.setText(marvelQuestions.get(currentQuestionNumber - 1)[2]);
            fourthRadio.setText(marvelQuestions.get(currentQuestionNumber - 1)[3]);
        }
        else if(currentTopicName.equalsIgnoreCase("Math")) {
            questionTitle.setText(mathQuestionTitles[currentQuestionNumber - 1]);

            firstRadio.setText(mathQuestions.get(currentQuestionNumber - 1)[0]);
            secondRadio.setText(mathQuestions.get(currentQuestionNumber - 1)[1]);
            thirdRadio.setText(mathQuestions.get(currentQuestionNumber - 1)[2]);
            fourthRadio.setText(mathQuestions.get(currentQuestionNumber - 1)[3]);
        }
        else if(currentTopicName.equalsIgnoreCase("Physics")) {
            questionTitle.setText(physicsQuestionTitles[currentQuestionNumber - 1]);

            firstRadio.setText(physicsQuestions.get(currentQuestionNumber - 1)[0]);
            secondRadio.setText(physicsQuestions.get(currentQuestionNumber - 1)[1]);
            thirdRadio.setText(physicsQuestions.get(currentQuestionNumber - 1)[2]);
            fourthRadio.setText(physicsQuestions.get(currentQuestionNumber - 1)[3]);
        }

    }

    public void onRadioClick(View view) {
        radioButtonClicked = Integer.parseInt((String)view.getTag());
        ((RadioButton)view).setChecked(true);
    }

    public void onSubmitClick(View view) {
        if(radioButtonClicked != 0) {
            Intent QuizAnswer = new Intent(this, QuizAnswer.class);

            QuizAnswer.putExtra("topic", currentTopicName);
            QuizAnswer.putExtra("questionNumber", currentQuestionNumber);
            QuizAnswer.putExtra("numberCorrect", numberOfCorrect);
            QuizAnswer.putExtra("answerNumber", radioButtonClicked);

            startActivity(QuizAnswer);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}