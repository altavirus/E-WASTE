package com.example.e_waste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_waste.auth.admin_regActivity;
import com.example.e_waste.auth.driver_regActivity;
import com.example.e_waste.auth.user_regActivity;
import com.example.e_waste.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding object
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set click listeners for buttons
        binding.userBtn.setOnClickListener(this);
        binding.adminBtn.setOnClickListener(this);
        binding.driverButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        if (view == binding.userBtn) {
            intent = new Intent(MainActivity.this, user_regActivity.class);
        } else if (view == binding.adminBtn) {
            intent = new Intent(MainActivity.this, admin_regActivity.class);
        } else if (view == binding.driverButton) {
            intent = new Intent(MainActivity.this, driver_regActivity.class);
        }

        if (intent != null) {
            // Start the new activity without using flags
            startActivity(intent);
        }
    }
}
