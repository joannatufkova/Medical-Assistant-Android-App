package com.example.mymdassistantf110645.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.fragments.HealthTipFragment;

public class HealthTipsAdapter extends FragmentStateAdapter {
    private final String[] healthTips = new String[] {
            "Stay hydrated - Drinking enough water is crucial for your overall health.",
            "Eat balanced meals - Include a variety of foods in your diet to get all the nutrients you need.",
            "Exercise regularly - Regular physical activity can improve your mood and reduce the risk of diseases.",
            "Get enough sleep - Quality sleep is essential for your body to recover and function properly."
    };

    public HealthTipsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        // Example mapping, replace with actual logic
        int iconResId;
        switch (position) {
            case 0:
                iconResId = R.drawable.ic_stay_hydrated;
                break;
            case 1:
                iconResId = R.drawable.ic_eat_healthy;
                break;
            case 2:
                iconResId = R.drawable.ic_exercise;
                break;
            case 3:
                iconResId = R.drawable.ic_sleep;
                break;
            default:
                iconResId = R.drawable.ic_health_icon;
        }
        return HealthTipFragment.newInstance(healthTips[position], iconResId);
    }


    @Override
    public int getItemCount() {
        return healthTips.length;
    }

}
