package com.example.e_waste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_waste.databinding.ActivityAdminRqstsBinding;
import com.example.e_waste.models.admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_rqstsActivity extends AppCompatActivity {
    ActivityAdminRqstsBinding binding;
    private DatabaseReference mDatabaseRef;
    String adminName;
    String adminEmail;
    String adminPswd;
    String adminPhone;
    String adminPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRqstsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("admins");

        Intent intent = getIntent();
        if (intent != null) {
            adminName = intent.getStringExtra("adminName");
            adminEmail = intent.getStringExtra("adminEmail");
            adminPswd = intent.getStringExtra("adminPswd");
            adminPhone = intent.getStringExtra("adminPhone");
            adminPin = intent.getStringExtra("adminPin");
        }

        uploadAdmin();
    }

    private void uploadAdmin() {
        admin admin = new admin(adminName, adminEmail, adminPswd, adminPhone, adminPin);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data uploaded successfully
                        Toast.makeText(admin_rqstsActivity.this, "Successful admin upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to upload data
                        Toast.makeText(admin_rqstsActivity.this, "Failed to upload admin: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
