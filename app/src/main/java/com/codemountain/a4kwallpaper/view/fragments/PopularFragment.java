package com.codemountain.a4kwallpaper.view.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.codemountain.a4kwallpaper.interfaces.OnItemClickListener;
import com.codemountain.a4kwallpaper.viewModel.PopularViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.codemountain.a4kwallpaper.utils.Constants.STORAGE_CODE;

public class PopularFragment extends Fragment {

    private static final String TAG = "PopularFragment";
    private RecyclerView recyclerView;
    private PopularViewModel popularViewModel;
    private WallpaperPagedListAdapter pagedListAdapter;

    //dialog widget
    private ImageView dialogImageView;
    private ImageButton dialogDownloadBtn, dialogShareBtn;
    private FloatingActionButton dialogCloseBtn;
    private ProgressBar progressBar;

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        initViews(view);
        fetchData();
        return view;
    }

    private void fetchData() {
        if (getActivity() == null) {
            return;
        }
        //inject viewModel
        popularViewModel = new ViewModelProvider(getActivity()).get(PopularViewModel.class);
        pagedListAdapter = new WallpaperPagedListAdapter(getActivity());

        popularViewModel.popularPagedList.observe(getActivity(), hits -> {
            pagedListAdapter.submitList(hits);
        });
        pagedListAdapter.setOnItemClickListener((view, position) -> showImageDialog(position));
        recyclerView.setAdapter(pagedListAdapter);
    }

    private void initViews(View view) {
        //recyclerview
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void showImageDialog(int position) {
        if(getContext() == null){
            return;
        }
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_show_image);
        dialogImageView = dialog.findViewById(R.id.dialogImage);
        dialogDownloadBtn = dialog.findViewById(R.id.dialogDownload);
        dialogShareBtn = dialog.findViewById(R.id.dialogShare);
        dialogCloseBtn = dialog.findViewById(R.id.dialogClose);
        progressBar = dialog.findViewById(R.id.progressBar);

        dialogShareBtn.setOnClickListener(v -> Toast.makeText(getActivity(), "Feature coming soon...", Toast.LENGTH_SHORT).show());

        dialogCloseBtn.setOnClickListener(v -> dialog.dismiss());

        dialogDownloadBtn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
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
        if (getContext() == null){
            return;
        }
        String imageUrl = pagedListAdapter.getCurrentList().get(position).largeImageURL;
        Log.e(TAG, "loadImageIntoDialog: " + pagedListAdapter.getCurrentList().get(position).largeImageURL);
        Glide.with(getContext()).load(imageUrl)
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
                Toast.makeText(getContext(), "storage permission denied", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Image saved ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}