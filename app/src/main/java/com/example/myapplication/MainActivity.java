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

    private Question[] mQuestionBank = new Question[]{ //Questions Created
            new Question(R.string.questions_australia, true),
            new Question(R.string.questions_oceans, true),
            new Question(R.string.questions_africa, false),
            new Question(R.string.questions_americas, true),
            new Question(R.string.questions_asia,
                    true),
            new Question(R.string.questions_mideast,
                    false),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //at Start-Up
        super.onCreate(savedInstanceState); //open where phone once was
        setContentView(R.layout.activity_main); //set ContentView to what we have in layout/main
        true_button = (Button) findViewById(R.id.button); // Declaring JavaButton Equal To .xml
        // button
        false_button = (Button) findViewById(R.id.button2); // --
        true_button.setOnClickListener(this); // make sure the button is registering a click
        false_button.setOnClickListener(this); // --
        textView = (TextView) findViewById(R.id.textview); //setting TextView equal to .xml
        // textview
        textView.setText(mQuestionBank[screenCount].getTextResId()); //set Text to Question text
        nextButton = (Button) findViewById(R.id.next_button); //Declaring JavaButton Equal to .xml
        // button
        nextButton.setOnClickListener(this); // make sure button is registering on click
    }

    @Override
    public void onClick(View v) { // View - area on the screen which is also incidentally something
        // - happens when a 'click' is registered
        boolean bool;
        if (v.getId() != R.id.next_button) { //if not next
            if (v.getId() == R.id.button) { // is this the button
                bool = true; //first button clicked

                //toast.setGravity(Gravity.TOP, 0, 0);
            } else if (v.getId() == R.id.button2) {
                bool = false; //second button clicked
            }
            else{
                bool = false;
            }
            checkAnswer(bool);
        }
        else{
            updateQuestion();
        }
    }

    private void checkAnswer(boolean bool) {
        if (bool == mQuestionBank[screenCount].isAnswerTrue()) { //if answer is correct
            questionsCorrect++; //increase num of questions
            bool = false; //set bool to false
            showTop(toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT));
            // shows correct
        } else {
            showTop(toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT));
            //shows incorrect
        }
    }

    private void updateQuestion() {
        screenCount++; //increments the screen
        textView.setText(mQuestionBank[screenCount].getTextResId()); // shows following screen
    }

    //helper method that takes a Toast and shows/displays it at the top
    public void showTop(Toast toasty){
        toasty.setGravity(Gravity.TOP, 0, 200); //place at top (but below banner)
        toasty.show(); //show
    }


}