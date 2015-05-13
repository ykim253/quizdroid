package edu.washington.ykim253.quizdroid;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FrOverview extends Fragment{

    private Activity hostActivity;
    private Topic choseOne;
    private String topic;
    private int QuestionNumber;

    public FrOverview() {

    }
    public FrOverview(Topic chosenOne) {
        this.choseOne = chosenOne;// Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            QuestionNumber = getArguments().getInt("QuestionNumber");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View overviewView = inflater.inflate(R.layout.topic_overview, container, false);

        TextView Overview = (TextView) overviewView.findViewById(R.id.Overview);
        TextView NumQuestion = (TextView) overviewView.findViewById(R.id.NumQuestion);
        TextView description = (TextView) overviewView.findViewById(R.id.Desc);
        Button Begin = (Button) overviewView.findViewById(R.id.Begin);

        Overview.setText(choseOne.getTitle() + " Overview");
        description.setText(choseOne.getLong());

        NumQuestion.setText("There are " + choseOne.getQuestions().size() + " questions in this topic");

        Begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle onToTheNextOne = new Bundle();
                onToTheNextOne.putInt("QuestionNumber", QuestionNumber);
                onToTheNextOne.putString("topic", topic);
                onToTheNextOne.putInt("questions", 0);
                onToTheNextOne.putInt("correct", 0);
                if (hostActivity instanceof SecondActivity) {
                    ((SecondActivity) hostActivity).questionTime(onToTheNextOne);
                }
            }
        });

        return overviewView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    public void onDetach() {
        super.onDetach();

    }

}
