package com.example.e_waste;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_waste.databinding.ActivityUserRqstBinding;
import com.example.e_waste.models.request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_rqstActivity extends AppCompatActivity {
ActivityUserRqstBinding binding;
    private DatabaseReference mDatabaseRef;
    String userName;
    String userEmail;
    String userPswd;
    String userPhone;
    String userLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid =user.getUid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rqst);
        binding=ActivityUserRqstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(uid).child("requests");
        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("userName");
            userEmail = intent.getStringExtra("userEmail");
            userPswd = intent.getStringExtra("userPswd");
            userPhone = intent.getStringExtra("userPhone");
            userLocation = intent.getStringExtra("userLocation");
        }


        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadFile();
            }
        });
    }

    private void uploadFile() {
        request request=new request(userName,userEmail,userPswd,userPhone,userLocation);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data uploaded successfully
                        Toast.makeText(user_rqstActivity.this, "Request successfully sent", Toast.LENGTH_SHORT).show();
                        // Enable the button and hide the progress bar
                        binding.button4.setEnabled(true);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to upload data
                        Toast.makeText(user_rqstActivity.this, "Failed to upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        // Enable the button and hide the progress bar
                        binding.button4.setEnabled(true);
                    }
                });
    }


}