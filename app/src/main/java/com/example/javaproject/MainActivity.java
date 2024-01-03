package com.example.javaproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.javaproject.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private final String LOCATION_PERMISSION_APPROX = Manifest.permission.ACCESS_COARSE_LOCATION; // Approximative
    private final String LOCATION_PERMISSION_PRECISE = Manifest.permission.ACCESS_FINE_LOCATION; // Precise

    private final ActivityResultLauncher<String> permissionLauncher =
        registerForActivityResult( new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                step1();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        });

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.locateMeButton.setOnClickListener(c -> step1());

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Get current location with network
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this::getCityFromLocation, getMainLooper());

        // Get current location with gps
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this::getCityFromLocation, getMainLooper());

    }


    /***
     * STARTING BLOC TO LOCATE ME METHOD
     */

    private void step1() {

        // First check the location permissions
        if(checkPermission(LOCATION_PERMISSION_APPROX)) {
            permissionLauncher.launch(LOCATION_PERMISSION_APPROX);
        }
        else if(checkPermission(LOCATION_PERMISSION_PRECISE)) {
            permissionLauncher.launch(LOCATION_PERMISSION_PRECISE);
        }
        else {
            step2();
        }

    }

    @SuppressLint("MissingPermission") // Useless checking permission for localization
    private void step2() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if ( isGpsEnable || isNetworkEnable) {
            Location location = getMoreAccurateLocation(locationManager);

            if (location != null) {
                getCityFromLocation(location);
            } else {
                Toast.makeText(this, "Impossible de localiser", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("MissingPermission") // Useless checking permission for localization
    private Location getMoreAccurateLocation(LocationManager locationManager) {

        AtomicReference<Location> finalLocation = new AtomicReference<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            finalLocation.set(locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER));
        }
        else {

            // Get current location with network
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, finalLocation::set, getMainLooper());

            // Get current location with gps
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, location -> {
                Location tempLocation = finalLocation.get();
                if (tempLocation == null || (location.getAccuracy() > tempLocation.getAccuracy())) {
                    finalLocation.set(location);
                }
            }, getMainLooper());
        }

        return finalLocation.get();
    }

    private void getCityFromLocation(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 10);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (addressList != null && !addressList.isEmpty()) {
            Toast.makeText(this, String.valueOf(addressList.get(0).getLocality()), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

    /***
     * ENDING BLOC TO LOCATE ME METHOD
     */


    /**
     * CHECK IF CITY IS VALID OR NOT BY COUNTRY
     */

    private void checkIfCityExist() {
        final String city = "Reims";
        final String wrongCity = "Rins";
        Geocoder geocoder = new Geocoder(this, Locale.FRANCE);

        try {
            List<Address> addressList = geocoder.getFromLocationName(city, 1);

            if (addressList != null && !addressList.isEmpty()) {
                Toast.makeText(this, addressList.get(0).getLocality(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}