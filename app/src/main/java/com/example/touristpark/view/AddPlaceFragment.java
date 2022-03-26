package com.example.touristpark.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.touristpark.databinding.FragmentAddPlaceBinding;

import java.util.List;
import java.util.Locale;

public class AddPlaceFragment extends Fragment implements LocationListener {
    private final int REQUEST_PERMISSION = 103;
    private FragmentAddPlaceBinding binding;
    private LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPlaceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getContext()!=null){
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        }
        checkPermission();
        listeners();
    }

    private void listeners(){
            binding.selectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
    }


    private void checkPermission(){
        if(getActivity() !=null){
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);
            } else {
               getLocation();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length!=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }

    public void getLocation() {
        try {
           if(getContext()!=null){
               locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
               if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                   return;
               }
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
           }

        }catch (Exception e){
             e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
           try {
               Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
               List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
               String address = addresses.get(0).getAddressLine(0);
               binding.progressId.setVisibility(View.GONE);
               binding.locationId.setText(address);
               binding.locationId.setVisibility(View.VISIBLE);
               binding.okId.setVisibility(View.VISIBLE);
               Log.d("anr", "onLocationChanged: called");
           }catch (Exception e){
               e.printStackTrace();
           }
    }



    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


}