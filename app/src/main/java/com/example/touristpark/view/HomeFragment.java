package com.example.touristpark.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private int REQUEST_PERMISSION = 103;
    private AlertDialog.Builder builder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addPlaceId){
                checkPermission();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermission(){
        if(getActivity() !=null){
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);
            } else {
                showOptions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length!=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   showOptions();
            } else {
                 builder = new AlertDialog.Builder(getContext());
                 builder.setTitle("Location Permission Denied!");
                 builder.setMessage("Without location permission\nyou can't add new tourist place");
                 builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.dismiss();
                     }
                 });
                 AlertDialog alertDialog = builder.create();
                 alertDialog.show();
                 alertDialog.setCanceledOnTouchOutside(false);
            }
        }
    }
    private void showOptions(){
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.choose_option,null);
        builder.setView(view);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        view.findViewById(R.id.signUpId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.home_frag_to_sign_up_frag);
            }
        });
    }
}