package com.npardon.gmagroandroid.ui;

import androidx.fragment.app.Fragment;


import android.app.Activity;


import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.databinding.ActivityLesInterventionsBinding;

public class LesInterventions extends Activity {
    ActivityLesInterventionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLesInterventionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    InterventionsFragment homeFragment = new InterventionsFragment();
                    FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
                    fragmentTransactionHome.replace(R.id.frameLayout, homeFragment, "A").addToBackStack("name").commit();
                    break;
                case R.id.add:
                    AddFragment addFragment = new AddFragment();
                    FragmentTransaction fragmentTransactionAdd = getFragmentManager().beginTransaction();
                    fragmentTransactionAdd.replace(R.id.frameLayout, addFragment, "A").addToBackStack("name").commit();
                    break;
                case R.id.profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransactionProfile = getFragmentManager().beginTransaction();
                    fragmentTransactionProfile.replace(R.id.frameLayout, profileFragment, "A").addToBackStack("name").commit();
                    break;
            }
            return true;
        });

    }

}