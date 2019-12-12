package com.randstad.nasaapp;

import android.os.Bundle;

import com.randstad.nasaapp.model.NasaImage;
import com.randstad.nasaapp.network.adapters.NasaListAdapter;
import com.randstad.nasaapp.network.api.Methods;
import com.randstad.nasaapp.network.api.ServiceGenerator;
import com.randstad.nasaapp.utils.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MAIN_ACTIVITY";
    private ArrayList<NasaImage> imageList;
    private NasaListAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Methods service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        service = ServiceGenerator.createService(Methods.class);
        imageList = new ArrayList<NasaImage>();
        setAdapterData(imageList);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getImageList();
        });
    }

    private void getImageList() {
        mSwipeRefreshLayout.setRefreshing(true);
        service.getImageList(Constants.NETWORK.API_KEY).enqueue(new Callback<ArrayList<NasaImage>>() {
            @Override
            public void onResponse(Call<ArrayList<NasaImage>> call, Response<ArrayList<NasaImage>> response) {
                if (null != response.body()) {
                    imageList = response.body();

                    setAdapterData(imageList);
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<NasaImage>> call, Throwable t) {
                Log.e(TAG, "ERROR = " + t.getLocalizedMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setAdapterData(ArrayList<NasaImage> imageList) {
        adapter = new NasaListAdapter(getApplicationContext(), imageList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable("list", imageList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageList = (ArrayList<NasaImage>) savedInstanceState.getSerializable("list");
    }

    @Override
    public void onRefresh() {
        getImageList();
    }
}
