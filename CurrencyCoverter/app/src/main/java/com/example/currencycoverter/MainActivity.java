package com.example.currencycoverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public void converClicked(View view){

        EditText currencyTextField = findViewById(R.id.currencyTextField);


        double userInput = Double.parseDouble(currencyTextField.getText().toString());
        double philippinePeso = 52.14;

        double total = userInput * philippinePeso;


        Toast.makeText(MainActivity.this, "₱" + String.format("%.2f", total), Toast.LENGTH_LONG).show();


        Log.i("info", "The user input is " + currencyTextField.getText().toString());
        Log.i("info", "The converted amount is " + String.format("%.2f", total));




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
