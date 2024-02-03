package com.example.mymdassistantf110645.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ReminderBroadcast;
import com.example.mymdassistantf110645.database.DatabaseHelper;
import com.example.mymdassistantf110645.database.model.Appointment;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AppointmentFragment extends Fragment {
    private EditText doctorNameEditText, specialityEditText, noteEditText;
    private TextView selectedDateTextView, selectedTimeTextView;
    private Button pickDateButton, pickTimeButton, saveButton, addAppointmentButton;
    private LinearLayout appointmentForm;
    private ListView appointmentListView;
    private ArrayAdapter<String> appointmentListAdapter;
    private List<String> appointmentList = new ArrayList<>();
    private Calendar selectedDate = Calendar.getInstance();
    private Calendar selectedTime = Calendar.getInstance();
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        doctorNameEditText = view.findViewById(R.id.doctorNameEditText);
        specialityEditText = view.findViewById(R.id.specialityEditText);
        noteEditText = view.findViewById(R.id.appointmentNoteEditText);
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        selectedTimeTextView = view.findViewById(R.id.selectedTimeTextView);
        pickDateButton = view.findViewById(R.id.pickDateButton);
        pickTimeButton = view.findViewById(R.id.pickTimeButton);
        saveButton = view.findViewById(R.id.saveAppointmentButton);
        addAppointmentButton = view.findViewById(R.id.addAppointmentButton);
        appointmentForm = view.findViewById(R.id.appointmentForm);
        appointmentListView = view.findViewById(R.id.appointmentListView);

        appointmentListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, appointmentList);
        appointmentListView.setAdapter(appointmentListAdapter);

        addAppointmentButton.setOnClickListener(v -> toggleAppointmentForm());
        pickDateButton.setOnClickListener(v -> showDatePickerDialog());
        pickTimeButton.setOnClickListener(v -> showTimePickerDialog());
        saveButton.setOnClickListener(v -> saveAppointment());

        dbHelper = new DatabaseHelper(getContext());

        loadAppointments();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(false, "My Appointments", true);
        }
    }

    private void toggleAppointmentForm() {
        if (appointmentForm.getVisibility() == View.GONE) {
            appointmentForm.setVisibility(View.VISIBLE);
            // Reset the date and time pickers for a new appointment
            selectedDate = Calendar.getInstance();
            selectedTime = Calendar.getInstance();
            selectedDateTextView.setText("");
            selectedTimeTextView.setText("");
        } else {
            appointmentForm.setVisibility(View.GONE);
        }
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    String selectedDateString = year + "-" + (month + 1) + "-" + dayOfMonth;
                    selectedDateTextView.setText(selectedDateString);
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute) -> {
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedTime.set(Calendar.MINUTE, minute);
                    String selectedTimeString = hourOfDay + ":" + minute;
                    selectedTimeTextView.setText(selectedTimeString);
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }
    private void saveAppointment() {
        String doctorName = doctorNameEditText.getText().toString();
        String speciality = specialityEditText.getText().toString();
        String date = selectedDateTextView.getText().toString();
        String time = selectedTimeTextView.getText().toString();
        String note = noteEditText.getText().toString();

        Appointment newAppointment = new Appointment(doctorName, speciality, date, time, note);

        if (dbHelper.addAppointment(newAppointment)) {
            Toast.makeText(getContext(), "Appointment saved", Toast.LENGTH_SHORT).show();
            loadAppointments();
            resetAppointmentForm(); // Reset the form
            scheduleReminder(); // Schedule a reminder for the appointment
        } else {
            Toast.makeText(getContext(), "Failed to save appointment", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAppointmentForm() {
        doctorNameEditText.setText("");
        specialityEditText.setText("");
        noteEditText.setText("");
        selectedDateTextView.setText("");
        selectedTimeTextView.setText("");

        selectedDate = Calendar.getInstance();
        selectedTime = Calendar.getInstance();

        appointmentForm.setVisibility(View.GONE);
    }
    private void loadAppointments() {
        List<Appointment> appointments = dbHelper.getAllAppointments();
        List<String> appointmentDetailsList = new ArrayList<>();

        for (Appointment appointment : appointments) {
            String details = appointment.getDoctorName() + " - " + appointment.getDate() + " at " + appointment.getTime() + " - " + appointment.getNote();
            appointmentDetailsList.add(details);
        }

        appointmentListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, appointmentDetailsList);
        appointmentListView.setAdapter(appointmentListAdapter);
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleReminder() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR));
        c.set(Calendar.MONTH, selectedDate.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, selectedDate.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, selectedTime.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, selectedTime.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}
