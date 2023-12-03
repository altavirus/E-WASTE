package com.example.e_waste.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_waste.R;
import com.example.e_waste.admin_rqstsActivity;
import com.example.e_waste.databinding.FragmentAdminLoginBinding;
import com.example.e_waste.databinding.FragmentDriverLoginBinding;
import com.example.e_waste.databinding.FragmentUserLoginBinding;
import com.example.e_waste.driver_rqstsActivity;
import com.example.e_waste.user_rqstActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class adminLoginFragment extends Fragment implements View.OnClickListener {

    private FragmentAdminLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public adminLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        binding.LoginButton.setOnClickListener(this);
        binding.registerTextView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuthListener = createAuthStateListener();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.registerTextView) {
            // Adjust the destination activity based on your requirements
            Intent intent = new Intent(requireActivity(), driver_regActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        if (v == binding.LoginButton) {
            loginWithPassword();
        }
    }

    private void loginWithPassword() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            // Handle empty email or password
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                        } else {
                            // Login failed
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private FirebaseAuth.AuthStateListener createAuthStateListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(requireActivity(), admin_rqstsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        };
    }
}
