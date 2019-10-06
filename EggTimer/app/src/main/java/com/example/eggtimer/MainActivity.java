package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MediaPlayer mediaPlayer;
    Button go;
    int seekbarTimer;
    int flag = 0;


    public void timeFormatTextView(int countDownTimer){

        textView.setText(""+String.format("%d:%d:%d",
                TimeUnit.MILLISECONDS.toHours(countDownTimer * 1000),
                TimeUnit.MILLISECONDS.toMinutes(countDownTimer * 1000) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countDownTimer * 1000)),
                TimeUnit.MILLISECONDS.toSeconds(countDownTimer * 1000) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countDownTimer * 1000))));

    }

    public void countDownTimerTextView(long timer){

        textView.setText("" + String.format("%d:%d:%d",
                TimeUnit.MILLISECONDS.toHours(timer),
                TimeUnit.MILLISECONDS.toMinutes(timer),
                TimeUnit.MILLISECONDS.toSeconds(timer) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer))));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);

        final SeekBar timerDurationSeekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.airhorn);

        timerDurationSeekBar.setMax(3600);

        timerDurationSeekBar.setProgress(0);

        timeFormatTextView(0);

        timerDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekbarTimer = i;
                timerDurationSeekBar.setProgress(i);

                Log.i("info", String.valueOf(i));

                timeFormatTextView(i);

                Log.i("info", ""+ i);

                System.out.println(TimeUnit.MILLISECONDS.toMinutes(i *1000) + "<-Milli to minutes/ minutes to hours -> " + TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(i*1000)));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        go = findViewById(R.id.button);

        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            flag++;


                 new CountDownTimer(seekbarTimer * 1000, 1000) {

                        public void onTick(long milliSecondsUntilDone) {

                            if(flag == 1) {

                                countDownTimerTextView(milliSecondsUntilDone);

                                timerDurationSeekBar.animate().alpha(0);

                                go.setText("Stop!");
                            } else {

                                cancel();
                                go.setText("Go!");
                                timeFormatTextView(0);
                                flag = 0;
                                timerDurationSeekBar.animate().alpha(1);
                                timerDurationSeekBar.setProgress(0);

                            }

                        }

                        public void onFinish() {

                            Log.i("done!", "Countdown finished");

                            mediaPlayer.start();
                            timerDurationSeekBar.animate().alpha(1);
                            go.setText("Go!");
                            cancel();

                        }

                    }.start();


            }
        });









    }
}
