package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebrityURLs = new ArrayList<String>();
    ArrayList<String> celebrityNames = new ArrayList<String>();
    int chosenCelebrity = 0;

    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];

    ImageView imageView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;


    public void celebChosen(View view) {

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {

            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Wrong! It was " + celebrityNames.get(chosenCelebrity), Toast.LENGTH_SHORT).show();

        }

        createNewQuestion();

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }


            return null;
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected  String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while(data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            } catch (Exception e) {

                Log.i("Error Downloadtask" , "Exception!");
                e.printStackTrace();
                return "failed";

            }

           // return null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        imageView = findViewById(R.id.imageView);

        button0 = findViewById(R.id.button1);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        DownloadTask task = new DownloadTask();

        String result = null;

        try {

            result = task.execute("http://www.posh24.se/kandisar").get();
            Log.i("URL contents" , " " + result);

            String [] splitResult = result.split("<div class =\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while(m.find()){

               celebrityURLs.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while(m.find()){

                celebrityNames.add(m.group(1));
            }




        } catch (InterruptedException e) {

            Log.i("Error" , "Interrupted exception!");

        } catch (ExecutionException e) {

            Log.i("Error" , "Execution Exception!");

        }

        createNewQuestion();


    }

    public void createNewQuestion() {

        Random random = new Random();
        chosenCelebrity = random.nextInt(celebrityURLs.size());

        ImageDownloader imageTask = new ImageDownloader();

        Bitmap celebrityImage;

        try {
            celebrityImage = imageTask.execute(celebrityURLs.get(chosenCelebrity)).get();

            imageView.setImageBitmap(celebrityImage);

            locationOfCorrectAnswer = random.nextInt(4);

            int incorrectAnswerLocation;

            for(int i = 0; i<4; i++) {

                if(i == locationOfCorrectAnswer) {

                    answers[i] = celebrityNames.get(chosenCelebrity);

                } else {

                    incorrectAnswerLocation = random.nextInt(celebrityURLs.size());

                    while(incorrectAnswerLocation == chosenCelebrity) {

                        incorrectAnswerLocation = random.nextInt(celebrityURLs.size());

                    }
                    answers[i] = celebrityNames.get(incorrectAnswerLocation);

                }

            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
