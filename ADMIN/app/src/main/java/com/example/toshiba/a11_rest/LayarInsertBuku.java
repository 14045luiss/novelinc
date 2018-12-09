package com.example.toshiba.a11_rest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.a11_rest.Model.GetBuku;
import com.example.toshiba.a11_rest.Rest.ApiClient;
import com.example.toshiba.a11_rest.Rest.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarInsertBuku extends AppCompatActivity {
    Context mContext;
    ImageView mImageView;
    ImageView btAddPhotoId;
    ImageView btAddBack, btAddData;
    EditText edtAddJudulBuku, edtAddPenulisBuku, edtAddPenerbitBuku, edtAddTahunTerbitBuku, edtAddSinopsisBuku;
    TextView tvAddMessage;
    String imagePath = "";
    Button btnCaptures;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_buku);


        mContext = getApplicationContext();
        mImageView = (ImageView) findViewById(R.id.imgAddPhotoId);
        btAddPhotoId = (ImageView) findViewById(R.id.btAddPhotoId);
        edtAddJudulBuku = (EditText) findViewById(R.id.edtAddJudul);
        btnCaptures = (Button) findViewById(R.id.btnCapture);
        edtAddPenulisBuku = (EditText) findViewById(R.id.edtAddpenulis);
        edtAddPenerbitBuku = (EditText) findViewById(R.id.edtAddpenerbit);
        edtAddTahunTerbitBuku = (EditText) findViewById(R.id.edtAddtahunterbit);
        edtAddSinopsisBuku = (EditText) findViewById(R.id.edtAddsinopsis);
        btAddData = findViewById(R.id.btAddData);
        btAddBack = findViewById(R.id.btAddBack);
        tvAddMessage = (TextView) findViewById(R.id.tvAddMessage);
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface =
                        ApiClient.getClient().create(ApiInterface.class);
                MultipartBody.Part body = null;
                if (!imagePath.isEmpty()) {
// Buat file dari image yang dipilih
                    File file = new File(imagePath);
                    // Buat RequestBody instance dari file
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("image/jpg"), file);
// MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("photo_url", file.getName(),
                            requestFile);
                }
                RequestBody reqJudul =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAddJudulBuku.getText().toString().isEmpty()) ? "" : edtAddJudulBuku.getText().toString());
                RequestBody reqPenulis =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAddPenulisBuku.getText().toString().isEmpty()) ? "" : edtAddPenulisBuku.getText().toString());
                RequestBody reqPenerbit =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAddPenerbitBuku.getText().toString().isEmpty()) ? "" : edtAddPenerbitBuku.getText().toString());
                RequestBody reqTahunTerbit =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAddTahunTerbitBuku.getText().toString().isEmpty()) ? "" : edtAddTahunTerbitBuku.getText().toString());
                RequestBody reqSinopsis =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAddSinopsisBuku.getText().toString().isEmpty()) ? "" : edtAddSinopsisBuku.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "insert");
                Call<GetBuku> mBukuCall = mApiInterface.postBuku(body, reqJudul, reqPenulis, reqPenerbit, reqTahunTerbit, reqSinopsis, reqAction);
                mBukuCall.enqueue(new Callback<GetBuku>() {
                    @Override
                    public void onResponse(Call<GetBuku> call, Response<GetBuku>
                            response) {
                        //Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getStatus().equals("failed")) {
                            tvAddMessage.setText("Retrofit Insert \n Status = " + response.body()
                                    .getStatus() + "\n" + "Message = " + response.body().getMessage() + "\n");
                        } else {
                            String detail = "\n" +

                                    "id_buku = " + response.body().getResult().get(0).getIdBuku() + "\n " +
                                    "judul = " + response.body().getResult().get(0).getJudul() + "\n " +
                                    "penulis = " + response.body().getResult().get(0).getPenulis() + "\n " +
                                    "penerbit = " + response.body().getResult().get(0).getPenerbit() + "\n " +
                                    "tahun_terbit = " + response.body().getResult().get(0).getTahun_terbit() + "\n " +
                                    "sinopsis = " + response.body().getResult().get(0).getSinopsis() + "\n " +
                                    "photo_url = " + response.body().getResult().get(0).getPhotoUrl() + "\n";


                            tvAddMessage.setText("Retrofit Insert \n Status = " + response.body().getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetBuku> call, Throwable t) {
                        //Log.d("Insert Retrofit", t.getMessage());
                        tvAddMessage.setText("Retrofit Insert Failure \n Status = " + t.getMessage());
                    }
                });
            }
        });
        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LayarListBuku.class);
                startActivity(intent);
            }
        });


        btAddPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(
                        galleryIntent,
                        "Pilih foto untuk di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });


        btnCaptures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Camera di device anda tidak tersedia", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage(){
        Intent takePictureIntent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, 100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10){
            if (data==null){
                Toast.makeText(mContext, "Foto gagal di-load",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath =cursor.getString(columnIndex);
//               Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
                Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                cursor.close();
            }else{
                Toast.makeText(mContext, "Foto gagal di-load",
                        Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}