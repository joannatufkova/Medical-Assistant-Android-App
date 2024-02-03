package com.example.mymdassistantf110645.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

public class LabResultsFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab_results, container, false);
        webView = view.findViewById(R.id.labResultsWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://web.kandilarov.com/login");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(true, "Check Your Results Directly From Here", true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "Check Your Results Directly From Here", false);
        }
    }
}
