package com.example.hideorshowui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button showButton;
    Button hideButton;

    TextView textView;

    public void showClicked(View view) {


        textView.setVisibility(View.VISIBLE);


    }


    public void hideClicked(View view) {


        textView.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showButton = findViewById(R.id.showButton);
        hideButton = findViewById(R.id.hideButton);
        textView = findViewById(R.id.textView);
    }
}
