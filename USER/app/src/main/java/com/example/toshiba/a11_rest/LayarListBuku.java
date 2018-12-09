package com.example.toshiba.a11_rest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.toshiba.a11_rest.Model.Buku;
import com.example.toshiba.a11_rest.Model.GetBuku;
import com.example.toshiba.a11_rest.Rest.ApiClient;
import com.example.toshiba.a11_rest.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarListBuku extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    ImageView btGet, btAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_list_buku);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet = (ImageView) findViewById(R.id.btGet);
//        btAddData = (ImageView) findViewById(R.id.btAddData);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetBuku> mPembeliCall = mApiInterface.getBuku();
                mPembeliCall.enqueue(new Callback<GetBuku>() {
                    @Override
                    public void onResponse(Call<GetBuku> call, Response<GetBuku> response) {
                        Log.d("Get Buku",response.body().getStatus());
                        List<Buku> listBuku = response.body().getResult();
                        mAdapter = new BukuAdapter(listBuku);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetBuku> call, Throwable t) {
                        Log.d("Get Buku",t.getMessage());
                    }
                });
            }
        });

//        btAddData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, LayarInsertBuku.class);
//                startActivity(intent);
//            }
//        });

    }

}
