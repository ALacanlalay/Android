package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                try {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    centerMapOnLocation(lastKnownLocation, "Your Location");
                } catch (Exception e){

                    e.printStackTrace();

                }


            }

        }
    }

    public void centerMapOnLocation(Location location, String title) {

        //mMap.clear();
        try {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());


            if (title != "Your Location") {

                mMap.addMarker(new MarkerOptions().position(userLocation).title(title));

            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);


        Intent intent = getIntent();


        //checking the tapped list from MainActivity's listview
        if (intent.getIntExtra("placeNumber", 0) == 0) {

            //zoom to users location

            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    centerMapOnLocation(location, "Your Location");

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (Build.VERSION.SDK_INT < 23) { // check if device API level is below 23

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                } else {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }

            } else { //proceed here if device API level is 23 above

                //checks permission to access location from user if it is granted
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    try {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        centerMapOnLocation(lastKnownLocation, "Your Location");
                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } else {//if permission not granted, request permission from user. it will then have a request code which will be checked onRequestPermission method

                    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }

            }

        } else {

            //initialize place location
            try {

            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);


                placeLocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).latitude);
                placeLocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).longitude);

                centerMapOnLocation(placeLocation, MainActivity.places.get(intent.getIntExtra("placeNumber", 0)));

                mMap.addMarker(new MarkerOptions().position(MainActivity.locations.get(intent.getIntExtra("placeNumber", 0))).title(MainActivity.places.get(intent.getIntExtra("placeNumber", 0))));

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        //prevents camera from moving towards a tapped marker

        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    boolean doNotMoveCameraToCenterMarker = true;
                    public boolean onMarkerClick(Marker marker) {
                        //Do whatever you need to do here ....
                        marker.showInfoWindow(); // displays information about the tapped marker

                        return doNotMoveCameraToCenterMarker;
                    }

                });

    }

    //allows placing marker after long click
    @Override
    public void onMapLongClick(LatLng latLng) {

        //getting address of a marker
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String address = "";

        try {

            List<Address> listAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if(listAddress != null && listAddress.size() > 0) {

                if(listAddress.get(0).getThoroughfare() != null) {

                    if(listAddress.get(0).getSubThoroughfare() != null) {

                        address += listAddress.get(0).getSubThoroughfare() + " ";

                    }

                    address += listAddress.get(0).getThoroughfare();

                }

            }


        } catch (IOException e) {

            e.printStackTrace();

        }

        if(address == "") {

            SimpleDateFormat sdf = new SimpleDateFormat("mm:HH yyyMMdd");

            address = sdf.format(new Date());

        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

        //saving places/address and locations/coordinates to the arraylist on Main Activity
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);

        //updates the listview....
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);

        try {

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.places)).apply();

            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();

            for(LatLng coordinates : MainActivity.locations) {

                latitudes.add(Double.toHexString(coordinates.latitude));
                longitudes.add(Double.toString(coordinates.longitude));

            }

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("latitudes", ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("longitudes", ObjectSerializer.serialize(longitudes)).apply();



        } catch (IOException e) {

            e.printStackTrace();

        }

        Toast.makeText(getApplicationContext(), "Location saved", Toast.LENGTH_SHORT).show();

    }
}
