package com.example.globalstore.novelinc;

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

import com.example.globalstore.novelinc.Adapter.MyAdapter;
import com.example.globalstore.novelinc.Model.GetReview;
import com.example.globalstore.novelinc.Model.Review;
import com.example.globalstore.novelinc.Rest.ApiClient;
import com.example.globalstore.novelinc.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class list_review extends AppCompatActivity {
    Button btGet, btUpdate, btInsert, btDelete;
    ApiInterface mApiInterface;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review);

        btGet = (Button) findViewById(R.id.btGet);
//        btUpdate = (Button) findViewById(R.id.btUpdate);
//        btInsert = (Button) findViewById(R.id.btInsert);
//        btDelete = (Button) findViewById(R.id.btDelete);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetReview> reviewCall = mApiInterface.getReview();
                reviewCall.enqueue(new Callback<GetReview>() {
                    @Override
                    public void onResponse(Call<GetReview> call, Response<GetReview> response) {
                        List<Review> reviewList = response.body().getListDataReview();
                        Log.d("Retrofit Get", "Jumlah Data Review: " + String.valueOf(reviewList.size()));

                        mAdapter = new MyAdapter(reviewList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetReview> call, Throwable t) {
                        // Log error
                        Log.e("Retrofit Get", t.toString());
                    }
                });
            }
        });

//        btUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelBuku> updateBukuCall =
//                        mApiInterface.putBuku("2","Fabel Anak","Dini Puspita","Siaga","2007", "Buku Ini Laris" );
//                updateBukuCall.enqueue(new Callback<PostPutDelBuku>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelBuku> call, Response<PostPutDelBuku> response) {
//                        Log.d("Retrofit Update", "Status Update: " +
//                                String.valueOf(response.body().getStatus()));
//                    }
//                    @Override
//                    public void onFailure(Call<PostPutDelBuku> call, Throwable t) {
//                        Log.d("Retrofit Update", t.getMessage());
//                    }
//                });
//            }
//        });
//        btInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelBuku> postBukuCall = mApiInterface.postBuku("16",
//                        "Dilan", "Dinar", "ExC", "2013", "Buku ini Romantis" );
//                postBukuCall.enqueue(new Callback<PostPutDelBuku>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelBuku> call, Response<PostPutDelBuku> response) {
//                        Log.d("Retrofit Insert", "Status Insert: " +
//                                String.valueOf(response.body().getStatus()));
//                    }
//                    @Override
//                    public void onFailure(Call<PostPutDelBuku> call, Throwable t) {
//                        Log.d("Retrofit Insert", t.getMessage());
//                    }
//                });
//            }
//        });
//        btDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelBuku> deleteBuku = mApiInterface.deleteBuku("1");
//                deleteBuku.enqueue(new Callback<PostPutDelBuku>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelBuku> call,
//                                           Response<PostPutDelBuku> response) {
//                        Log.i("Retrofit Delete", "Status Delete: " +
//                                String.valueOf(response.body().getStatus()));
//                    }
//                    @Override
//                    public void onFailure(Call<PostPutDelBuku> call, Throwable t) {
//                        Log.i("Retrofit Delete", t.getMessage());
//                    }
//                });
//            }
//        });
   }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            Intent mIntent;
            switch (item.getItemId()) {
                case R.id.menuTambahNovel:
                    mIntent = new Intent(this, LayarInsertBuku.class);
                    startActivity(mIntent);
                    return true;
                case R.id.menuListBuku:
                    mIntent = new Intent(this, LayarListBuku.class);
                    startActivity(mIntent);
                    return true;
                default:
                 return super.onOptionsItemSelected(item);
//                case R.id.menuInsertDataBuku:
//                    Intent intent = new Intent(this, LayarInsertBuku.class);
//                    startActivity(intent);
//                    return true;
        }
    }
}

