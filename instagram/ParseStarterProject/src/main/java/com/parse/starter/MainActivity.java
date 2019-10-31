/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeActive = true;

  TextView textViewChangeSignUpMode;

  EditText editTextPassword;

  public void showUserList() {

    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(intent);

  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.textViewChangeSignUpMode){

      Button buttonSignUp = findViewById(R.id.buttonSignUp);

      if(signUpModeActive) {

        signUpModeActive = false;
        buttonSignUp.setText("Login");
        textViewChangeSignUpMode.setText("Or, Signup");

      } else {

        signUpModeActive = true;
        buttonSignUp.setText("Signup");
        textViewChangeSignUpMode.setText("Or, Login");

      }

    } else if (v.getId() == R.id.backgroundLayout || v.getId() == R.id.imageView) {


      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);



    }

  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {

    if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

      signUp(v);

    }

    return false;
  }



  public void signUp(View view) {

    EditText editTextUsername = findViewById(R.id.editTextUsername);


    if(editTextUsername.getText().toString().matches("") || editTextPassword.getText().toString().matches("")) {

      Toast.makeText(getApplicationContext(), "Username and Password is required", Toast.LENGTH_SHORT).show();

    } else {

      if(signUpModeActive) {

        ParseUser user = new ParseUser();

        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {

              Log.i("Signup", "Successful");

              showUserList();

            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });
      } else {

        ParseUser.logInInBackground(editTextUsername.getText().toString(), editTextPassword.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if(user != null) {

              Log.i("Signup", "Login successful");

              showUserList();

            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });

      }

    }

  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram");

    textViewChangeSignUpMode = findViewById(R.id.textViewChangeSignUpMode);

    textViewChangeSignUpMode.setOnClickListener(this);

    ConstraintLayout backgroundLayout = findViewById(R.id.backgroundLayout);

    ImageView imageView = findViewById(R.id.imageView);

    backgroundLayout.setOnClickListener(this);

    imageView.setOnClickListener(this);

    editTextPassword = findViewById(R.id.editTextPassword);

    editTextPassword.setOnKeyListener(this);

    if(ParseUser.getCurrentUser() != null) {

      showUserList();

    }

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}