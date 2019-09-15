package com.example.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    MediaPlayer mPlayer;

    boolean flag = false;
    int cnt = 0;

    public void buttonClicked(View view){


        cnt++;
        int id = view.getId();

        String ourId = "";

        ourId = view.getResources().getResourceEntryName(id);

        int resourceId = getResources().getIdentifier(ourId, "raw", "com.example.basicphrases" );

        mediaPlayer = MediaPlayer.create(this, resourceId);

         if(cnt >= 2){
             mPlayer.stop();
             Log.i("button Clicked", "media should stop");

             mediaPlayer.start();
             mPlayer = mediaPlayer;
             cnt = 1;
         } else if(cnt == 1) {
             mediaPlayer.start();
             mPlayer = mediaPlayer;
             cnt = 1;
         }










        Log.i("button Clicked", "true " + ourId);
        Log.i("button Clicked", "true " + resourceId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
