package com.example.touristpark.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
        underLineText();
        listeners();
    }

    private void listeners(){
        binding.signupId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.home_frag_to_sign_up_frag);
            }
        });
    }

    private void underLineText(){
        String signInText = binding.signInId.getText().toString();
        String signUpText = binding.signupId.getText().toString();
        String anonomousText = binding.anonomousId.getText().toString();

        SpannableString signInTextSpanable = new SpannableString(signInText);
        SpannableString singnUpTextSpanable = new SpannableString(signUpText);
        SpannableString anonomousTextSpanable = new SpannableString(anonomousText);

        signInTextSpanable.setSpan(new UnderlineSpan(),0,signInTextSpanable.length(),0);
        singnUpTextSpanable.setSpan(new UnderlineSpan(),0, singnUpTextSpanable.length(),0);
        anonomousTextSpanable.setSpan(new UnderlineSpan(),0,anonomousTextSpanable.length(),0);

        binding.signInId.setText(signInTextSpanable);
        binding.signupId.setText(singnUpTextSpanable);
        binding.anonomousId.setText(anonomousTextSpanable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length!=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

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

}