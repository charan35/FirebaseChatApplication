package com.jayaraj.firebasechatapplication.view;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.jayaraj.firebasechatapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private ImageView download;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        mImageView = (TouchImageView) findViewById(R.id.imageView);
        download=findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String urlPhotoClick;
        user=getIntent().getStringExtra("user");

        if (user.equals("friend")){
            download.setVisibility(View.VISIBLE);
        }
        else{
            download.setVisibility(View.GONE);
        }

        urlPhotoClick = getIntent().getStringExtra("urlPhotoClick");

        Glide.with(this)
                .load(urlPhotoClick)
                .asBitmap()
                .override(640,640).fitCenter()
                .into(mImageView);
    }

    private void download(){

        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl( getIntent().getStringExtra("urlPhotoClick"));
        storageReference.getMetadata().addOnCompleteListener(new OnCompleteListener<StorageMetadata>() {
            @SuppressLint("NewApi")
            @Override
            public void onComplete(@NonNull Task<StorageMetadata> task) {
                String fileName = task.getResult().getName();
                Log.d("File Download", fileName);
                File downloadLocation = null;

                downloadLocation = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                        "Firebase Messaging Images");

                downloadLocation.mkdir();

                Log.d("Download", fileName);

                final File downloadFile = new File(downloadLocation,fileName);
                storageReference.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(FullScreenImageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                storageReference.getFile(downloadFile).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FullScreenImageActivity.this, "Failed to Download", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
