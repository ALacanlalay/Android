package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    int flag = 0;

    //0 = yellow, 1 = red

    int activePlayer = 0;

    // 2 means unslotted

    int[] gameState = {2,2,2,2,2,2,2,2,2};


    int[][] winningState = {
//horizontal
            {0,1,2},
            {3,4,5},
            {6,7,8},
//vertical
            {0,3,6},
            {1,4,7},
            {2,5,8},
//diagonal
            {0,4,8},
            {2,4,6},


    };




    public void dropIn(View view){

       ImageView cnt = (ImageView) view;

       flag++;

        System.out.println(cnt.getTag().toString());

        int tappedCounter = Integer.parseInt(cnt.getTag().toString());

        Log.i("info", "GameState: " + gameState[tappedCounter]);

        if(gameState[tappedCounter] == 2) {

            gameState[tappedCounter] = activePlayer;
            cnt.setTranslationY(-1000f);

            Log.i("info", "ActivePlayer: " + gameState[tappedCounter] + " " + tappedCounter);


            if (activePlayer == 0) {


                cnt.setImageResource(R.drawable.yellow);

                Log.i("info", "ActivePlayerYellow: " + activePlayer);

                activePlayer = 1;


            } else {

                cnt.setImageResource(R.drawable.red);

                Log.i("info", "ActivePlayerRed: " + activePlayer);
                activePlayer = 0;

            }

                cnt.animate().translationY(0f).setDuration(300);

        }

        for(int[] winningState : winningState){

         //   Log.i("info", "activePlayerWinningState: "+ activePlayer);

            if(gameState[winningState[0]] == gameState[winningState[1]] &&
                    gameState[winningState[1]] == gameState[winningState[2]] &&
                    gameState[winningState[2]] != 2){
                Log.i("info", "activePlayerWinningState: "+ gameState[winningState[0]] + " " + gameState[winningState[1]] + " " +gameState[winningState[2]]);

                //someone has one!

                String winner;

                if(gameState[winningState[0]] == 0){

                    Log.i("info", "Winner! "+ gameState[winningState[0]]);

                        winner = "Yellow";

                    for(int i = 0; i < gameState.length; i++){

                        gameState[i] = 0;
                    }

                } else {
                    Log.i("info", "Winner! "+ gameState[winningState[0]]);
                        winner = "Red";

                    for(int i = 0; i < gameState.length; i++){

                        gameState[i] = 1;
                    }
                }



                TextView textView = findViewById(R.id.textView);

                textView.setText( winner + " wins");

                LinearLayout layout = findViewById(R.id.playAgainLayout);

                layout.setVisibility(View.VISIBLE);
                flag = 0;

            }

            if(flag == 9){

                Toast.makeText(MainActivity.this, "Reset the game!", Toast.LENGTH_LONG).show();

                androidx.gridlayout.widget.GridLayout gridLayout=findViewById(R.id.gridLayout);
                for(int i = 0; i < gridLayout.getChildCount(); i++) {

                    ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

                }

                for(int i = 0; i < gameState.length; i++){

                    gameState[i] = 2;
                }

                activePlayer = 0;
                flag = 0;
            }
        }


    }


    public void playAgain(View view){




            LinearLayout layout = findViewById(R.id.playAgainLayout);
            layout.setVisibility(View.INVISIBLE);

            androidx.gridlayout.widget.GridLayout gridLayout=findViewById(R.id.gridLayout);


            for(int i = 0; i < gridLayout.getChildCount(); i++) {

                ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

            }

            for(int i = 0; i < gameState.length; i++){

                gameState[i] = 2;
            }

            activePlayer = 0;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
