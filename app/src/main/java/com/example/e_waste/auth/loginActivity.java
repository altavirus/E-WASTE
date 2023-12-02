package com.example.e_waste.auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.e_waste.R;
import com.example.e_waste.databinding.ActivityLoginBinding;
import com.example.e_waste.user_rqstActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class loginActivity extends AppCompatActivity{



ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String fragmentToLoad = getIntent().getStringExtra("fragmentToLoad");

        if (fragmentToLoad != null) {
            // Load the appropriate fragment based on the information
            switch (fragmentToLoad) {
                case "UserLoginFragment":
                    loadFragment(new userLoginFragment());
                    break;
                case "DriverLoginFragment":
                    loadFragment(new driverLoginFragment());
                    break;
                case "AdminLoginFragment":
                    loadFragment(new adminLoginFragment());
                    break;
                // Add more cases for other fragments if needed
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
   }

