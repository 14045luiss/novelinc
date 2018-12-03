package com.example.toshiba.a11_rest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toshiba.a11_rest.Model.PostPutDelReview;
import com.example.toshiba.a11_rest.Rest.ApiClient;
import com.example.toshiba.a11_rest.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarDetail extends AppCompatActivity {

    EditText edtIdReview, edtIdUser, edtidbuku, edtTanggalreview, edtIsireview;
    Button btInsert, btUpdate, btDelete, btBack;
    TextView tvMessage;
    ApiInterface mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_detail);

        edtIdReview = (EditText) findViewById(R.id.edtIdReview);
        edtIdUser = (EditText) findViewById(R.id.edtIdUser);
        edtidbuku = (EditText) findViewById(R.id.edtIdbuku);
        edtTanggalreview = (EditText) findViewById(R.id.edtIsireview);
        edtIsireview = (EditText) findViewById(R.id.edtTanggalreview);

        tvMessage = (TextView) findViewById(R.id.tvMessage2);

        btInsert = (Button) findViewById(R.id.btInsert2);
        btUpdate = (Button) findViewById(R.id.btUpdate2);
        btDelete = (Button) findViewById(R.id.btDelete2);
        btBack = (Button) findViewById(R.id.btBack);


        Intent mIntent = getIntent();
        edtIdReview.setText(mIntent.getStringExtra("id_review"));
        edtIdUser.setText(mIntent.getStringExtra("id_user"));
        edtidbuku.setText(mIntent.getStringExtra("id_buku"));
        edtTanggalreview.setText(mIntent.getStringExtra("tanggal_review"));
        edtIsireview.setText(mIntent.getStringExtra("isi_review"));

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<PostPutDelReview> updatePembelianCall = mApiInterface.putReview(
                        edtIdReview.getText().toString(),
                        edtIdUser.getText().toString(),
                        edtidbuku.getText().toString(),
                        edtTanggalreview.getText().toString(),
                        edtIsireview.getText().toString());

                updatePembelianCall.enqueue(new Callback<PostPutDelReview>() {
                    @Override
                    public void onResponse(Call<PostPutDelReview> call, Response<PostPutDelReview> response) {
                        tvMessage.setText(" Retrofit Update: " +
                                "\n " + " Status Update : " +response.body().getStatus() +
                                "\n " + " Message Update : "+ response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<PostPutDelReview> call, Throwable t) {
                        tvMessage.setText("Retrofit Update: \n Status Update :"+ t.getMessage());
                    }
                });
            }
        });

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<PostPutDelReview> postPembelianCall = mApiInterface.postReview(
                        edtIdReview.getText().toString(),
                        edtIdUser.getText().toString(),
                        edtidbuku.getText().toString(),
                        edtTanggalreview.getText().toString(),
                        edtIsireview.getText().toString());

                postPembelianCall.enqueue(new Callback<PostPutDelReview>() {
                    @Override
                    public void onResponse(Call<PostPutDelReview> call, Response<PostPutDelReview> response) {
                        tvMessage.setText(" Retrofit Insert: " +
                                "\n " + " Status Insert : " +
                                response.body().getStatus() +
                                "\n " + " Message Insert : "+ response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<PostPutDelReview> call, Throwable t) {
                        tvMessage.setText("Retrofit Insert: \n Status Insert :"+ t.getMessage());
                    }
                });
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtIdReview.getText().toString().trim().isEmpty()){

                    Call<PostPutDelReview> deletePembelian = mApiInterface.deleteReview(edtIdReview.getText().toString());
                    deletePembelian.enqueue(new Callback<PostPutDelReview>() {
                        @Override
                        public void onResponse(Call<PostPutDelReview> call, Response<PostPutDelReview> response) {
                            tvMessage.setText(" Retrofit Delete: " +
                                    "\n " + " Status Delete : " +response.body().getStatus() +
                                    "\n " + " Message Delete : "+ response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<PostPutDelReview> call, Throwable t) {
                            tvMessage.setText("Retrofit Delete: \n Status Delete :"+ t.getMessage());
                        }
                    });
                }else{
                    tvMessage.setText("id_review harus diisi");
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
            }
        });


    }
}
