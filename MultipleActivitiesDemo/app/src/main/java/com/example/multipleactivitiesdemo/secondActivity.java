package com.example.multipleactivitiesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class secondActivity extends AppCompatActivity {


    public void toMainActivity(View view) {

        Intent intent = new Intent(this, MainActivity.class);


        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        Toast.makeText(this, "Hello "+ intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();


    }
}
