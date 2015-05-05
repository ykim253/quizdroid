package edu.washington.ykim253.quizdroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class FrQuestion extends Fragment {

    Activity hostActivity;
    private String topic;
    private int QuestionNumber;
    private int questions;
    private int correct;
    RadioGroup choices;
    Button submit;
    private String chosenAnswer;
    private String correctAnswer;

    View v;

    public FrQuestion() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            QuestionNumber = getArguments().getInt("numQuestions");
            questions = getArguments().getInt("questions");
            correct = getArguments().getInt("correct");
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.quiz_questions, container, false);
        choices = (RadioGroup) v.findViewById(R.id.choices);

        //questions get set up
        int QuestionNum = getResources().getIdentifier(topic.split(" ")[0] + "_Q" + questions, "string", getActivity().getPackageName());
        String question = getResources().getString(QuestionNum);
        TextView QuestionTitle = (TextView) v.findViewById(R.id.TitleQ);
        QuestionTitle.setText(question);
        submit = (Button) v.findViewById(R.id.SubmitButton);

        for (int i=1; i <= 4; i++) {
            int choices = getResources().getIdentifier(topic.split(" ")[0] + "_Q" + questions + "C" + i, "string" , getActivity().getPackageName());
            String choice =  getResources().getString(choices);
            int RadioId = getResources().getIdentifier("Radio" + i, "id", getActivity().getPackageName());
            TextView radioChoice = (TextView) v.findViewById(RadioId);
            radioChoice.setText(choice);
        }

        //listen to radio click to enable submit
        RadioButton tester = (RadioButton) v.findViewById(R.id.Radio1);
        tester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
            }
        });

        RadioButton tester2 = (RadioButton) v.findViewById(R.id.Radio2);
        tester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(v.VISIBLE);
                ((RadioButton)v).setChecked(true);
            }
        });

        RadioButton tester3 = (RadioButton) v.findViewById(R.id.Radio3);
        tester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
            }
        });

        RadioButton tester4 = (RadioButton) v.findViewById(R.id.Radio4);
        tester4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
            }
        });

        //submit button listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = topic.split(" ")[0];

                int chosenOne = choices.indexOfChild(v.findViewById(choices.getCheckedRadioButtonId()));
                int chosenOneID = getResources().getIdentifier(topic.split(" ")[0] + "_Q" + questions + "C" +
                        (chosenOne + 1), "string", getActivity().getPackageName());

                int correctOneID = getResources().getIdentifier(trim + "_Q" +
                        questions + "A", "integer", getActivity().getPackageName());
                int correctOne = getResources().getInteger(correctOneID);
                int correctAnswerID = getResources().getIdentifier(trim + "_Q" + questions + "C" +
                        correctOne, "string", getActivity().getPackageName());
                //correctAnswer = getResources().getString(correctAnswerID);

                Bundle onToTheNext = new Bundle();

                onToTheNext.putString("topic", topic);
                onToTheNext.putString("chosenOne", chosenTxt);
                onToTheNext.putInt("correctOne", correctOne);
                onToTheNext.putInt("QuestionNumber", QuestionNumber);
                onToTheNext.putInt("questions", questions);
                if (chosenOne + 1 == correctOne) {
                    onToTheNext.putInt("correct", correct + 1);
                } else {
                    onToTheNext.putInt("correct", correct);
                }

                if (hostActivity instanceof SecondActivity) {
                    ((SecondActivity) hostActivity).answerTime(onToTheNext);
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.hostActivity = activity;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri, Bundle nextOne);
    }
}
