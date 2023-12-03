package com.example.e_waste.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.e_waste.R;
import com.example.e_waste.admin_rqstsActivity;
import com.example.e_waste.databinding.ActivityAdminRegBinding;
import com.example.e_waste.models.admin;
import com.example.e_waste.user_rqstActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class admin_regActivity extends AppCompatActivity  implements View.OnClickListener {
ActivityAdminRegBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "admin_regActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg);
    binding=ActivityAdminRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("adminsList");
        mAuth = FirebaseAuth.getInstance();
        binding.loginTextView.setOnClickListener(this);
        binding.createUserButton.setOnClickListener(this);
        createAuthStateListener();
    }
    @Override
    public void onClick(View view) {
        if (view == binding.loginTextView) {
            navigateTologinActivity("AdminLoginFragment");
        } else if (view == binding.createUserButton) {
            uploadAdmin();
            createNewUser();
        }
    }

    private void navigateTologinActivity(String fragmentTag) {
        Intent intent = new Intent(admin_regActivity.this, loginActivity.class);
        intent.putExtra("fragmentToLoad", fragmentTag);
        startActivity(intent);
        finish();
    }
    private void createNewUser() {
        if (!isFormValid()) {
            return;
        }

        showProgressBar();

        final String email = binding.emailEditText.getText().toString().trim();
        final String password = binding.passwordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                Log.d(TAG, "Authentication successful");
            } else {
                Toast.makeText(admin_regActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadAdmin() {
        admin admin = new admin(binding.nameEditText.getText().toString(), binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString(), binding.phoneEditText.getText().toString(), binding.adminPinEditText.getText().toString());
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data uploaded successfully
                        Toast.makeText(admin_regActivity.this, "Successful admin upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to upload data
                        Toast.makeText(admin_regActivity.this, "Failed to upload admin: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void createAuthStateListener() {
        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                navigateTologinActivity("AdminLoginFragment");
            }
        };
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void hideProgressBar() {
        binding.firebaseProgressBar.setVisibility(View.GONE);
        binding.loadingTextView.setVisibility(View.GONE);
    }
    private boolean isFormValid() {
        return isValidName(binding.nameEditText.getText().toString()) &&
                isValidEmail(binding.emailEditText.getText().toString()) &&
                isValidPassword(binding.passwordEditText.getText().toString(), binding.confirmPasswordEditText.getText().toString()) &&
                isPhoneValid(binding.phoneEditText.getText().toString()) && isValidPin(binding.adminPinEditText.getText().toString());
    }
    private void showProgressBar() {

        binding.firebaseProgressBar.setVisibility(View.VISIBLE);
        binding.loadingTextView.setVisibility(View.VISIBLE);
        binding.loadingTextView.setText("Sign Up process in Progress");
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            binding.emailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name){
        if(name.equals("")){
            binding.nameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }
    private boolean isValidPin(String pin){
        if(pin.equals("")){
            binding.adminPinEditText.setError("Please set admin pin");
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password, String confirmPassword){
        if(password.length() < 6){
            binding.passwordEditText.setError("Please create a password containing atleast 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)){
            binding.passwordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }


    private boolean isPhoneValid(String phone) {
        if(phone.length() < 10 ){
            binding.phoneEditText.setError("Please enter a valid Kenyan phone number");
            return false;
        } else if (TextUtils.isEmpty(phone)){
            binding.phoneEditText.setError("Please enter phone number");
            return false;
        }
        return true;
    }

    private void createFirebaseUserProfile(final FirebaseUser user){
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(binding.nameEditText.getText().toString())
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgressBar();
                        if(task.isSuccessful()){
                            Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                            Toast.makeText(admin_regActivity.this, "Going to home page", Toast.LENGTH_LONG).show();
                            startNextActivity();
                        }
                    }
                });
    }
    private void startNextActivity() {
        Intent intent = new Intent(admin_regActivity.this, admin_rqstsActivity.class);

        // Pass user information using extras
        intent.putExtra("adminName", binding.nameEditText.getText().toString());
        intent.putExtra("adminEmail", binding.emailEditText.getText().toString());
        intent.putExtra("adminPswd", binding.passwordEditText.getText().toString());
        intent.putExtra("adminPhone", binding.phoneEditText.getText().toString());
        intent.putExtra("adminPin", binding.adminPinEditText.getText().toString());
        startActivity(intent);
        finish();
    }
}