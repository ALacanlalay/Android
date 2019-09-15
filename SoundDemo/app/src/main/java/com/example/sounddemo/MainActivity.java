package com.example.sounddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    AudioManager audioManager;

//    final Handler mSeekbarUpdateHandler = new Handler();
//    Runnable mUpdateSeekbar;




    public void play(View view){



        mediaPlayer.start();
  //      mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 1000);

    }

    public void pause(View view){

        mediaPlayer.pause();
       // mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for audio volume control
        mediaPlayer = MediaPlayer.create(this, R.raw.bird); // to get the audio file working

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); // to work with volume in device

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // setting the max volume

        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // setting the current volume

        SeekBar volumeControl = findViewById(R.id.seekBar); //defining volume control

        volumeControl.setMax(maxVolume);

        volumeControl.setProgress(currentVolume);

        //seekbar for volume
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.i("SeekBar value", Integer.toString(i));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        final SeekBar durationControl = findViewById(R.id.seekBarDuration); // defining duration control

        durationControl.setMax(mediaPlayer.getDuration()); // setting the max duration of audio

        // runnable method to get the current position of the seekbar for duration
/*
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                durationControl.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 1000); //delay movement of seekbar for duration
            }
        };

 */

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                durationControl.setProgress(mediaPlayer.getCurrentPosition());
             //   Log.i("info", "runnable is active");


            }
        }, 0, 1000);



        //seekbar for duration control
        durationControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


               // if (b)
                    mediaPlayer.seekTo(i);

                    Log.i("info", Integer.toString(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.start();

            }
        });







    }


}
