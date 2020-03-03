package com.jayaraj.firebasechatapplication.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.jayaraj.firebasechatapplication.R;

import java.io.File;

public class VideoPlayerActivity extends AppCompatActivity {

    private Uri videoURI;
    private VideoView videoView;
    private ImageView imageView;
    private MediaController mediaController;
    String type,videoURL,user;
    ImageView download;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer_layout);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user=getIntent().getStringExtra("user");

        download=findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        if (user.equals("friend")){
            download.setVisibility(View.VISIBLE);
        }
        else{
            download.setVisibility(View.GONE);
        }

        videoURL = getIntent().getStringExtra("Video URL");
        type=getIntent().getStringExtra("type");
        videoView = findViewById(R.id.video_view);
        imageView=findViewById(R.id.image_view);
        if (type.equals("video")) {
            mediaController = new MediaController(this);

            videoURI = Uri.parse(videoURL);

            mediaController.setAnchorView(videoView);

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoURI);
            videoView.requestFocus();
            videoView.start();
        }
        else if (type.equals("audio")) {
            mediaController = new MediaController(this);

            videoURI = Uri.parse(videoURL);

            mediaController.setAnchorView(imageView);
            imageView.setVisibility(View.VISIBLE);

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoURI);
            videoView.requestFocus();
            videoView.start();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    private void download(){

        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoURL);
        storageReference.getMetadata().addOnCompleteListener(new OnCompleteListener<StorageMetadata>() {
            @SuppressLint("NewApi")
            @Override
            public void onComplete(@NonNull Task<StorageMetadata> task) {
                String fileName = task.getResult().getName();
                Log.d("File Download", fileName);
                File downloadLocation = null;
                if(type.equals("video")) {
                    downloadLocation = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_MOVIES),
                            "Firebase Messaging Videos");
                }

                if(type.equals("audio")) {
                    downloadLocation = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_MUSIC),
                            "Firebase Messaging Audio");
                }

                downloadLocation.mkdir();

                Log.d("Download", fileName);

                final File downloadFile = new File(downloadLocation,fileName);
                storageReference.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(VideoPlayerActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
