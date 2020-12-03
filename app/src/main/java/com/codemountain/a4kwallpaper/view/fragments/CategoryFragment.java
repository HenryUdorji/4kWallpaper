package com.codemountain.a4kwallpaper.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codemountain.a4kwallpaper.R;
import com.codemountain.a4kwallpaper.adapters.CategoryAdapter;
import com.codemountain.a4kwallpaper.interfaces.OnItemClickListener;
import com.codemountain.a4kwallpaper.model.Category;
import com.codemountain.a4kwallpaper.view.activities.ViewCategoryActivity;

import java.util.ArrayList;
import java.util.List;

import static com.codemountain.a4kwallpaper.utils.Constants.CATEGORY_NAME;

public class CategoryFragment extends Fragment {
    private static final String TAG = "CategoryFragment";
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    public static List<Category> categoryList = new ArrayList<>();

    public CategoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        initViews(view);
        new LoadData().execute("");
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class LoadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            categoryList.clear();
            categoryList.add(new Category("Nature", R.drawable.ic_nature));
            categoryList.add(new Category("Sports", R.drawable.category_placeholder));
            categoryList.add(new Category("Computer", R.drawable.ic_computer));
            categoryList.add(new Category("Animals", R.drawable.category_placeholder));
            categoryList.add(new Category("Buildings", R.drawable.ic_building));
            categoryList.add(new Category("Fashion", R.drawable.category_placeholder));
            categoryList.add(new Category("Food", R.drawable.ic_food));
            categoryList.add(new Category("Backgrounds", R.drawable.category_placeholder));
            categoryList.add(new Category("Places", R.drawable.category_placeholder));
            categoryList.add(new Category("Transportation", R.drawable.category_placeholder));
            categoryList.add(new Category("Travel", R.drawable.category_placeholder));
            categoryList.add(new Category("People", R.drawable.ic_people));

            adapter = new CategoryAdapter(getActivity(), categoryList);
            Log.e(TAG, "doInBackground: adapter loaded " + categoryList.size());
            return "Execute";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.setOnItemClickListener((view, position) -> {
                String name = categoryList.get(position).getCategoryName().toLowerCase();
                startActivity(new Intent(getActivity(), ViewCategoryActivity.class)
                .putExtra(CATEGORY_NAME, name));
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            });
            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onPostExecute: recyclerView setup with adapter");
        }
    }

    private void initViews(View view) {
        //recyclerview
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e(TAG, "initViews: views initialized");
    }
}