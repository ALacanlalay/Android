package com.example.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImage;

    public void downloadImage(View view) {

//https://www.clipartwiki.com/iclip/Tximb_hand-images-free-clip-art-clipart-image-hand/

        ImageDownloader task = new ImageDownloader();

        Bitmap myImage;

        try {

            myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/SpongeBob_SquarePants_character.svg/1200px-SpongeBob_SquarePants_character.svg.png").get();

            downloadedImage.setImageBitmap(myImage);


        } catch(Exception e) {

            e.printStackTrace();

        }
        Log.i("info", "Button clicked");

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {



        @Override
        protected Bitmap doInBackground(String... urls) {
            Log.i("info", "i've been here");
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return  myBitmap;


            } catch(MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImage = findViewById(R.id.imageView);


    }
}
