package com.example.mymdassistantf110645.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.database.DatabaseHelper;
import com.example.mymdassistantf110645.database.model.User;
import com.example.mymdassistantf110645.ui.activities.MainActivity;

public class SignUpFragment extends Fragment {
    private EditText signupEmailEditText;
    private EditText signupUsernameEditText;
    private EditText signupPasswordEditText;
    private EditText signupConfirmPasswordEditText;
    private DatabaseHelper databaseHelper;
    public SignUpFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        signupEmailEditText = view.findViewById(R.id.signupEmailEditText);
        signupUsernameEditText = view.findViewById(R.id.signupUsernameEditText);
        signupPasswordEditText = view.findViewById(R.id.signupPasswordEditText);
        signupConfirmPasswordEditText = view.findViewById(R.id.signupConfirmPasswordEditText);

        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> registerUser());

        Button buttonGoToLogin = view.findViewById(R.id.buttonGoToLogin);
        buttonGoToLogin.setOnClickListener(v -> ((MainActivity)getActivity()).replaceFragments(LoginFragment.class));

        databaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void registerUser() {
        String email = signupEmailEditText.getText().toString().trim();
        String username = signupUsernameEditText.getText().toString().trim();
        String password = signupPasswordEditText.getText().toString().trim();
        String confirmPassword = signupConfirmPasswordEditText.getText().toString().trim();

        // Validate the input fields
        if (!isValidEmail(email)) {
            signupEmailEditText.setError("Invalid Email");
            return;
        }
        if (username.isEmpty()) {
            signupUsernameEditText.setError("Username is required");
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            signupPasswordEditText.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            signupConfirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        boolean isRegistered = performRegistration(email, username, password);

        if (isRegistered) {
            Toast.makeText(getActivity(), "Successful registration", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).replaceFragments(LoginFragment.class);
        } else {
            Toast.makeText(getActivity(), "Please fill the fields correctly.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean performRegistration(String email, String username, String password) {
        if (databaseHelper.isEmailExists(email)) {
            Toast.makeText(getActivity(), "Email already registered", Toast.LENGTH_SHORT).show();
            return false;
        } else if(databaseHelper.isUsernameExists(username)){
            Toast.makeText(getActivity(),"Username already exists", Toast.LENGTH_LONG).show();
            return false;
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(password); // Remember to hash the password in real applications

            return databaseHelper.addUser(newUser);
        }
    }


}
