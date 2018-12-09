package com.example.toshiba.a11_rest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.toshiba.a11_rest.Model.GetReview;
import com.example.toshiba.a11_rest.Model.Review;
import com.example.toshiba.a11_rest.Rest.ApiClient;
import com.example.toshiba.a11_rest.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   ImageView btGet;
    ApiInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGet = (ImageView) findViewById(R.id.btGet);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiInterface  = ApiClient.getClient().create(ApiInterface.class);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofit2.Call<GetReview> pembelianCall = mApiInterface.getReview();
                pembelianCall.enqueue(new Callback<GetReview>() {
                    @Override
                    public void onResponse(Call<GetReview> call, Response<GetReview> response) {
                        List<Review> reviewList = response.body().getListDataReview();
                        Log.d("Retrofit Get", "Jumlah data pembelian: " + String.valueOf(reviewList.size()));
                        mAdapter = new MyAdapter(reviewList, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<GetReview> call, Throwable t) {
                        // Log error
                        Log.e("Retrofit Get", t.toString());
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIntent;
        switch (item.getItemId()) {

            case R.id.MenuListReview:
                mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
                return true;

            case R.id.menuListBuku:
                mIntent = new Intent(this, LayarListBuku.class);
                startActivity(mIntent);
                return true;
            case R.id.menuInsertDataBuku:
                Intent intent = new Intent(this, LayarInsertBuku.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

