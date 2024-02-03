package com.example.mymdassistantf110645.ui.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymdassistantf110645.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileDisplayActivity extends AppCompatActivity {
    private TextView fileContentTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);

        fileContentTextView = findViewById(R.id.file_content_text_view);

        Uri fileUri = getIntent().getData();
        if (fileUri != null) {
            loadFileContent(fileUri);
        }
    }

    private void loadFileContent(Uri fileUri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(fileUri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append('\n');
                    }
                    reader.close();
                    inputStream.close();
                    final String fileContent = stringBuilder.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fileContentTextView.setText(fileContent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
