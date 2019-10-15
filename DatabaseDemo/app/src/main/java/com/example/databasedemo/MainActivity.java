package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            SQLiteDatabase friendsDB = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            //friendsDB.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INTEGER(3), id INTEGER NOT NULL PRIMARY KEY)");

            //friendsDB.execSQL("INSERT INTO newusers (name, age) VALUES ('Vin', 23)");

            //friendsDB.execSQL("INSERT INTO newusers (name, age) VALUES ('Wyn', 22)");

            friendsDB.execSQL("DELETE FROM newUsers Where id = 1");

            Cursor c = friendsDB.rawQuery("SELECT * FROM newusers", null);

            int nameIndex = c.getColumnIndex("name");

            int ageIndex = c.getColumnIndex("age");

            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();

            while(c != null) {

                Log.i("UserResults - Name", c.getString(nameIndex));

                Log.i("UserResults - age", String.valueOf(c.getInt(ageIndex)));

                Log.i("UserResults - Id", String.valueOf(c.getInt(idIndex)));

                c.moveToNext();

            }




        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
