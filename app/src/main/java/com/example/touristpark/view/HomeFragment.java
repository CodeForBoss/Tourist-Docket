package com.example.touristpark.view;

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
import com.example.touristpark.repository.model.User;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    Bundle bundle = new Bundle();
    User user = new User("Anonymous","ano");
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
        binding.anonomousId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("userParcel",user);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.home_frag_to_homeuser_frag,bundle);
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
}