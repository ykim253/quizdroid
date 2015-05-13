package edu.washington.ykim253.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public class FrAnswer extends Fragment {

    Activity hostActivity;
    private String topic;
    private int QuestionNumber;
    private int questions;
    private int correct;
    private String correctOne;
    private String chosenOne;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            QuestionNumber = getArguments().getInt("QuestionNumber");
            questions = getArguments().getInt("questions");
            correct = getArguments().getInt("correct");
            correctOne = getArguments().getString("correctOne");
            chosenOne = getArguments().getString("chosenOne");
        } else {
            throw new IllegalArgumentException("Bundle passed incorrectly");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.quiz_answer, container, false);

        String correctAnswer = "Correct Answer: " + correctOne;
        String originalAnswer = "Selected Answer: " + chosenOne;

        TextView OGAnswer = (TextView) v.findViewById(R.id.OGAnswer);
        TextView CorrectAnswer = (TextView) v.findViewById(R.id.CorrectAnswer);
        TextView howMany = (TextView) v.findViewById(R.id.howMany);
        Button NextButton = (Button) v.findViewById(R.id.NextButton);

        //if there are no more questions, switch the button
        if(questions == QuestionNumber - 1) {
            NextButton.setText("Finish");
        }

        //set the user and correct answer for feedback
        CorrectAnswer.setText(correctAnswer);
        OGAnswer.setText(originalAnswer);
        howMany.setText("You have " + correct + " out of " + QuestionNumber + " possible.");

        Button next = (Button) v.findViewById(R.id.NextButton);

        // next button listener
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions < QuestionNumber - 1) {
                    Bundle backToIt = new Bundle();

                    backToIt.putString("topic", topic);
                    backToIt.putInt("QuestionNumber", QuestionNumber);
                    backToIt.putInt("questions", questions + 1);
                    backToIt.putInt("correct", correct);
                    if (hostActivity instanceof SecondActivity) {
                        ((SecondActivity) hostActivity).questionTime(backToIt);
                    }
                }
                else {
                    Intent Restart = new Intent(getActivity(), MainActivity.class);
                    startActivity(Restart);
                }
            }
        });

        return v;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    public void onDetach() {
        super.onDetach();

    }

}
