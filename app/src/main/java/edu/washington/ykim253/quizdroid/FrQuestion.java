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

    View v;

    public FrQuestion() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            QuestionNumber = getArguments().getInt("QuestionNumber");
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
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(topic.equals("Math")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Math_Q1C1);
                                correctAnswer = getString(R.string.Math_Q1C1);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Math_Q2C1);
                                correctAnswer = getString(R.string.Math_Q2C4);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Math_Q3C1);
                                correctAnswer = getString(R.string.Math_Q3C3);
                            }
                        } else if (topic.equals("Physics")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Physics_Q1C1);
                                correctAnswer = getString(R.string.Physics_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Physics_Q2C1);
                                correctAnswer = getString(R.string.Physics_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Physics_Q3C1);
                                correctAnswer = getString(R.string.Physics_Q3C3);
                            }
                        } else {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Marvel_Q1C1);
                                correctAnswer = getString(R.string.Marvel_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Marvel_Q2C1);
                                correctAnswer = getString(R.string.Marvel_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Marvel_Q3C1);
                                correctAnswer = getString(R.string.Marvel_Q3C4);
                            }
                        }
                        Bundle onToTheNext = new Bundle();

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
        tester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(v.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(topic.equals("Math")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Math_Q1C2);
                                correctAnswer = getString(R.string.Math_Q1C1);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Math_Q2C2);
                                correctAnswer = getString(R.string.Math_Q2C4);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Math_Q3C2);
                                correctAnswer = getString(R.string.Math_Q3C3);
                            }
                        } else if (topic.equals("Physics")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Physics_Q1C2);
                                correctAnswer = getString(R.string.Physics_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Physics_Q2C2);
                                correctAnswer = getString(R.string.Physics_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Physics_Q3C2);
                                correctAnswer = getString(R.string.Physics_Q3C3);
                            }
                        } else {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Marvel_Q1C2);
                                correctAnswer = getString(R.string.Marvel_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Marvel_Q2C2);
                                correctAnswer = getString(R.string.Marvel_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Marvel_Q3C2);
                                correctAnswer = getString(R.string.Marvel_Q3C4);
                            }
                        }
                        Bundle onToTheNext = new Bundle();

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
        tester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(topic.equals("Math")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Math_Q1C3);
                                correctAnswer = getString(R.string.Math_Q1C1);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Math_Q2C3);
                                correctAnswer = getString(R.string.Math_Q2C4);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Math_Q3C3);
                                correctAnswer = getString(R.string.Math_Q3C3);
                            }
                        } else if (topic.equals("Physics")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Physics_Q1C3);
                                correctAnswer = getString(R.string.Physics_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Physics_Q2C3);
                                correctAnswer = getString(R.string.Physics_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Physics_Q3C3);
                                correctAnswer = getString(R.string.Physics_Q3C3);
                            }
                        } else {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Marvel_Q1C3);
                                correctAnswer = getString(R.string.Marvel_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Marvel_Q2C3);
                                correctAnswer = getString(R.string.Marvel_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Marvel_Q3C3);
                                correctAnswer = getString(R.string.Marvel_Q3C4);
                            }
                        }
                        Bundle onToTheNext = new Bundle();

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
        tester4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                ((RadioButton)v).setChecked(true);
                submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(topic.equals("Math")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Math_Q1C4);
                                correctAnswer = getString(R.string.Math_Q1C1);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Math_Q2C4);
                                correctAnswer = getString(R.string.Math_Q2C4);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Math_Q3C4);
                                correctAnswer = getString(R.string.Math_Q3C3);
                            }
                        } else if (topic.equals("Physics")) {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Physics_Q1C4);
                                correctAnswer = getString(R.string.Physics_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Physics_Q2C4);
                                correctAnswer = getString(R.string.Physics_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Physics_Q3C4);
                                correctAnswer = getString(R.string.Physics_Q3C3);
                            }
                        } else {
                            if (questions == 1) {
                                chosenAnswer = getString(R.string.Marvel_Q1C4);
                                correctAnswer = getString(R.string.Marvel_Q1C2);
                            }
                            if (questions == 2) {
                                chosenAnswer = getString(R.string.Marvel_Q2C4);
                                correctAnswer = getString(R.string.Marvel_Q2C1);
                            }
                            if (questions == 3) {
                                chosenAnswer = getString(R.string.Marvel_Q3C4);
                                correctAnswer = getString(R.string.Marvel_Q3C4);
                            }
                        }
                        Bundle onToTheNext = new Bundle();

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

        //
        // for later for me to figure out more. hardcoded stuff to make it work for now
        // submit button listener
 /*       submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = topic.split(" ")[0];

                int chosenOne = choices.indexOfChild(v.findViewById(choices.getCheckedRadioButtonId()));
                int chosenOneID = getResources().getIdentifier(topic.split(" ")[0] + "_Q" + questions + "C" +
                        (chosenOne + 1), "string", getActivity().getPackageName());

                if(topic.equals("Math")) {
                    String desc = getString(R.string.Math_Overview);
                    if (questions == 1) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Math_Q1C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Math_Q1C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Math_Q1C3);
                        } else {
                            chosenAnswer = getString(R.string.Math_Q1C4);
                        }
                        correctAnswer = getString(R.string.Math_Q1C1);
                    }
                    if (questions == 2) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Math_Q2C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Math_Q2C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Math_Q2C3);
                        } else {
                            chosenAnswer = getString(R.string.Math_Q2C4);
                        }
                        correctAnswer = getString(R.string.Math_Q2C4);
                    }
                    if (questions == 3) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Math_Q3C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Math_Q3C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Math_Q3C3);
                        } else {
                            chosenAnswer = getString(R.string.Math_Q3C4);
                        }
                        correctAnswer = getString(R.string.Math_Q2C3);
                    }
                } else if (topic.equals("Physics")) {
                    String desc = getString(R.string.Physics_Overview);
                    if (questions == 1) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Physics_Q1C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Physics_Q1C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Physics_Q1C3);
                        } else {
                            chosenAnswer = getString(R.string.Physics_Q1C4);
                        }
                        correctAnswer = getString(R.string.Physics_Q1C2);
                    }
                    if (questions == 2) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Physics_Q2C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Physics_Q2C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Physics_Q2C3);
                        } else {
                            chosenAnswer = getString(R.string.Physics_Q2C4);
                        }
                        correctAnswer = getString(R.string.Physics_Q2C1);
                    }
                    if (questions == 3) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Physics_Q3C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Physics_Q3C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Physics_Q3C3);
                        } else {
                            chosenAnswer = getString(R.string.Physics_Q3C4);
                        }
                        correctAnswer = getString(R.string.Physics_Q2C3);
                    }
                } else {
                    String desc = getString(R.string.Marvel_Overview);
                    if (questions == 1) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Marvel_Q1C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Marvel_Q1C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Marvel_Q1C3);
                        } else {
                            chosenAnswer = getString(R.string.Marvel_Q1C4);
                        }
                        correctAnswer = getString(R.string.Marvel_Q1C2);
                    }
                    if (questions == 2) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Marvel_Q2C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Marvel_Q2C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Marvel_Q2C3);
                        } else {
                            chosenAnswer = getString(R.string.Marvel_Q2C4);
                        }
                        correctAnswer = getString(R.string.Marvel_Q2C1);
                    }
                    if (questions == 3) {
                        if(chosenOne == 0) {
                            chosenAnswer = getString(R.string.Marvel_Q3C1);
                        } else if (chosenOne == 1) {
                            chosenAnswer = getString(R.string.Marvel_Q3C2);
                        } else if (chosenOne == 2) {
                            chosenAnswer = getString(R.string.Marvel_Q3C3);
                        } else {
                            chosenAnswer = getString(R.string.Marvel_Q3C4);
                        }
                        correctAnswer = getString(R.string.Marvel_Q2C4);
                    }
                }

                int correctOneID = getResources().getIdentifier(trim + "_Q" +
                        questions + "A", "integer", getActivity().getPackageName());
                int correctOne = getResources().getInteger(correctOneID);
                int correctAnswerID = getResources().getIdentifier(trim + "_Q" + questions + "C" +
                        correctOne, "string", getActivity().getPackageName());

                Bundle onToTheNext = new Bundle();

                onToTheNext.putString("topic", topic);
                onToTheNext.putString("chosenOne", chosenAnswer);
                onToTheNext.putString("correctOne", correctAnswer);
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
*/
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
