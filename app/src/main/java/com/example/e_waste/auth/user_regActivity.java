package com.example.e_waste.auth;

import static android.content.ContentValues.TAG;

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
import com.example.e_waste.databinding.ActivityMainBinding;
import com.example.e_waste.databinding.ActivityUserRegBinding;
import com.example.e_waste.models.User;
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

public class user_regActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "user_regActivity";
    private DatabaseReference mDatabaseRef;
    private ActivityUserRegBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("usersList");
        mAuth = FirebaseAuth.getInstance();
        binding.loginTextView.setOnClickListener(this);
        binding.createUserButton.setOnClickListener(this);
        createAuthStateListener();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.loginTextView) {
            navigateTologinActivity("UserLoginFragment");
        } else if (view == binding.createUserButton) {
            uploadUser();
            createNewUser();
        }
    }

    private void navigateTologinActivity(String fragmentTag) {
        Intent intent = new Intent(user_regActivity.this, loginActivity.class);
        intent.putExtra("fragmentToLoad", fragmentTag);
        startActivity(intent);
        finish();
    }
    private void uploadUser() {
        User User=new User(binding.nameEditText.getText().toString(),binding.emailEditText.getText().toString(),binding.passwordEditText.getText().toString(),binding.phoneEditText.getText().toString());
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(User)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data uploaded successfully
                        Toast.makeText(user_regActivity.this, "Successful user upload", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to upload data
                        Toast.makeText(user_regActivity.this, "Failed to upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
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
                Toast.makeText(user_regActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void createAuthStateListener() {
        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                navigateTologinActivity("UserLoginFragment");
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
                isPhoneValid(binding.phoneEditText.getText().toString());
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
                            Toast.makeText(user_regActivity.this, "Going to home page", Toast.LENGTH_LONG).show();
                            startNextActivity();
                        }
                    }
                });
    }
    private void startNextActivity() {
        Intent intent = new Intent(user_regActivity.this, user_rqstActivity.class);

        // Pass user information using extras
        intent.putExtra("userName", binding.nameEditText.getText().toString());
        intent.putExtra("userEmail", binding.emailEditText.getText().toString());
        intent.putExtra("userPswd", binding.passwordEditText.getText().toString());
        intent.putExtra("userPhone", binding.phoneEditText.getText().toString());
        startActivity(intent);
        finish();
    }
}