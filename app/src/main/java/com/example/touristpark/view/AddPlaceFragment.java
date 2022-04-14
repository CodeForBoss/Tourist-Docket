package com.example.touristpark.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentAddPlaceBinding;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.viewmodel.TouristParkViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddPlaceFragment extends Fragment implements LocationListener {
    private final int REQUEST_PERMISSION = 104;
    private static final int IMAGE_REQUEST = 1;
    ArrayList<Uri> imageUri = new ArrayList<>();
    boolean isImageSelected = false;
    private FragmentAddPlaceBinding binding;
    private LocationManager locationManager;
    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private TouristParkViewModel touristParkViewModel;
    private User user = new User();
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPlaceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        if(getContext()!=null){
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        }
        listeners();
        checkBundle();
        checkPermission();
    }

    private void checkBundle() {
        bundle = getArguments();
        if(bundle != null){
            user = bundle.getParcelable("userParcel1");
        }
    }
       private int generatePlaceId(){
        Random rand = new Random();
           return rand.nextInt(1000);
       }
    private void listeners(){
            binding.okId.setOnClickListener(view -> {
                if(checkValidation()){
                    Comment comment = new Comment(user,binding.commentId.getText().toString(),binding.ratingId.getRating());
                    ArrayList<Comment> allComment = new ArrayList<>();
                    allComment.add(comment);
                    Place place = new Place(allComment,binding.descriptionId.getText().toString(),binding.locationId.getText().toString(),
                            null,generatePlaceId());
                    touristParkViewModel.registerNewPlace(user,place,requireActivity(),imageUri);
                }
            });
            binding.uploadImageId.setOnClickListener(view -> openChooseFile());
    }
    private void openChooseFile() {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode == RESULT_OK && data!=null) {
            imageUri.clear();
            isImageSelected = true;
            if(data.getClipData()!=null){
                binding.selectImage.setVisibility(View.GONE);
                binding.multipleImageViewId.setVisibility(View.VISIBLE);
                int len = data.getClipData().getItemCount();
                for(int i = 0; i < len; i++){
                    Uri mImageUri = data.getClipData().getItemAt(i).getUri();
                    if(i<4){
                        if(i==0){
                            Glide.with(this).load(mImageUri).into(binding.showPicId1);
                        } else if(i==1){
                            Glide.with(this).load(mImageUri).into(binding.showPicId2);
                        } else if(i==2){
                            Glide.with(this).load(mImageUri).into(binding.showPicId3);
                        } else if(i==3){
                            Glide.with(this).load(mImageUri).into(binding.showPicId4);
                        }
                        imageUri.add(mImageUri);
                    }
                }
            } else {
                binding.multipleImageViewId.setVisibility(View.GONE);
                binding.selectImage.setVisibility(View.VISIBLE);
                Uri mImageUri = data.getData();
                imageUri.add(mImageUri);
                Glide.with(this).load(mImageUri).into(binding.selectImage);
            }
        } else {
            isImageSelected = false;
        }
    }

    private boolean checkValidation(){
        if(!isImageSelected){
            Toast.makeText(getContext(), "Please select image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(binding.descriptionId.getText())){
            binding.descriptionId.setError("This can't be empty");
            binding.descriptionId.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(binding.commentId.getText())){
            binding.commentId.setError("This can't be empty!");
            binding.descriptionId.requestFocus();
            return false;
        }
        return true;
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
            } else {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Location Permission Denied!");
                builder.setMessage("Without location permission\nyou can't register new place");
                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    bundle = new Bundle();
                    bundle.putParcelable("userParcel",user);
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.place_frag_to_homeuser_frag,bundle);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
            }
        } else {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Location Permission Denied!");
            builder.setMessage("Without location permission\nyou can't register new place");
            builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                bundle = new Bundle();
                bundle.putParcelable("userParcel",user);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.place_frag_to_homeuser_frag,bundle);
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void getLocation() {
        try{
            isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
           e.printStackTrace();
        }
        try{
            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!isGPSEnable && !isNetworkEnable){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Attention!");
            builder.setMessage("GPS or Network is not enable");
            builder.setPositiveButton(android.R.string.ok,null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        if(isGPSEnable){
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        if(isNetworkEnable){
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,this);
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
           try {
               Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
               List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
               String address = addresses.get(0).getAddressLine(0);
               binding.progressId.setVisibility(View.GONE);
               binding.locationId.setText("Location: "+address);
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