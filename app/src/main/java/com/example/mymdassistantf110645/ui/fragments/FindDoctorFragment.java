package com.example.mymdassistantf110645.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

import okhttp3.OkHttpClient;

public class FindDoctorFragment extends Fragment  {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_doctor, container, false);
        webView = view.findViewById(R.id.findDoctorWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://superdoc.bg/");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "Find Your Doctor Directly From Here", true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "Find Your Doctor Directly From Here", false);
        }
    }

}
