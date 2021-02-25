package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button true_button;
    Button false_button;
    Button nextButton;
    Button prevButton;
    Button cheatButton;
    TextView textView;
    Toast toast;
    int questionsCorrect = 0;
    int screenCount = 0;
    boolean bool = false;
    private static final int REQUEST_CODE_CHEAT = 0;


    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

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

    boolean[] answered = new boolean[mQuestionBank.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) { //at Start-Up
        super.onCreate(savedInstanceState); //open where phone once was
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main); //set ContentView to what we have in layout/main
        if (savedInstanceState != null){
            screenCount = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        for (boolean booleans : answered){
            booleans = false;
        }
        true_button = (Button) findViewById(R.id.button); // Declaring JavaButton Equal To .xml
        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(this);
        // button
        prevButton = (Button) findViewById(R.id.prev_button);
        false_button = (Button) findViewById(R.id.button2); // --
        true_button.setOnClickListener(this); // make sure the button is registering a click
        false_button.setOnClickListener(this); // --
        textView = (TextView) findViewById(R.id.textview); //setting TextView equal to .xml
        // textview
        textView.setText(mQuestionBank[screenCount].getTextResId()); //set Text to Question text
        textView.setOnClickListener(this);
        nextButton = (Button) findViewById(R.id.next_button); //Declaring JavaButton Equal to .xml
        // button
        nextButton.setOnClickListener(this); // make sure button is registering on click
        if (answered[screenCount] == true){
            block();
        }
        else{
            open();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, screenCount);

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onClick(View v) { // View - area on the screen which is also incidentally something
        // - happens when a 'click' is registered

        if (v.getId() == R.id.textview || v.getId() == R.id.next_button){
            if (screenCount != mQuestionBank.length - 1){
                updateQuestion();
            }
            else{
                int Answered = 0;
                for (boolean booleanz : answered){
                    if (booleanz == true){
                         Answered++;
                    }
                }
                double firstproportion = (double) (100.0 * questionsCorrect/Answered);
                double secondproportion = (double) (100.0 * questionsCorrect/mQuestionBank.length);
                if (!bool){
                    showTop(toast.makeText(this, "Percentage Correct Out of Those Answered:" + String.valueOf(firstproportion) + "%" + "\n" + "Percentage Correct Out of Total Questions: " + String.valueOf(secondproportion) + "%", Toast.LENGTH_LONG));
                    nextButton.setText("Quit");
                    bool = true;
                }
                else if (bool){
                    onStop();
                }
                //updateQuestion();
            }
            if (answered[screenCount] == false){
                open();
            }
            else if (answered[screenCount] == true){
                block();
            }
        }
        else if (v.getId() != R.id.next_button && v.getId() != R.id.prev_button && v.getId() != R.id.cheat_button) { //if not next
            if (screenCount <= mQuestionBank.length - 1){
                if (answered[screenCount] == false) {
                    answered[screenCount] = true;
                    block();
                    if (v.getId() == R.id.button) { // is this the button
                        bool = true; //first button clicked

                        //toast.setGravity(Gravity.TOP, 0, 0);
                    } else if (v.getId() == R.id.button2) {
                        bool = false; //second button clicked
                    } else {
                        bool = false;
                    }
                    checkAnswer(bool);
                } else {

                }
            }

        }
        else if (v.getId() == R.id.prev_button){
            if (screenCount != 0){
                prevQuestion();
            }
            if (answered[screenCount] == false){
                open();
            }
            else if (answered[screenCount] == true){
                block();
            }

        }
        else if (v.getId() == R.id.cheat_button){
            boolean answerIsTrue = mQuestionBank[screenCount].isAnswerTrue();
            Intent intent = CheatActivity.newIntent(this, answerIsTrue);
            startActivityForResult(intent,
                    REQUEST_CODE_CHEAT);
        }
    }

    private void block(){
        true_button.setEnabled(false);
        false_button.setEnabled(false);
        true_button.setClickable(false);
        false_button.setClickable(false);
    }

    private void open(){
        true_button.setEnabled(true);
        false_button.setEnabled(true);
        true_button.setClickable(true);
        false_button.setClickable(true);
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
        prevButton.setOnClickListener(this);
    }

    private void prevQuestion() {
        screenCount--; //increments the screen
        textView.setText(mQuestionBank[screenCount].getTextResId()); // shows following screen
    }

    //helper method that takes a Toast and shows/displays it at the top
    public void showTop(Toast toasty){
        toasty.setGravity(Gravity.TOP, 0, 200); //place at top (but below banner)
        toasty.show(); //show
    }


}