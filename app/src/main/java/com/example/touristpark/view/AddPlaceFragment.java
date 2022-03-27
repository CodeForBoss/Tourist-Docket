package com.example.touristpark.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.touristpark.databinding.FragmentAddPlaceBinding;

import java.util.List;
import java.util.Locale;

public class AddPlaceFragment extends Fragment implements LocationListener {
    private final int REQUEST_PERMISSION = 103;
    private static final int IMAGE_REQUEST = 1;
    Uri imguri;
    boolean isImageSelected = false;
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
                      openChooseFile();
                }
            });
            binding.okId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkValidation();

                }
            });
    }
    private String getFileExtention(Uri imageUrl){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUrl));
    }
    private void openChooseFile() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode == RESULT_OK && data!=null&& data.getData()!=null) {
            imguri = data.getData();
            Glide.with(this).load(imguri).into(binding.selectImage);
            isImageSelected = true;
        }
    }

    private void checkValidation(){
        if(!isImageSelected){
            Toast.makeText(getContext(), "Please select image!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(binding.descriptionId.getText())){
            binding.descriptionId.setError("This can't be empty");
            return;
        }
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