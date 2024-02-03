package com.example.mymdassistantf110645.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView vitalsCard = view.findViewById(R.id.vitalsMenu);
        vitalsCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_addVitalsFragment);
        });

        CardView documentsCard = view.findViewById(R.id.documentsMenu);
        documentsCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_addDocumentsFragment);
        });

        CardView labresultsCard = view.findViewById(R.id.labResultsMenu);
        labresultsCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_labResultsFragment);
        });

        CardView additionaInfoCard = view.findViewById(R.id.additionalInformationMenu);
        additionaInfoCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_additionalInfoFragment);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(true, "My Profile", true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "", false);
        }
    }

}

