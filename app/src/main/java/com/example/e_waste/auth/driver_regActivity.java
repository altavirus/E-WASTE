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
import com.example.e_waste.databinding.ActivityDriverRegBinding;
import com.example.e_waste.driver_rqstsActivity;
import com.example.e_waste.user_rqstActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class driver_regActivity extends AppCompatActivity  implements View.OnClickListener {
ActivityDriverRegBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "driver_regActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg);
        binding=ActivityDriverRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.loginTextView.setOnClickListener(this);
        binding.buttonNext.setOnClickListener(this);
        createAuthStateListener();
    }
    @Override
    public void onClick(View view) {
        if (view == binding.loginTextView) {
            navigateTologinActivity("DriverLoginFragment");
        } else if (view == binding.buttonNext) {
            createNewUser();
        }
    }

    private void navigateTologinActivity(String fragmentTag) {
        Intent intent = new Intent(driver_regActivity.this, loginActivity.class);
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
        final String password = binding.passPEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                Log.d(TAG, "Authentication successful");
            } else {
                Toast.makeText(driver_regActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAuthStateListener() {
        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                navigateTologinActivity("DriverLoginFragment");
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
                isValidPassword(binding.passPEditText.getText().toString()) &&
                isPhoneValid(binding.numberEditText.getText().toString()) &&
                isValidPin(binding.pinEditText.getText().toString()) && isValidNumPlate(binding.trkEditText.getText().toString());
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
            binding.pinEditText.setError("Please enter admin pin");
            return false;
        }
        return true;
    }
    private boolean isValidNumPlate(String numPlate){
        if(numPlate.equals("")){
            binding.trkEditText.setError("Please enter Truck Registration number");
            return false;
        } else if (numPlate.length() <7){
            binding.trkEditText.setError("Please enter valid Truck Registration number");
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password){
        if(password.length() < 6){
            binding.passPEditText.setError("Please create a password containing atleast 6 characters");
            return false;
        }
        return true;
    }


    private boolean isPhoneValid(String phone) {
        if(phone.length() < 10 ){
            binding.numberEditText.setError("Please enter a valid Kenyan phone number");
            return false;
        } else if (TextUtils.isEmpty(phone)){
            binding.numberEditText.setError("Please enter phone number");
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
                            Toast.makeText(driver_regActivity.this, "Going to home page", Toast.LENGTH_LONG).show();
                            startNextActivity();
                        }
                    }
                });
    }
    private void startNextActivity() {
        Intent intent = new Intent(driver_regActivity.this, driver_rqstsActivity.class);

        // Pass user information using extras
        intent.putExtra("driverName", binding.nameEditText.getText().toString());
        intent.putExtra("driverEmail", binding.emailEditText.getText().toString());
        intent.putExtra("driverPswd", binding.passPEditText.getText().toString());
        intent.putExtra("trkNum", binding.trkEditText.getText().toString());
        intent.putExtra("adminPin", binding.pinEditText.getText().toString());
        intent.putExtra("driverPhone", binding.numberEditText.getText().toString());
        startActivity(intent);
        finish();
    }
}