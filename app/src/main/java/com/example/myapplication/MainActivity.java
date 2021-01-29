package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button true_button;
    Button false_button;
    Button nextButton;
    TextView textView;
    Toast toast;
    int questionsCorrect = 0;
    int screenCount = 0;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.questions_australia, true),
            new Question(R.string.questions_oceans, true),
            new Question(R.string.questions_africa, false),
            new Question(R.string.questions_americas, true),
            new Question(R.string.questions_asia, true),
            new Question(R.string.questions_mideast, false),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        true_button = (Button) findViewById(R.id.button);
        false_button = (Button) findViewById(R.id.button2);
        true_button.setOnClickListener(this);
        false_button.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textview);
        textView.setText(mQuestionBank[screenCount].getTextResId());
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // View - area on the screen which is also incidentally something
        boolean bool;
        if (v.getId() != R.id.next_button) {
            if (v.getId() == R.id.button) { // is this the button
                bool = true;

                //toast.setGravity(Gravity.TOP, 0, 0);
            } else if (v.getId() == R.id.button2) {
                bool = false;
            }
            else{
                bool = false;
            }
            if (bool == mQuestionBank[screenCount].isAnswerTrue()) {
                questionsCorrect++;
                bool = false;
                toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            } else {
                toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            screenCount++;
            textView.setText(mQuestionBank[screenCount].getTextResId());
        }
    }


}