package com.example.mymdassistantf110645.ui.activities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.fragments.AppointmentFragment;
import com.example.mymdassistantf110645.ui.fragments.FindDoctorFragment;
import com.example.mymdassistantf110645.ui.fragments.HomePageFragment;
import com.example.mymdassistantf110645.ui.fragments.LoginFragment;
import com.example.mymdassistantf110645.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class UserProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton settingsButton;
    private TextView toolbarTitle;
    protected NavHostFragment m_navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = findViewById(R.id.custom_toolbar);
        settingsButton = findViewById(R.id.settings_button);
        toolbarTitle = findViewById(R.id.custom_toolbar_title);

        updateToolbar(false, "", false);

        initBottomNavBarColors();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        m_navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.hostNavFragment);
        assert m_navHostFragment != null : "Main NavHostFragment is NULL";

        NavigationUI.setupWithNavController(bottomNavigationView, m_navHostFragment.getNavController());

//        settingsButton.setOnClickListener(v -> {
//            NavController navController = Navigation.findNavController(this, R.id.hostNavFragment);
//            navController.navigate(R.id.action_global_loginFragment);
//        });
        createNotificationChannel();
    }

    private void initBottomNavBarColors() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // Unselected
                new int[]{android.R.attr.state_checked}  // Selected
        };

        int[] colors = new int[]{
                getColor(R.color.inactive_tab_color), // Color for Unselected Icons
                getColor(R.color.black)    // Color for Selected Icons
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        // Apply the color state list to both icon and text color for consistency
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);
    }

    public void updateToolbar(boolean showBackAndSettings, String title, boolean showTitle) {
        if(showBackAndSettings) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);
            settingsButton.setVisibility(View.VISIBLE);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        } else {
            toolbar.setNavigationIcon(null);
            settingsButton.setVisibility(View.GONE);
        }

        toolbarTitle.setText(showTitle ? title : "");
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyAppointment", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
