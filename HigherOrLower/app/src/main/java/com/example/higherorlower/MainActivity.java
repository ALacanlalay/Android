package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Random rand = new Random();
    int random;
    Boolean x = true;


    public void makeToast(String string){

        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    public void guessClicked(View view) {

        EditText guessTextField = findViewById(R.id.guessTextField);

        String userInput = guessTextField.getText().toString();
        int guessInput;

        try{
            x = true;
             guessInput = Integer.parseInt(userInput); //checks if the input is and integer or not


        }catch (NumberFormatException | NullPointerException asd){
            x = false;

            makeToast("That is not a valid input!");
        }

        if(x == true) {
             guessInput = Integer.parseInt(userInput);


            if (guessInput == random) {

                makeToast("Congratulations! You got it right! I'm thinking of a new number now!");
                random = rand.nextInt(3) + 2;//nextInt(max-min + 1) + min


            } else if (guessInput > random) {

                makeToast("Lower!");

            } else if (guessInput < random) {

                makeToast("Higher!");

            }

            Log.i("Random number is", Integer.toString(random));

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = rand.nextInt(3) + 2;
    }
}
