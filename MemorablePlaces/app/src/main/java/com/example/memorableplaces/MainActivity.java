package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     static ArrayList<String> places = new ArrayList<String>();

     static ArrayList<LatLng> locations = new ArrayList<LatLng>();

     static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = findViewById(R.id.listView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);



        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();


        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();


        try {

            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));

            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));

            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (IOException e) {

            e.printStackTrace();

        }

        if(places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {

            if(places.size() == latitudes.size() && latitudes.size() == longitudes.size()) {

                for(int i = 0; i < latitudes.size(); i++) {

                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));

                }

            }

        } else {

            places.add("Add a new place");
            //locations.add(new LatLng(0, 0)); //this particular code resets map to (0,0) coordinates whenever i restart my emulator even if i tapped on an existing location and so i have to remove it
        }

        //places.add("Add a new place...");

        //removes duplicate add new places, i dunno why it is happenning in a real device but in an emulator, it does work properly without this code
        for(int i = 1; i < places.size(); i++) {

            if(places.get(i).equals("Add a new place...")) {

                places.remove(i);

            }

        }



        locations.add(new LatLng(0, 0));

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, places);

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                intent.putExtra("placeNumber", position);

                startActivity(intent);

            }
        });


    }
}
