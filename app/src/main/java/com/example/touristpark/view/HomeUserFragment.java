package com.example.touristpark.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentHomeUserBinding;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.view.adapter.RecyclerViewAdapter;
import com.example.touristpark.viewmodel.TouristParkViewModel;

import java.util.ArrayList;

public class HomeUserFragment extends Fragment implements ItemClickListener {
    private FragmentHomeUserBinding fragmentHomeUserBinding;
    private final ArrayList<Place> placeList = new ArrayList<>();
    private TouristParkViewModel touristParkViewModel;
    private RecyclerViewAdapter adapter;
    private User user = new User();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeUserBinding = FragmentHomeUserBinding.inflate(inflater,container,false);
       return fragmentHomeUserBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        setUpRecyclerView();
        observers();
        checkBundle();
    }

    private void checkBundle() {
        Bundle bundle = getArguments();
        if(bundle != null){
            user = bundle.getParcelable("userParcel");
            if(user.getEmail().equals("ano")){
                setHasOptionsMenu(false);
            }
        }
    }

    public void setUpRecyclerView(){
        adapter = new RecyclerViewAdapter(placeList,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        fragmentHomeUserBinding.recyclerId.setLayoutManager(manager);
        fragmentHomeUserBinding.recyclerId.setAdapter(adapter);
    }

    public void observers(){
        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        touristParkViewModel.allPlaces.observe(getViewLifecycleOwner(), item-> {
            placeList.clear();
            placeList.addAll(item);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        });
    }

    @Override
    public void singleItemClick(Place place) {
         Bundle bundle = new Bundle();
         bundle.putParcelable("singleParcel",place);
         bundle.putParcelable("userParcel2",user);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.homeUser_frag_to_ItemDetail_frag,bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.registerId:
                 Bundle bundle = new Bundle();
                 bundle.putParcelable("userParcel1",user);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.homeuser_frag_to_place_frag,bundle);
                break;
            case R.id.profileId:
            case R.id.logoutId: Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.homeuser_frag_to_home_frag);
        }
        return super.onOptionsItemSelected(item);
    }
}