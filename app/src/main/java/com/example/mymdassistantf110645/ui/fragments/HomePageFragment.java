package com.example.mymdassistantf110645.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.HealthTipsAdapter;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

public class HomePageFragment extends Fragment {
    private ViewPager2 healthTipsViewPager;
    public HomePageFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        healthTipsViewPager = view.findViewById(R.id.healthTipsViewPager);
        HealthTipsAdapter adapter = new HealthTipsAdapter(this);
        healthTipsViewPager.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "Home Page", true);
        }
    }
}
