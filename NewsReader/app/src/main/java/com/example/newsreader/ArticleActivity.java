package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static java.sql.DriverManager.println;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();

        String htmlFromMainActivity = intent.getStringExtra("content");

        Log.i("html", htmlFromMainActivity);

        //webView.loadData(intent.getStringExtra("content"), "text/html", "UTF-8"); //this format is not working...

        //webView.loadDataWithBaseURL( "", htmlFromMainActivity, "text/html", "UTF-8", "" ); //this works

        String urlLoad = intent.getStringExtra("content");

        webView.loadUrl(urlLoad);


    }
}
