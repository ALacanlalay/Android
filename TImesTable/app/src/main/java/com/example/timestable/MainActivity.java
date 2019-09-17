package com.example.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public void generateMultiplier(int multipleOf){

        ArrayList<String> multiplier = new ArrayList<>();

        for(int i = 1; i <= 10; i++){

            multiplier.add(Integer.toString(i * multipleOf));

        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, multiplier);

        listView.setAdapter(arrayAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        final SeekBar multiplierControl = findViewById(R.id.multiplierControl);

        multiplierControl.setMax(20);

        multiplierControl.setProgress(1);

        multiplierControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                int min = 1;

                if(i < min){
                    i = min;
                    multiplierControl.setProgress(i);
                } else {

                    generateMultiplier(i);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateMultiplier(1);

    }
}
