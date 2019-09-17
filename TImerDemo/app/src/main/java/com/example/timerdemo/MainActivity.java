package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  /*      final Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this, 1000);

                Log.i("Runnable", "per second");


            }
        };

        handler.post(run);

   */

        new CountDownTimer(10000, 1000){

            public void onTick(long milliSecondsUntilDone) {

                Log.i("Seconds left", String.valueOf(milliSecondsUntilDone));

            }

            public void onFinish() {

                Log.i("done!", "Countdown finished");

            }
        }.start();


    }
}
