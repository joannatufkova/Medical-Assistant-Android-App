# Medical-Assistant-Android-App

# Overview
The Medical Assistant Android App is designed to facilitate users in managing their health-related activities. It provides features to schedule doctor's appointments, view health tips, track personal health data, and manage medical documents.

# Features
Home Page
* Health Tips: Users receive daily health tips to encourage a healthy lifestyle.
Find Doctor
* Allows users to search for doctors by specialty, name, or location.
Appointments
* Manage and schedule appointments with doctors.
Profile
* Vitals: Users can track their vital signs like heart rate, blood pressure, etc.
* Documents: Store and manage personal health records and medical documents.
* Lab Results: View results from laboratory tests.
* Additional Information: Provide and view additional health information.

# Architecture
1. API
* HealthApiService: Manages the retrieval of health data from a remote server.
2. Database
* Model: Contains data models for Appointment, User, and Vital.
* DatabaseHelper: Assists in database operations like CRUD actions for the models.
3. UI
* Activities: Includes MainActivity and UserProfileActivity for main interaction points.
* Fragments: Multiple fragments like AddDocumentsFragment, AppointmentFragment, etc., to handle specific sections of the app.

# Adapters
* HealthTipsAdapter: Manages the display of health tips in a list view.
* FileListAdapter: Handles the listing of files/documents within the app.

# Broadcast Receivers
* ReminderBroadcast: Responsible for sending reminders to users for their appointments or health tips.
