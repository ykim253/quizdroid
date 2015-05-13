package edu.washington.ykim253.quizdroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class SecondActivity extends ActionBarActivity {
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
}