/*
Create an app where the user will input a number
and the app will tell if it is a triangular number,
a square number, or both
 */

package com.example.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int userInput;



    public void makeToast(String string){

        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();

    }

    class Shape{

        int number;
        boolean isTriangleTrue = false;
        boolean isSquareTrue = false;

        public boolean isTriangleNumber(){
            number = 1;
            for(int i = 1; i <= userInput; i++ ){
                if(number == userInput){
                    isTriangleTrue = true;
                }


                number = number + (i+1);

            }
            Log.i("Triangle Number", "ValueT: " + isTriangleTrue);
            return isTriangleTrue;

        }


        public boolean isSquareNumber(){
            number = 0;;
            for(int i = 0; i <= userInput; i++){
                number = i * i;
                if(number == userInput){
                    isSquareTrue = true;

                }


            }
            Log.i("Square Number", "ValueS: " + isSquareTrue);
            return isSquareTrue;
        }

    }




    public void identifyClicked(View view){

        EditText editText = findViewById(R.id.editText);
        String userInputString = editText.getText().toString();

        if(!userInputString.isEmpty()){


            try{
                userInput = Integer.parseInt(userInputString);

                Shape num = new Shape();

                if(num.isTriangleNumber()  && !num.isSquareNumber()){
                    makeToast(userInput + " is a Triangle number!");
                    Log.i("info", "Your number is a Triangle number!");
                } else if(!num.isTriangleNumber() && num.isSquareNumber()){
                    makeToast(userInput + " is a Square number");
                    Log.i("info", "Your number is a Square number!");
                } else if(num.isTriangleNumber() && num.isSquareNumber()){
                    makeToast(userInput + " is both Triangle and Square number!");
                    Log.i("info", "Your number is both a Triangle number and Square number!");
                } else{
                    makeToast(userInput + " is neither Triangle nor Square number!");
                    Log.i("info", "Your number is neither Triangle nor Square number!");
                }

            }catch (NumberFormatException | NullPointerException e){

                makeToast("This is not a valid input!");
            }




        } else{
            makeToast("Please enter a number!");
        }
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
