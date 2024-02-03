package com.example.mymdassistantf110645.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;

public class HealthTipFragment extends Fragment {
    private static final String ARG_HEALTH_TIP = "healthTip";

    public static HealthTipFragment newInstance(String healthTip, int iconResId) {
        HealthTipFragment fragment = new HealthTipFragment();
        Bundle args = new Bundle();
        args.putString("healthTip", healthTip);
        args.putInt("iconResId", iconResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_tip, container, false);

        TextView healthTipTextView = view.findViewById(R.id.healthTipTextView);
        ImageView healthTipIcon = view.findViewById(R.id.healthTipIcon);

        if (getArguments() != null) {
            healthTipTextView.setText(getArguments().getString("healthTip"));
            int iconResId = getArguments().getInt("iconResId", -1);
            if (iconResId != -1) {
                healthTipIcon.setImageResource(iconResId);
            }
        }
        return view;
    }

}
