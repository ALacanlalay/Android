package com.example.squareorround;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    private int chinsize;

    String watchShape = null;

    public void showWatchShape(View view) {

        if(watchShape.equals("Circle Layout")) {

            Toast.makeText(this, "Circle Layout", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Square Layout", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View container = findViewById(R.id.frame);

        container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                chinsize = insets.getSystemWindowInsetBottom();
                v.onApplyWindowInsets(insets);
                Log.i("insets", String.valueOf(insets));

                if(insets.isRound()) {

                    watchShape = "Circle Layout";

                } else {

                    watchShape = "Square Layout";

                }

                Log.i("View", String.valueOf(v));
                return insets;
            }
        });

        Log.i("container", String.valueOf(container));

        watchShape = String.valueOf(container);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }
}
