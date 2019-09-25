package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewTimer;
    TextView textViewProblem;
    TextView textViewScore;
    TextView textViewStatus;

    androidx.gridlayout.widget.GridLayout gridLayout;

    Button goButton;
    Button playAgainButton;

    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;


    Random rand;

    int firstNum;
    int secondNum;
    int problemSum;
    int random;
    int pastRandomNumber = 0;
    int flagCount = 0;
    int currentScore = 0;
    int totalScore = 0;
    int randomCounter = 0;

    int pastChoiceButton = 0;

    List choicesList;

    String choiceTapped;
    String yourScoreIs = "Your score is: ";
    String correct = "Correct!";
    String wrong = "Wrong!";

    public void timer() {

        new CountDownTimer(30000+200, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                textViewTimer.setText(Long.toString(millisUntilFinished/1000) + "s");

            }

            public void onFinish(){
                textViewStatus.setVisibility(View.VISIBLE);
                displayStatus(yourScoreIs + currentScore + "/" + totalScore);
                playAgainButton.setVisibility(View.VISIBLE);
                choice1.setEnabled(false);
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                choice4.setEnabled(false);


            }
        }.start();

    }

    public void playAgain(View view) {

            currentScore = 0;
            totalScore = 0;

            displayScore();

            playAgainButton.setVisibility(View.INVISIBLE);
            textViewStatus.setVisibility(View.INVISIBLE);

            choice1.setEnabled(true);
            choice2.setEnabled(true);
            choice3.setEnabled(true);
            choice4.setEnabled(true);

            generateProblem();
            generateChoices();
            timer();

    }

    //String buttonId;
    public void choicesClicked(View view) {

        flagCount = 0;
        pastRandomNumber = 0;


        String resourceName = view.getResources().getResourceName(view.getId());

        System.out.println(resourceName);

        switch (view.getId()) {
            case R.id.choice1:
                // do something
                choiceTapped = choice1.getText().toString();
                if(problemSum == Integer.parseInt(choiceTapped)){

                    currentScore++;
                    Log.i("butto1", "you got the right answer");
                    displayStatus(correct);

                } else {


                    displayStatus(wrong);
                }

                break;
            case R.id.choice2:
                // do something else
                choiceTapped = choice2.getText().toString();
                if(problemSum == Integer.parseInt(choiceTapped)){

                    currentScore++;
                    Log.i("butto1", "you got the right answer");
                    displayStatus(correct);
                } else {

                    displayStatus(wrong);
                }
                break;
            case R.id.choice3:
                // i'm lazy, do nothing
                choiceTapped = choice3.getText().toString();
                if(problemSum == Integer.parseInt(choiceTapped)){

                    currentScore++;
                    Log.i("butto1", "you got the right answer");
                    displayStatus(correct);

                } else {

                    displayStatus(wrong);

                }
                break;
            case R.id.choice4:
                choiceTapped = choice4.getText().toString();
                if(problemSum == Integer.parseInt(choiceTapped)){

                    currentScore++;
                    Log.i("butto1", "you got the right answer");
                    displayStatus(correct);

                } else {

                    textViewStatus.setText(wrong);

                }
                break;

            default:
                System.out.println("do nothing");
        }

        totalScore++;

        textViewStatus.setVisibility(View.VISIBLE);

        choicesList.clear();

        generateProblem();
        generateChoices();
        displayScore();

    }

    public void displayScore() {

        textViewScore.setText(currentScore + "/" + totalScore);

    }

    public void displayStatus(String status) {

        textViewStatus.setText(status);

    }

    public void generateProblem () {


        int random;

        random = rand.nextInt(21) + 0;
        firstNum = random;
        random = rand.nextInt(21) + 0;
        secondNum = random;

        problemSum = firstNum + secondNum;

        textViewProblem.setText(firstNum + " + " + secondNum);

    }


    public void generateChoices () {

        randomCounter = rand.nextInt(4) + 0;

        for(int i = 0; i < 4; i++) {

            random = rand.nextInt(41) + 0;


            if(random == pastRandomNumber) {

                i--;

            } else if ( random == problemSum ) {

                flagCount++;

            } else if ( random != problemSum && i == randomCounter && flagCount == 0) {

                random = problemSum;
                flagCount++;

            }

            pastRandomNumber = random;
            choicesList.add(random);


        }

        choice1.setText((choicesList.get(0)).toString());
        choice2.setText((choicesList.get(1)).toString());
        choice3.setText((choicesList.get(2)).toString());
        choice4.setText((choicesList.get(3)).toString());

    }



    public void goClicked( View view ) {


        textViewTimer.setVisibility(View.VISIBLE);
        textViewProblem.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);

        gridLayout.setVisibility(View.VISIBLE);

        goButton.setVisibility(View.INVISIBLE);

        timer();

        generateProblem();
        generateChoices();
        displayScore();
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewProblem = findViewById(R.id.textViewProblem);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStatus = findViewById(R.id.textViewStatus);

        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);

        choicesList = new ArrayList();

        gridLayout = findViewById(R.id.gridLayout);

        textViewTimer.setVisibility(View.INVISIBLE);
        textViewProblem.setVisibility(View.INVISIBLE);
        textViewScore.setVisibility(View.INVISIBLE);
        textViewStatus.setVisibility(View.INVISIBLE);

        gridLayout.setVisibility(View.INVISIBLE);

        goButton.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        rand = new Random();

    }
}
