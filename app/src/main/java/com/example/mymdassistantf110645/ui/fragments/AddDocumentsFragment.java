package com.example.mymdassistantf110645.ui.fragments;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymdassistantf110645.FileListAdapter;
import com.example.mymdassistantf110645.R;
import com.example.mymdassistantf110645.ui.activities.FileDisplayActivity;
import com.example.mymdassistantf110645.ui.activities.UserProfileActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddDocumentsFragment extends Fragment {
    private static final int REQUEST_CODE_PICK_FILE = 1;
    private Button addButton;
    private TextView fileNameTextView;
    private View view;

    private RecyclerView filesRecyclerView;
    private FileListAdapter fileListAdapter;
    private List<Uri> fileUris = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_documents, container, false);

        filesRecyclerView = view.findViewById(R.id.files_recycler_view);
        filesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fileListAdapter = new FileListAdapter(getContext(), fileUris, new FileListAdapter.OnFileClickListener() {
            @Override
            public void onFileClick(Uri fileUri) {
                // check if getActivity() is not null
                Activity activity = getActivity();
                if (activity != null) {
                    // Used the activity to run on the UI thread
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Start an activity to display the file
                                                Intent intent = new Intent(getActivity(), FileDisplayActivity.class);
                                                intent.setData(fileUri);
                                                startActivity(intent);
                                            }
                                        });
                                }
                            }).start();
                        }
                    });
                }
            }
        });


        filesRecyclerView.setAdapter(fileListAdapter);

        addButton = view.findViewById(R.id.button_add);
        fileNameTextView = view.findViewById(R.id.text_file_name);
        addButton.setOnClickListener(v -> openFileChooser());
        return view;
    }


    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimetypes = {"application/pdf", "application/msword", "text/plain", "image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                fileUris.add(fileUri);
                fileListAdapter.notifyItemInserted(fileUris.size() - 1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof UserProfileActivity) {
            ((UserProfileActivity) getActivity()).updateToolbar(true, "Add Documents", true);
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
