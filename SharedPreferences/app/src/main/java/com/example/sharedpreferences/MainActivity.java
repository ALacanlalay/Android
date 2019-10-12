package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);

        ArrayList<String> myFriends = new ArrayList<String>();

        myFriends.add("Richmond");

        myFriends.add("Wyn");

        try {

            sharedPreferences.edit().putString("myFriends", ObjectSerializer.serialize(myFriends)).apply();

        } catch (IOException e) {

            e.printStackTrace();

        }

        ArrayList<String> newMyFriends = new ArrayList<String>();

        try {

            newMyFriends = (ArrayList<String>) ObjectSerializer.deserialize((sharedPreferences.getString("myFriends",ObjectSerializer.serialize(new ArrayList<String>()))));


        } catch (IOException e) {

            e.printStackTrace();

        }

        Log.i("newMyFriends", newMyFriends.toString());


        /*
        sharedPreferences.edit().putString("username", "vin").apply();

        String username = sharedPreferences.getString("username", "");

        Log.i("username", username);


 */

    }
}
