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
import java.util.*;

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
    int flagCount = 0;
    int currentScore = 0;
    int totalScore = 0;
    int randomCounter = 0;

    int randomDuplicate = 0;

    ArrayList choicesList;

    String choiceTapped;
    String yourScoreIs = "Your score is: ";
    String correct = "Correct!";
    String wrong = "Wrong!";


    public void timer() {

        new CountDownTimer(30000+100, 1000) {

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

        //generate random numbers for choices including one correct answer
        for (int i = 0; i < 4; i++) {

            random = rand.nextInt(41) + 0;

            if (random == problemSum) {

                flagCount++;

            } else if (random != problemSum && flagCount == 0 && i == randomCounter) {

                random = problemSum;
                flagCount++;

            } else if (random == problemSum && flagCount == 1) {

                while (random == problemSum) {

                    random = rand.nextInt(41) + 0;

                }

            }


            choicesList.add(random);
        }

        //delete duplicate choices
        for(int i = 0; i < 4; i++) {

            for( int j = i+1; j < 4; j++) {

                if(Integer.parseInt(choicesList.get(i).toString()) == Integer.parseInt(choicesList.get(j).toString())) {


                    randomDuplicate = Integer.parseInt(choicesList.get(j).toString());
                    while(randomDuplicate == Integer.parseInt(choicesList.get(i).toString())){

                        random = rand.nextInt(41) + 0;
                        randomDuplicate = random;

                        choicesList.remove(j);
                        choicesList.add(random);

                    }


                } else {

                    //do nothing

                }

            }

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

    public void choicesClicked(View view) {

        flagCount = 0;

        String cnt = (view.getTag()).toString();
        int buttonTag = Integer.parseInt(cnt);

        Log.i("info", "button tage: " + cnt);

/*
        String resourceName = view.getResources().getResourceName(view.getId());

        System.out.println(resourceName);

 */



/*        switch (view.getId()) {
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

 */

        if(buttonTag == 0) {

            choiceTapped = choice1.getText().toString();
            if(problemSum == Integer.parseInt(choiceTapped)){

                currentScore++;
                Log.i("butto1", "you got the right answer");
                displayStatus(correct);

            } else {


                displayStatus(wrong);
            }

        } else if (buttonTag == 1) {

            choiceTapped = choice2.getText().toString();
            if(problemSum == Integer.parseInt(choiceTapped)){

                currentScore++;
                Log.i("butto1", "you got the right answer");
                displayStatus(correct);

            } else {


                displayStatus(wrong);
            }

        } else if (buttonTag == 2) {

            choiceTapped = choice3.getText().toString();
            if(problemSum == Integer.parseInt(choiceTapped)){

                currentScore++;
                Log.i("butto1", "you got the right answer");
                displayStatus(correct);

            } else {


                displayStatus(wrong);
            }

        } else if (buttonTag == 3) {

            choiceTapped = choice4.getText().toString();
            if(problemSum == Integer.parseInt(choiceTapped)){

                currentScore++;
                Log.i("butto1", "you got the right answer");
                displayStatus(correct);

            } else {


                displayStatus(wrong);
            }

        }

        totalScore++;

        textViewStatus.setVisibility(View.VISIBLE);

        displayScore();

        choicesList.clear();
        generateProblem();
        generateChoices();


    }

    public void playAgain(View view) {

        currentScore = 0;
        totalScore = 0;

        flagCount = 0;

        choicesList.clear();

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
