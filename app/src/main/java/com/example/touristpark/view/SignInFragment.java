package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentSignInBinding;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.viewmodel.TouristParkViewModel;

import java.util.ArrayList;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private TouristParkViewModel touristParkViewModel;
    ArrayList<User> allUser = new ArrayList<>();
    private User singleUser = new User();
    private final Bundle bundle = new Bundle();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        listeners();
        observers();
    }

    private void observers() {
        touristParkViewModel.allUser.observe(getViewLifecycleOwner(),user -> {
            allUser.clear();
            allUser.addAll(user);
        });
    }

    private void listeners() {
         binding.signInBtnId.setOnClickListener(view -> {
             if(checkValidation()){
                 Toast.makeText(getContext(), "Log In Successfully!", Toast.LENGTH_SHORT).show();
                 bundle.putParcelable("userParcel",singleUser);
                 Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.signin_frag_to_homeuser_frag,bundle);
             } else {
                 Toast.makeText(getContext(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private boolean checkValidation(){
        if(binding.emailsignInId.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.emailsignInId.getText().toString()).matches()){
            binding.emailsignInId.setError("Invalid email!");
            binding.emailsignInId.requestFocus();
            return false;
        } else if(binding.passwordSignInId.getText().toString().isEmpty()){
            binding.passwordSignInId.setError("Invalid password!");
            binding.passwordSignInId.requestFocus();
            return false;
        } else {
            String pass = binding.passwordSignInId.getText().toString();
            String email = binding.emailsignInId.getText().toString();
            for(User user: allUser){
                if(user.getEmail().equals(email.trim()) && user.getPassword().equals(pass)){
                    singleUser = user;
                    return true;
                }
            }
            return false;
        }
    }
}