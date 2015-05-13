package edu.washington.ykim253.quizdroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
    private Topic topics;
    private Question listQ;

    View v;

    public FrQuestion() {

    }

    public FrQuestion(Topic topics) {
        this.topics = topics;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            QuestionNumber = getArguments().getInt("QuestionNumber");
            questions = getArguments().getInt("questions");
            correct = getArguments().getInt("correct");
            listQ = topics.getQuestions().get(questions);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.quiz_questions, container, false);
        choices = (RadioGroup) v.findViewById(R.id.choices);

        //questions get set up
        TextView QuestionTitle = (TextView) v.findViewById(R.id.TitleQ);
        QuestionTitle.setText(listQ.getQuestionText());
        submit = (Button) v.findViewById(R.id.SubmitButton);

        //listen to radio click to enable submit
        RadioButton tester = (RadioButton) v.findViewById(R.id.Radio1);
        tester.setText(listQ.getChoice1());
        tester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Bundle onToTheNext = new Bundle();
                        chosenAnswer = listQ.getChoice1();
                        if (listQ.getAnswer() == 1) {
                            correctAnswer = listQ.getChoice1();
                        } else if (listQ.getAnswer() == 2) {
                            correctAnswer = listQ.getChoice2();
                        } else if (listQ.getAnswer() == 3) {
                            correctAnswer = listQ.getChoice3();
                        } else {
                            correctAnswer = listQ.getChoice4();
                        }
                        onToTheNext.putString("topic", topic);
                        onToTheNext.putString("chosenOne", chosenAnswer);
                        onToTheNext.putString("correctOne", correctAnswer);
                        onToTheNext.putInt("QuestionNumber", QuestionNumber);
                        onToTheNext.putInt("questions", questions);
                        if (chosenAnswer.equals(correctAnswer)) {
                            onToTheNext.putInt("correct", correct + 1);
                        } else {
                            onToTheNext.putInt("correct", correct);
                        }

                        if (hostActivity instanceof SecondActivity) {
                            ((SecondActivity) hostActivity).answerTime(onToTheNext);
                        }
                    }
                });
            }
        });

        RadioButton tester2 = (RadioButton) v.findViewById(R.id.Radio2);
        tester2.setText(listQ.getChoice2());
        tester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(v.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Bundle onToTheNext = new Bundle();
                        chosenAnswer = listQ.getChoice2();
                        if (listQ.getAnswer() == 1) {
                            correctAnswer = listQ.getChoice1();
                        } else if (listQ.getAnswer() == 2) {
                            correctAnswer = listQ.getChoice2();
                        } else if (listQ.getAnswer() == 3) {
                            correctAnswer = listQ.getChoice3();
                        } else {
                            correctAnswer = listQ.getChoice4();
                        }
                        onToTheNext.putString("topic", topic);
                        onToTheNext.putString("chosenOne", chosenAnswer);
                        onToTheNext.putString("correctOne", correctAnswer);
                        onToTheNext.putInt("QuestionNumber", QuestionNumber);
                        onToTheNext.putInt("questions", questions);
                        if (chosenAnswer.equals(correctAnswer)) {
                            onToTheNext.putInt("correct", correct + 1);
                        } else {
                            onToTheNext.putInt("correct", correct);
                        }

                        if (hostActivity instanceof SecondActivity) {
                            ((SecondActivity) hostActivity).answerTime(onToTheNext);
                        }
                    }
                });
            }
        });

        RadioButton tester3 = (RadioButton) v.findViewById(R.id.Radio3);
        tester3.setText(listQ.getChoice3());
        tester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Bundle onToTheNext = new Bundle();
                        chosenAnswer = listQ.getChoice3();
                        if (listQ.getAnswer() == 1) {
                            correctAnswer = listQ.getChoice1();
                        } else if (listQ.getAnswer() == 2) {
                            correctAnswer = listQ.getChoice2();
                        } else if (listQ.getAnswer() == 3) {
                            correctAnswer = listQ.getChoice3();
                        } else {
                            correctAnswer = listQ.getChoice4();
                        }
                        onToTheNext.putString("topic", topic);
                        onToTheNext.putString("chosenOne", chosenAnswer);
                        onToTheNext.putString("correctOne", correctAnswer);
                        onToTheNext.putInt("QuestionNumber", QuestionNumber);
                        onToTheNext.putInt("questions", questions);
                        if (chosenAnswer.equals(correctAnswer)) {
                            onToTheNext.putInt("correct", correct + 1);
                        } else {
                            onToTheNext.putInt("correct", correct);
                        }

                        if (hostActivity instanceof SecondActivity) {
                            ((SecondActivity) hostActivity).answerTime(onToTheNext);
                        }
                    }
                });
            }
        });

        RadioButton tester4 = (RadioButton) v.findViewById(R.id.Radio4);
        tester4.setText(listQ.getChoice4());
        tester4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Bundle onToTheNext = new Bundle();
                        chosenAnswer = listQ.getChoice4();
                        if (listQ.getAnswer() == 1) {
                            correctAnswer = listQ.getChoice1();
                        } else if (listQ.getAnswer() == 2) {
                            correctAnswer = listQ.getChoice2();
                        } else if (listQ.getAnswer() == 3) {
                            correctAnswer = listQ.getChoice3();
                        } else {
                            correctAnswer = listQ.getChoice4();
                        }
                        onToTheNext.putString("topic", topic);
                        onToTheNext.putString("chosenOne", chosenAnswer);
                        onToTheNext.putString("correctOne", correctAnswer);
                        onToTheNext.putInt("QuestionNumber", QuestionNumber);
                        onToTheNext.putInt("questions", questions);
                        if (chosenAnswer.equals(correctAnswer)) {
                            onToTheNext.putInt("correct", correct + 1);
                        } else {
                            onToTheNext.putInt("correct", correct);
                        }

                        if (hostActivity instanceof SecondActivity) {
                            ((SecondActivity) hostActivity).answerTime(onToTheNext);
                        }
                    }
                });
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
