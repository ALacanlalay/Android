package com.example.workingwithcards;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CardFragment;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        FragmentManager fragmentManager =  getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CardFragment cardFragment = CardFragment.create("Hello Vin!", "How are you today", android.R.drawable.sym_def_app_icon);

        fragmentTransaction.add(R.id.frame, cardFragment);
        fragmentTransaction.commit();

        // Enables Always-on
        setAmbientEnabled();
    }
}
