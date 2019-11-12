package com.example.communicatingwithphone;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity_Wearable extends WearableActivity implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String username = "Vin";

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/data");
        putDataMapRequest.getDataMap().putString("username", username);

        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataRequest);



        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }
}
