package com.example.peoplecounter;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    int counter = 0;

    public void plusOne(View view) {

        counter ++;
        mTextView.setText(String.valueOf(counter));

    }

    public void reset(View view) {

        counter = 0;
        mTextView.setText(String.valueOf(counter));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }
}
