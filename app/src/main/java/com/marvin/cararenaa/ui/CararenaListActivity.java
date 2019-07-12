package com.marvin.cararenaa.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marvin.cararenaa.R;
import com.marvin.cararenaa.adapters.CararenaListAdapter;
import com.marvin.cararenaa.models.Carzarena;
import com.marvin.cararenaa.services.MarketCheckService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CararenaListActivity extends AppCompatActivity {
    public static final String TAG = CararenaListActivity.class.getSimpleName();

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    private CararenaListAdapter mAdapter;

    public ArrayList<Carzarena> carzarenas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String build = intent.getStringExtra("build");

        getRestaurants(build);
    }

    private void getRestaurants(String build) {
        final MarketCheckService marketCheckService = new MarketCheckService();

        marketCheckService.findcars(build, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                carzarenas = marketCheckService.processResults(response);

                CararenaListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new CararenaListAdapter(getApplicationContext(), carzarenas);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(CararenaListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}