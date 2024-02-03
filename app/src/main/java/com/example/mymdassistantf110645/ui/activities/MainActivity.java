package com.example.mymdassistantf110645.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mymdassistantf110645.ui.fragments.LoginFragment;
import com.example.mymdassistantf110645.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the LoginFragment as the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, new LoginFragment())
                    .commit();
        }
    }

    public void replaceFragments(Class<? extends Fragment> fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}


