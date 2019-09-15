package com.example.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    public void buttonClicked(View view){

        EditText editText = findViewById(R.id.editText);

        Log.i("info", editText.getText().toString());

        Toast.makeText(MainActivity.this, "Hi there, " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
        
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
