package com.example.mymdassistantf110645.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;


import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.database.DatabaseHelper;
import com.example.mymdassistantf110645.ui.activities.MainActivity;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

public class LoginFragment extends Fragment {
    private EditText loginEmailEditText;
    private EditText loginUsernameEditText;
    private EditText loginPasswordEditText;
    private CheckBox rememberMeCheckBox;
    private DatabaseHelper db;

    public LoginFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginEmailEditText = view.findViewById(R.id.loginEmailEditText);
        loginUsernameEditText = view.findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = view.findViewById(R.id.loginPasswordEditText);
        rememberMeCheckBox = view.findViewById(R.id.rememberMeCheckBox);

        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> loginUser());

        TextView forgotPasswordTextView = view.findViewById(R.id.forgotPasswordTextView);
        forgotPasswordTextView.setOnClickListener(v -> resetPassword());

        Button buttonGoToSignup = view.findViewById(R.id.buttonGoToSignup);
        buttonGoToSignup.setOnClickListener(v -> ((MainActivity)getActivity()).replaceFragments(SignUpFragment.class));

        db = new DatabaseHelper(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        checkAutoLogin();
    }

    private void resetPassword() {
        // Logic to handle password reset
        String email = loginEmailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            return;
        }
    }

    private void loginUser() {
        String email = loginEmailEditText.getText().toString().trim();
        String username = loginUsernameEditText.getText().toString().trim();
        String password = loginPasswordEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            loginEmailEditText.setError("Invalid Email");
            return;
        }
        if (username.isEmpty()) {
            loginUsernameEditText.setError("Username is required");
            return;
        }
        if (password.isEmpty() || password.length() <= 6) {
            loginPasswordEditText.setError("Invalid Password");
            return;
        }

        boolean isAuthenticated = authenticateUser(email, password);

        // Authenticate user
        if (isAuthenticated) {
            // Save user email to SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyMDAssistantPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("currentUserEmail", email);
            editor.apply();

            if (rememberMeCheckBox.isChecked()) {
                editor.putBoolean("rememberMe", true);
                editor.putString("email", email);
                editor.putString("username", username);
                editor.putString("password", password);
            } else {
                editor.clear();
            }
            editor.apply();

            Toast.makeText(getActivity(), "LogIn Successful", Toast.LENGTH_SHORT).show();

            // Start UserProfileActivity
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            // Show an error message
            Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean authenticateUser(String email, String password) {
        return db.checkUser(email,password);
    }


    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkAutoLogin() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyMDAssistantPrefs", Context.MODE_PRIVATE);
        boolean isRemembered = sharedPreferences.getBoolean("rememberMe", false);
        if (isRemembered) {
            String email = sharedPreferences.getString("email", "");
            String username = sharedPreferences.getString("username", "");
            String password = sharedPreferences.getString("password", ""); // Consider encrypting the password
            loginEmailEditText.setText(email);
            loginUsernameEditText.setText(username);
            loginPasswordEditText.setText(password);
            loginUser();
        }
    }
}
