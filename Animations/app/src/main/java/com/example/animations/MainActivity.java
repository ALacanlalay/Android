package com.example.animations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    public void fade(View view){

            ImageView tom = findViewById(R.id.tom);// tom fades



          //  tom.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000); // tom fades out

        //tom.animate().rotation(-3600f).translationX(-2000f).scaleX(1f).scaleY(1f).setDuration(2000);

                tom.animate().alpha(1f).rotation(-3600).translationX(0).scaleX(0.1f).scaleY(0.1f).setDuration(2000);








    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView tom = findViewById(R.id.tom);// tom fades


        tom.animate().alpha(0f).scaleY(1f).scaleY(1f).translationX(500f).setDuration(0);


    }
}
