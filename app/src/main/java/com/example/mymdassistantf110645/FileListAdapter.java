package com.example.mymdassistantf110645;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private final List<Uri> fileUris;
    private final LayoutInflater inflater;
    private final OnFileClickListener onFileClickListener;

    public FileListAdapter(Context context, List<Uri> fileUris, OnFileClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.fileUris = fileUris;
        this.onFileClickListener = listener;
    }

    @NonNull
    @Override
    public FileListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListAdapter.ViewHolder holder, int position) {
        Uri fileUri = fileUris.get(position);
        holder.fileName.setText(fileUri.getLastPathSegment());
        holder.fileIcon.setImageResource(R.drawable.ic_file_icon);

        // Set the click listener to the whole item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onFileClickListener != null) {
                    onFileClickListener.onFileClick(fileUri);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileUris.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public ImageView fileIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.item_file_name);
            fileIcon = itemView.findViewById(R.id.item_file_icon);
        }
    }
    public interface OnFileClickListener {
        void onFileClick(Uri fileUri);
    }

}
