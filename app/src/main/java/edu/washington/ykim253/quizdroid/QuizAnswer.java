package edu.washington.ykim253.quizdroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class QuizAnswer extends ActionBarActivity {

    private String currentTopicName;
    private int currentQuestionNumber;
    private int correctAnswers;
    private int AnswerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_answer);

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

        currentTopicName = getIntent().getStringExtra("topic");
        currentQuestionNumber = getIntent().getIntExtra("questionNumber", 0);
        correctAnswers = getIntent().getIntExtra("numberCorrect", 0);
        AnswerNumber = getIntent().getIntExtra("answerNumber", 0);

        String correctAnswer = "";
        String originalAnswer = "";

        TextView answerOriginalAnswer = (TextView) findViewById(R.id.answerOriginalAnswer);
        TextView answerCorrectAnswer = (TextView) findViewById(R.id.answerCorrectAnswer);
        TextView answerNumberCorrect = (TextView) findViewById(R.id.answerNumberCorrect);
        Button answerNextButton = (Button) findViewById(R.id.answerNextButton);

        if(currentTopicName.equalsIgnoreCase("Marvel Super Heroes")) {
            originalAnswer = marvelQuestions.get(currentQuestionNumber - 1)[AnswerNumber - 1];
            correctAnswer = marvelQuestions.get(currentQuestionNumber - 1)[1];
        }
        else if(currentTopicName.equalsIgnoreCase("Math")) {
            originalAnswer = mathQuestions.get(currentQuestionNumber - 1)[AnswerNumber - 1];
            correctAnswer = mathQuestions.get(currentQuestionNumber - 1)[1];
        }
        else if(currentTopicName.equalsIgnoreCase("Physics")) {
            originalAnswer = physicsQuestions.get(currentQuestionNumber - 1)[AnswerNumber - 1];
            correctAnswer = physicsQuestions.get(currentQuestionNumber - 1)[1];
        }

        if(currentQuestionNumber == 3) {
            answerNextButton.setText("Finish");
        }

        if(AnswerNumber == 2) {
            correctAnswers++;
        }

        answerCorrectAnswer.setText(correctAnswer);
        answerOriginalAnswer.setText(originalAnswer);
        answerNumberCorrect.setText("You have " + correctAnswers + " out of " + "3 possible.");
    }

    public void onNextClick(View view) {
        if(currentQuestionNumber < 3) {
            Intent sendToQuestionPage = new Intent(this, QuizQuestion.class);

            sendToQuestionPage.putExtra("topic", currentTopicName);
            sendToQuestionPage.putExtra("questionNumber", currentQuestionNumber);
            sendToQuestionPage.putExtra("numberCorrect", correctAnswers);
        }
        else {
            Intent startStartPage = new Intent(this, MainActivity.class);

            startActivity(startStartPage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}