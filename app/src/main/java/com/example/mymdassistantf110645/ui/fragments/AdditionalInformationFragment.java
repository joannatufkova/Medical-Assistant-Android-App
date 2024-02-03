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

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.api.HealthApiService;
import com.example.mymdassistantf110645.model.HealthData;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdditionalInformationFragment extends Fragment {
    private TextView dataTextView;
    public AdditionalInformationFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_information, container, false);
        dataTextView = view.findViewById(R.id.infoTextView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiateRetrofitRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(true, "Additional Information", true);
        }
    }

    private void initiateRetrofitRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.healthcare.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HealthApiService service = retrofit.create(HealthApiService.class);

        // Make the API call
        Call<HealthData> call = service.getHealthData();
        call.enqueue(new Callback<HealthData>() {
            @Override
            public void onResponse(Call<HealthData> call, Response<HealthData> response) {
                if (response.isSuccessful()) {
                    HealthData data = response.body();

                    // Assuming 'content' field contains HTML content
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        Spanned spannedContent = Html.fromHtml(data.getContent(), Html.FROM_HTML_MODE_COMPACT);
                        dataTextView.setText(spannedContent);
                    } else {
                        Spanned spannedContent = Html.fromHtml(data.getContent());
                        dataTextView.setText(spannedContent);
                    }

                    // Make links clickable
                    dataTextView.setMovementMethod(LinkMovementMethod.getInstance());

                    // Optional: Change link color
                    dataTextView.setLinkTextColor(Color.BLUE);
                } else {
                    // Handle errors
                    Log.e("API Error", "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HealthData> call, Throwable t) {
                // Handle failure
                Log.e("API Failure", t.getMessage());
            }
        });
    }
}
