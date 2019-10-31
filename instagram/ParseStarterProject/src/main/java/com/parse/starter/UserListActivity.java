package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    Intent intent;

    public void checkPermission() {

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            getPhoto();

        }

    }


    public void getPhoto() {

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                getPhoto();

            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.share) {

            if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){

                checkPermission();

            } else {

                getPhoto();
            }

        } else if(item.getItemId() == R.id.logout) {

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {


                //for api lvl below 29
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                /* for api lvl 29
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), selectedImage);
                Bitmap bitmap = ImageDecoder.decodeBitmap(source);




                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);

                 */

                Log.i("Photo", "Received");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                Log.i("Photo", "Successfully compressed");

                byte[] byteArray = stream.toByteArray();

                Log.i("Photo", "Photo Converting to byteArray");

                ParseFile file = new ParseFile("Image.jpg", byteArray);

                Log.i("Photo", "initialized parse file");

                ParseObject object = new ParseObject("Image");

                Log.i("Photo", "initialized parse object");

                object.put("image", file);

                Log.i("Photo", "photo sent to parse server");

                object.put("username", ParseUser.getCurrentUser().getUsername());

                Log.i("Photo", "username sent accordingly with his/her uploaded photo");

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null) {

                            Toast.makeText(UserListActivity.this, "Image shared!", Toast.LENGTH_SHORT).show();

                            Log.i("Done", "Image Shared");

                        } else {

                            Toast.makeText(UserListActivity.this, "Image could not be shared! - Please try again later!", Toast.LENGTH_SHORT).show();

                            Log.i("Done", "Image not Shared");


                        }

                        Log.i("Photo", "successfully shared!");

                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Image could not be shared!- please try again later!", Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Feed");

        final ArrayList<String> usernames = new ArrayList<>();

        final ListView listViewUser = findViewById(R.id.listViewUser);

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent  = new Intent(getApplicationContext(), UserFeedActivity.class);

                intent.putExtra("username", usernames.get(position));

                startActivity(intent);


            }
        });

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);

        listViewUser.setAdapter((arrayAdapter));

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e == null) {

                    if(objects.size() > 0) {

                        for(ParseUser user : objects) {

                            usernames.add(user.getUsername());

                        }

                        listViewUser.setAdapter(arrayAdapter);

                    }

                } else {

                    e.printStackTrace();

                }

            }
        });


    }
}
