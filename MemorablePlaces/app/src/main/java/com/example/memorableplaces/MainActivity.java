package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

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

        places.add("Add a new place...");

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
