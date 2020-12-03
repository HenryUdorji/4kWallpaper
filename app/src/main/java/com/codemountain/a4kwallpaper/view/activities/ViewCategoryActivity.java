package com.codemountain.a4kwallpaper.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codemountain.a4kwallpaper.R;
import com.codemountain.a4kwallpaper.adapters.WallpaperPagedListAdapter;
import com.codemountain.a4kwallpaper.utils.SharedPref;
import com.codemountain.a4kwallpaper.viewModel.LatestViewModel;
import com.codemountain.a4kwallpaper.viewModel.ViewCategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.codemountain.a4kwallpaper.utils.Constants.CATEGORY;
import static com.codemountain.a4kwallpaper.utils.Constants.CATEGORY_NAME;
import static com.codemountain.a4kwallpaper.utils.Constants.STORAGE_CODE;

public class ViewCategoryActivity extends AppCompatActivity {

    private static final String TAG = "ViewCategoryActivity";
    private RecyclerView recyclerView;
    private ViewCategoryViewModel viewCategoryViewModel;
    private WallpaperPagedListAdapter pagedListAdapter;
    private Toolbar toolbar;
    private String name;

    //dialog widget
    private ImageView dialogImageView;
    private ImageButton dialogDownloadBtn, dialogShareBtn;
    private FloatingActionButton dialogCloseBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        Intent intent = getIntent();
        name = intent.getStringExtra(CATEGORY_NAME);
        //save category into sharedPref
        SharedPref.getSharedPrefInstance().putStringInPref(CATEGORY, name);

        initViews();
        fetchData();
    }

    private void fetchData() {
        //inject viewModel
        viewCategoryViewModel = new ViewModelProvider(this).get(ViewCategoryViewModel.class);
        pagedListAdapter = new WallpaperPagedListAdapter(this);

        viewCategoryViewModel.viewCategoryPagedList.observe(this, hits -> {
            pagedListAdapter.submitList(hits);
        });
        Log.e(TAG, "fetchData: hits submitted to adapter");
        pagedListAdapter.setOnItemClickListener((view, position) -> {
            showImageDialog(position);
            Log.e(TAG, "itemClick: clicked " + position);
        });
        recyclerView.setAdapter(pagedListAdapter);
        Log.e(TAG, "fetchData: adapter set");

    }

    private void initViews() {
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name.toUpperCase());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewCategoryActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Log.e(TAG, "initViews: views initialized");
    }

    private void showImageDialog(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_show_image);
        dialogImageView = dialog.findViewById(R.id.dialogImage);
        dialogDownloadBtn = dialog.findViewById(R.id.dialogDownload);
        dialogShareBtn = dialog.findViewById(R.id.dialogShare);
        dialogCloseBtn = dialog.findViewById(R.id.dialogClose);
        progressBar = dialog.findViewById(R.id.progressBar);

        dialogShareBtn.setOnClickListener(v -> Toast.makeText(this, "Feature coming soon...", Toast.LENGTH_SHORT).show());

        dialogCloseBtn.setOnClickListener(v -> dialog.dismiss());

        dialogDownloadBtn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, STORAGE_CODE);
                }
                else {
                    saveImage();
                }
            }
        });

        loadImageIntoDialog(position);
        dialog.show();
    }

    private void loadImageIntoDialog(int position) {
        String imageUrl = pagedListAdapter.getCurrentList().get(position).largeImageURL;
        Log.e(TAG, "loadImageIntoDialog: " + pagedListAdapter.getCurrentList().get(position).largeImageURL);
        Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(dialogImageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage() {
        Bitmap bitmap = ((BitmapDrawable)dialogImageView.getDrawable()).getBitmap();
        String time = String.valueOf(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path + "/Pictures/4kWallpaper");
        dir.mkdir();
        String fileName = "4kWallpaper_"+ time + ".jpg";
        File file = new File(dir, fileName);
        OutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Image saved ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(ViewCategoryActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}