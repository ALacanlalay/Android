package com.parse.starter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.graphics.Matrix;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class UserFeedActivity extends AppCompatActivity {

    private static int fixOrientation(Bitmap bitmap) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            return 90;
        }
        return 0;
    }


    public static Bitmap flipIMage(Bitmap bitmap) {
        //Moustafa: fix issue of image reflection due to front camera settings
        Matrix matrix = new Matrix();
        int rotation = fixOrientation(bitmap);
        matrix.postRotate(rotation);
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
/*
        ImageView imageView = new ImageView(getApplicationContext());

        imageView.setLayoutParams(new ViewGroup.LayoutParams(

                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT


        ));

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.instagramlogo));

        linearLayout.addView(imageView);

 */

        Intent intent = getIntent();

        String activeUsername = intent.getStringExtra("username");

        setTitle(activeUsername + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", activeUsername);

        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null) {

                    if(objects.size() > 0) {

                        for(final ParseObject object : objects) {

                            final ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if(e == null && data != null) {

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        fixOrientation(bitmap);

                                        flipIMage(bitmap);

                                        int x = bitmap.getWidth();
                                        int y = bitmap.getHeight();

                                        System.out.println("Width = " + x + " Height = " + y);

                                        if(x > 4096 || y > 4096) {

                                            x = x / 2;
                                            y = y / 2;

                                        }

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                MATCH_PARENT,
                                                WRAP_CONTENT
                                        ));

                                        //imageView.setImageBitmap(bitmap);

                                        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, x, y, false));

                                        linearLayout.setVerticalScrollBarEnabled(true);

                                        linearLayout.addView(imageView);


                                    }


                                }
                            });

                        }

                    } else {

                        Toast.makeText(UserFeedActivity.this, "The user does not have uploaded pictures!", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    e.printStackTrace();

                }

            }
        });

    }



}
