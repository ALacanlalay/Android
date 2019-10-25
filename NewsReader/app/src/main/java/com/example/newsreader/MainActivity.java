package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<String>();

    ArrayList<String> content = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    SQLiteDatabase articlesDB;

    public void updateListView() {

        Cursor c = articlesDB.rawQuery("SELECT * FROM articles", null);

        int contentIndex = c.getColumnIndex("content");

        int titleIndex = c.getColumnIndex("title");


        if(c.moveToFirst()) {

            titles.clear();

            content.clear();

            do {

                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));


            } while (c.moveToNext());

        }

        Log.i("contentIndex", String.valueOf(contentIndex));


            arrayAdapter.notifyDataSetChanged();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);

                intent.putExtra("content", content.get(position));

                startActivity(intent);

            }
        });

        articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);

        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleId INTEGER, title VARCHAR, content VARCHAR)");

        updateListView();

        DownloadTask task = new DownloadTask();

        try {

            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty"); // this url has the IDs of all the topstories/news in the site api

        } catch (Exception e) {

            e.printStackTrace();

        }

    }




    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            URL url;

            HttpURLConnection urlConnection = null;
            //getting the IDs of each news article

            try {

                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while(data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                Log.i("URL content", result);

                //fix the position of the IDs per line

                JSONArray jsonArray = new JSONArray(result);

                int numberOfItems = 20;

                if(jsonArray.length() < 20) {

                    numberOfItems = jsonArray.length();

                }

                articlesDB.execSQL("DELETE FROM articles");

                for(int i = 0; i < numberOfItems; i++) {

                    String articleId = jsonArray.getString(i);

                    Log.i("JSON item", jsonArray.getString(i) + " " + numberOfItems);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty"); // this site views the info about the site with the corresponding IDs

                    urlConnection = (HttpURLConnection) url.openConnection();

                    inputStream = urlConnection.getInputStream();

                    reader = new InputStreamReader(inputStream);

                    data = reader.read();

                    String articleInfo = "";

                    while(data != -1) {

                        char current = (char) data;

                        articleInfo += current;

                        data = reader.read();

                    }

                    Log.i("ArticleInfo", articleInfo); //this shows what the url has inside in json format... including the site/url of the news itself

                    JSONObject jsonObject = new JSONObject(articleInfo);

                    if(!jsonObject.isNull("title") && !jsonObject.isNull("url")) {

                        String articleTitle = jsonObject.getString("title");
                        String articleURL = jsonObject.getString("url");

                        Log.i("articleTitleURL", articleTitle + articleURL);

                        url = new URL(articleURL);

                        urlConnection = (HttpURLConnection) url.openConnection();

                        inputStream = urlConnection.getInputStream();

                        reader = new InputStreamReader(inputStream);

                        data = reader.read();

                        String articleContent = "";

                        while(data != -1) {

                            char current = (char) data;

                            articleContent += current; // in the tutorial, this was articleInfo. which is, based on my testing, is incorrect so i have to change it!

                            data = reader.read();

                        }

                        Log.i("articleContent", articleContent);

                        String sql = "INSERT INTO articles (articleId, title, content) VALUES (? , ? , ?)";

                        SQLiteStatement statement = articlesDB.compileStatement(sql);

                        statement.bindString(1, articleId);

                        statement.bindString(2, articleTitle);

                        statement.bindString(3, articleURL); // this is supposed to be articleContent but from my understanding, the articleContent doesn't have any value so it means it's null.
                        //after a couple of experiments, it doesn't really return anything. Instead of articleContent, i changed it to articleURL because that's where the content is therefore it's the one to be inserted into the database!!

                        statement.execute();

                    }


                }



            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            updateListView();

        }

    }

}
