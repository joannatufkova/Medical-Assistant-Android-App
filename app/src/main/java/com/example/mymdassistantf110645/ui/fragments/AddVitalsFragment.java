package com.example.mymdassistantf110645.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.database.DatabaseHelper;
import com.example.mymdassistantf110645.database.model.Appointment;
import com.example.mymdassistantf110645.database.model.Vital;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

public class AddVitalsFragment extends Fragment {
    private EditText temperatureEditText, pulseEditText, bloodPressureEditText;
    private EditText bloodGlucoseEditText, respiratoryRateEditText, weightEditText, heightEditText;
    private Button saveVitalsButton;
    private TextView editButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vitals, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton = view.findViewById(R.id.settings_button);
        editButton.setOnClickListener(v -> toggleEditState(true));

        temperatureEditText = view.findViewById(R.id.temperatureEditText);
        pulseEditText = view.findViewById(R.id.pulseEditText);
        bloodPressureEditText = view.findViewById(R.id.bloodPressureEditText);
        bloodGlucoseEditText = view.findViewById(R.id.bloodGlucoseEditText);
        respiratoryRateEditText = view.findViewById(R.id.respiratoryRateEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        saveVitalsButton = view.findViewById(R.id.saveVitalsButton);

        saveVitalsButton.setOnClickListener(v -> {
            saveVitals();
            toggleEditState(false);
        });

        displayLastVital();
    }

    private void toggleEditState(boolean isEditable) {
        temperatureEditText.setEnabled(isEditable);
        pulseEditText.setEnabled(isEditable);
        bloodPressureEditText.setEnabled(isEditable);
        bloodGlucoseEditText.setEnabled(isEditable);
        respiratoryRateEditText.setEnabled(isEditable);
        weightEditText.setEnabled(isEditable);
        heightEditText.setEnabled(isEditable);

        saveVitalsButton.setVisibility(isEditable ? View.VISIBLE : View.GONE);
    }

    private void displayLastVital() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        Vital lastVital = db.getLastVital();
        if (lastVital != null) {
            temperatureEditText.setText(String.valueOf(lastVital.getTemperature()));
            pulseEditText.setText(String.valueOf(lastVital.getPulse()));
            bloodPressureEditText.setText(String.valueOf(lastVital.getBloodPressure()));
            bloodGlucoseEditText.setText(String.valueOf(lastVital.getBloodGlucose()));
            respiratoryRateEditText.setText(String.valueOf(lastVital.getRespiratoryRate()));
            weightEditText.setText(String.valueOf(lastVital.getWeight()));
            heightEditText.setText(String.valueOf(lastVital.getHeight()));
        }
    }
    private void saveVitals() {
        double temperature = Double.parseDouble(temperatureEditText.getText().toString());
        int pulse = Integer.parseInt(pulseEditText.getText().toString());
        String bloodPressure = bloodPressureEditText.getText().toString();
        int bloodGlucose = Integer.parseInt(bloodGlucoseEditText.getText().toString());
        int respiratoryRate = Integer.parseInt(respiratoryRateEditText.getText().toString());
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double height = Double.parseDouble(heightEditText.getText().toString());

        Vital vital = new Vital(temperature, pulse, bloodPressure, bloodGlucose, respiratoryRate, weight, height);

        try (DatabaseHelper db = new DatabaseHelper(getContext())) {

            if (db.addVital(vital)) {
                Toast.makeText(getContext(), "Vitals saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save vitals", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(true, "Vitals", true);
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
