package com.example.whatstheweather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView1;
    TextView textView2;
    String userInput;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";

            URL url;

            HttpURLConnection urlConnection = null;


            try {

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

                Log.i("Error doInBackground", "Failed!");

            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {

                try {

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");

                    JSONObject obj = new JSONObject(result);
                    String temperatureInfo = obj.getString("main");

                    String tempInKelvin = null;

                    Log.i("Info", temperatureInfo);

                    String [] splitResult = temperatureInfo.split("humidity");

                    Pattern p = Pattern.compile("\"temp\":(.*?),");
                    Matcher m = p.matcher(splitResult[0]);

                    while(m.find()){

                        Log.i("Info", m.group(1));

                        tempInKelvin = String.valueOf(m.group(1));

                    }

                    Log.i("TempInKelvin", tempInKelvin);

                    double tempInCelsius = Double.parseDouble(tempInKelvin) - 273.15;

                    DecimalFormat df = new DecimalFormat("#.00");

                    JSONArray arrWeatherInfo = new JSONArray(weatherInfo);

                    for(int i = 0; i < arrWeatherInfo.length(); i++) {

                    JSONObject jsonPartWeatherInfo = arrWeatherInfo.getJSONObject(i);

                    Log.i("Id", String.valueOf(jsonPartWeatherInfo.getInt("id")));


                    if(jsonPartWeatherInfo.getString("main").equals("") && jsonPartWeatherInfo.getString("description").equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Can't find weather!", Toast.LENGTH_LONG).show();

                    } else {

                        textView1.setText("Weather Condition: " + jsonPartWeatherInfo.getString("main") + "\n" + "Description: " + jsonPartWeatherInfo.getString("description") + "\n" + "Temperature: " + df.format(tempInCelsius) + " °C");
                        //textView2.setText("Description: " + jsonPartWeatherInfo.getString("description"));

                    }


                }




            } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Can't find weather!", Toast.LENGTH_LONG).show();
                    Log.i("Error onPostExecute", "Failed!");

                    e.printStackTrace();

                    textView1.setText("");
                    textView2.setText("");

            }

        }



    }

    public void buttonClicked(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);


                String result = "";

                try {
                   // String encodedUserInput = URLEncoder.encode(editText.getText().toString(), "UTF-8"); // Just in case spaces cannot be read by the app. use this.

                    // put it in task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + encodedUserInput + "&appid=72a39750eafd190cc37845297a7fac23").get(); <--- Just like this line

                    userInput = editText.getText().toString();
                    Log.i("info", userInput);

                    DownloadTask task = new DownloadTask();

                    result = task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + userInput + "&appid=72a39750eafd190cc37845297a7fac23").get();

                    Log.i("Web Content", result);


                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), "Can't find weather!", Toast.LENGTH_LONG).show();
                    Log.i("Error buttonClicked", "Failed!");
                //    textView1.setText("");
                 //   textView2.setText("");

                }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        editText = findViewById(R.id.editText);




    }
}
