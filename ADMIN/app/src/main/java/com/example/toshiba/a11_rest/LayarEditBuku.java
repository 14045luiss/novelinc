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

public class LayarEditBuku extends AppCompatActivity {
    ImageView mPhotoUrl;
    EditText edtIdBuku, edtJudul, edtPenulis, edtPenerbit, edtTahunTerbit, edtSinopsis ;
    TextView tvMessage;
    Context mContext;
    ImageView btUpdate, btDelete, btBack;
    Button btPhotoUrl;
    String pathImage = "";
    Button mButtonPicture;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_edit_buku);

        mContext = getApplicationContext();
        mPhotoUrl = (ImageView) findViewById(R.id.imgPhotoId);
        edtIdBuku = (EditText) findViewById(R.id.edtIdbuku);
        edtJudul = (EditText) findViewById(R.id.edtJudul);
        edtPenulis = (EditText) findViewById(R.id.edtPenulis);
        edtPenerbit = (EditText) findViewById(R.id.edtPenerbit);
        edtTahunTerbit = (EditText) findViewById(R.id.edtTahunterbit);
        edtSinopsis = (EditText) findViewById(R.id.edtsinopsis);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        btUpdate = (ImageView) findViewById(R.id.btUpdate);
        btDelete = (ImageView) findViewById(R.id.btDelete);
        btBack = (ImageView) findViewById(R.id.btBack);
        btPhotoUrl = (Button) findViewById(R.id.btPhotoId);
        mButtonPicture = (Button) findViewById(R.id.btPhotoId);

        Intent mIntent = getIntent();
        edtIdBuku.setText(mIntent.getStringExtra("id_buku"));
        edtJudul.setText(mIntent.getStringExtra("judul"));
        edtPenulis.setText(mIntent.getStringExtra("penulis"));
        edtPenerbit.setText(mIntent.getStringExtra("penerbit"));
        edtTahunTerbit.setText(mIntent.getStringExtra("tahun_terbit"));
        edtSinopsis.setText(mIntent.getStringExtra("sinopsis"));

        // if (mIntent.getStringExtra("photo_url").length()>0) Picasso.with(mContext).load
// (ApiClient.BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
// else Picasso.with(mContext).load(R.drawable.photoid).into(mPhotoUrl);
        if (mIntent.getStringExtra("photo_url") != null)
            Glide.with(mContext).load(ApiClient.BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
        else
            Glide.with(mContext).load(R.drawable.default_user).into(mPhotoUrl);

        pathImage = mIntent.getStringExtra("photo_url");
        setListener();

        mButtonPicture.setOnClickListener(new View.OnClickListener() {
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

    private void setListener() {
        final ApiInterface mApiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultipartBody.Part body = null;
//dicek apakah image sama dengan yang ada di server atau berubah
//jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!pathImage.contains("upload/" + edtIdBuku.getText().toString()))
                        &&
                        (pathImage.length() > 0)) {
//File creating from selected URL
                    File file = new File(pathImage);
// create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("photo_url", file.getName(),
                            requestFile);
                }
                RequestBody reqIdBuku =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdBuku.getText().toString().isEmpty()) ?
                                        "" : edtIdBuku.getText().toString());
                RequestBody reqJudul =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtJudul.getText().toString().isEmpty()) ?
                                        "" : edtJudul.getText().toString());
                RequestBody reqPenulis =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtPenulis.getText().toString().isEmpty()) ?
                                        "" : edtPenulis.getText().toString());
                RequestBody reqPenerbit =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtPenerbit.getText().toString().isEmpty()) ?
                                        "" : edtPenerbit.getText().toString());
                RequestBody reqTahunTerbit =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtPenerbit.getText().toString().isEmpty()) ?
                                        "" : edtTahunTerbit.getText().toString());
                RequestBody reqSinopsis =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtSinopsis.getText().toString().isEmpty()) ?
                                        "" : edtSinopsis.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "update");
                Call<GetBuku> callUpdate = mApiInterface.putBuku(body,
                        reqIdBuku, reqJudul,reqPenulis, reqPenerbit,reqTahunTerbit, reqSinopsis, reqAction);
                callUpdate.enqueue(new Callback<GetBuku>() {
                    @Override
                    public void onResponse(Call<GetBuku> call, Response<GetBuku>
                            response) {
                        //Log.d("Update Retrofit ", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")) {
                            tvMessage.setText("Retrofit Update \n Status = " + response.body().getStatus() + "\n" + "Message = " + response.body().getMessage() + "\n");
                        } else {
                            String detail = "\n" +
                                    "id_buku= "+response.body().getResult().get(0).getIdBuku()+"\n "+
                                    "judul = "+response.body().getResult().get(0).getJudul()+"\n "+
                                    "penulis = "+response.body().getResult().get(0).getPenulis()+"\n "+
                                    "penerbit = "+response.body().getResult().get(0).getPenerbit()+"\n "+
                                    "tahun_terbit = "+response.body().getResult().get(0).getTahun_terbit()+"\n "+
                                    "sinopsis = "+response.body().getResult().get(0).getSinopsis()+"\n "+
                                    "photo_url = "+response.body().getResult().get(0).getPhotoUrl() + "\n";
                            tvMessage.setText("Retrofit Update \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = " + response.body().getMessage() + detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetBuku> call, Throwable t) {
//Log.d("Update Retrofit ", t.getMessage());
                        tvMessage.setText("Retrofit Update \n Status = " +
                                t.getMessage());
                    }
                });
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody reqIdBuku =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdBuku.getText().toString().isEmpty()) ?
                                        "" : edtIdBuku.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "delete");
                Call<GetBuku> callDelete =
                        mApiInterface.deleteBuku(reqIdBuku, reqAction);
                callDelete.enqueue(new Callback<GetBuku>() {
                    @Override
                    public void onResponse(Call<GetBuku> call, Response<GetBuku>
                            response) {
                        tvMessage.setText("Retrofit Delete \n Status = " +
                                response.body().getStatus() + "\n" +
                                "Message = " + response.body().getMessage() + "\n");
                    }

                    @Override
                    public void onFailure(Call<GetBuku> call, Throwable t) {
                        tvMessage.setText("Retrofit Delete \n Status = " +
                                t.getMessage());
                    }
                });
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tempIntent = new Intent(mContext, LayarListBuku.class);
                startActivity(tempIntent);
            }
        });
        btPhotoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih foto untuk " +
                        "di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10) {
            if (data == null) {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pathImage = cursor.getString(columnIndex);
//Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
                Glide.with(mContext).load(new File(pathImage)).into(mPhotoUrl);
                cursor.close();
            } else {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPhotoUrl.setImageBitmap(imageBitmap);
        }
    }

}
