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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Boolean signUpModeActive = true;

  TextView textViewChangeSignUpMode;

  public void signUp(View view) {

    EditText editTextUsername = findViewById(R.id.editTextUsername);
    EditText editTextPassword = findViewById(R.id.editTextPassword);

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

    textViewChangeSignUpMode = findViewById(R.id.textViewChangeSignUpMode);

    textViewChangeSignUpMode.setOnClickListener(this);

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
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

    }

  }
}