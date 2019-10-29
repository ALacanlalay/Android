/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      query.whereGreaterThan("score", 200);

      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              if (e == null && objects != null) {

                  for (ParseObject object : objects) {

                      object.put("score", object.getInt("score") + 50);
                      object.saveInBackground();

                  }

              }

          }
      });
























      /*

      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      query.whereEqualTo("username", "tommy");
      query.setLimit(1);

      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              if (e == null) {

                  Log.i("findInBackground", "Retrieved " + objects.size() + " objects");

                  if (objects.size() > 0) {

                      for (ParseObject object : objects) {

                          Log.i("findInBackgroundResult", Integer.toString(object.getInt("score")));

                      }

                  }

              }

          }
      });

      */

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}
