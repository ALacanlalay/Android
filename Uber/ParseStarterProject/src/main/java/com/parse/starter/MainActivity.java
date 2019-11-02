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
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity {

  public void redirectActivity() {

    if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {

      Intent intent = new Intent(getApplicationContext(),RiderActivity.class);

      startActivity(intent);

    }

    Log.i("User", String.valueOf(ParseUser.getCurrentUser().get("riderOrDriver")));

  }


  public void getStarted(View view) {

    Switch userSwitchType = findViewById(R.id.userTypeSwitch);

    Log.i("Switch Value", String.valueOf(userSwitchType.isChecked()));

    String userType = "rider";

    if(userSwitchType.isChecked()) {

      userType = "driver";
    }

    ParseUser.getCurrentUser().put("riderOrDriver", userType );

    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if(e == null) {

          redirectActivity();

        }

      }
    });

    Log.i("info", "Redirecting as " + userType);

    //redirectActivity();


  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportActionBar().hide();

    if(ParseUser.getCurrentUser() == null) {

      ParseAnonymousUtils.logIn(new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {

          if(e == null) {

            Log.i("Info", "Anonymous Login Successful!");

          } else {

            Log.i("Info", "Anonymous Login Failed!");

          }

        }
      });

    } else {

      if(ParseUser.getCurrentUser().get("riderOrDriver") != null) {

        Log.i("info", "Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"));

        redirectActivity();

      }
      Log.i("Info", "You're already logged in!");

    }
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}