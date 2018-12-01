package com.example.globalstore.novelinc;

import android.content.Intent;
import android.database.Cursor;
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

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarEditBuku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_edit_buku);
        mContext = getApplicationContext();
        mPhotoUrl = (ImageView) findViewById(R.id.imgPhotoId);
        edtIdPembeli = (EditText) findViewById(R.id.edtIdPembeli);
        edtNama = (EditText) findViewById(R.id.edtNamaPembeli);
        edtAlamat = (EditText) findViewById(R.id.edtAlamatPembeli);
        edtTelp = (EditText) findViewById(R.id.edtTelpnPembeli);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        btUpdate = (ImageView) findViewById(R.id.btUpdate);
        btDelete = (ImageView) findViewById(R.id.btDelete);
        btBack = (ImageView) findViewById(R.id.btBack);
        btPhotoUrl = (Button) findViewById(R.id.btPhotoUrl);
        Intent mIntent = getIntent();
        edtIdPembeli.setText(mIntent.getStringExtra("id_pembeli"));
        edtNama.setText(mIntent.getStringExtra("nama"));
        edtAlamat.setText(mIntent.getStringExtra("alamat"));
        edtTelp.setText(mIntent.getStringExtra("telp"));


        // if (mIntent.getStringExtra("photo_url").length()>0) Picasso.with(mContext).load
// (ApiClient.BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
// else Picasso.with(mContext).load(R.drawable.photoid).into(mPhotoUrl);
        if (mIntent.getStringExtra("photo_url") != null)
            Glide.with(mContext).load(ApiClient.BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
        else
            Glide.with(mContext).load(R.drawable.default_user).into(mPhotoUrl);

        pathImage = mIntent.getStringExtra("photo_url");
        setListener();
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
                if ((!pathImage.contains("upload/" + edtIdPembeli.getText().toString()))
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
                RequestBody reqIdPembeli =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdPembeli.getText().toString().isEmpty()) ?
                                        "" : edtIdPembeli.getText().toString());
                RequestBody reqNama =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtNama.getText().toString().isEmpty()) ?
                                        "" : edtNama.getText().toString());
                RequestBody reqAlamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtAlamat.getText().toString().isEmpty()) ?
                                        "" : edtAlamat.getText().toString());
                RequestBody reqTelp =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtTelp.getText().toString().isEmpty()) ?
                                        "" : edtTelp.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "update");
                Call<GetPembeli> callUpdate = mApiInterface.putPembeli(body,
                        reqIdPembeli, reqNama,
                        reqAlamat, reqTelp, reqAction);
                callUpdate.enqueue(new Callback<GetPembeli>() {
                    @Override
                    public void onResponse(Call<GetPembeli> call, Response<GetPembeli>
                            response) {
                        //Log.d("Update Retrofit ", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")) {
                            tvMessage.setText("Retrofit Update \n Status = " + response.body().getStatus() + "\n" + "Message = " + response.body().getMessage() + "\n");
                        } else {
                            String detail = "\n" +
                                    "id_pembeli = "+response.body().getResult().get(0).getIdPembeli()+"\n "+
                                    "nama = "+response.body().getResult().get(0).getNama()+"\n "+
                                    "alamat = "+response.body().getResult().get(0).getAlamat()+"\n "+
                                    "telp = "+response.body().getResult().get(0).getTelp()+"\n "+
                                    "photo_url = "+response.body().getResult().get(0).getPhotoUrl() + "\n";
                            tvMessage.setText("Retrofit Update \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = " + response.body().getMessage() + detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetPembeli> call, Throwable t) {
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
                RequestBody reqIdPembeli =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdPembeli.getText().toString().isEmpty()) ?
                                        "" : edtIdPembeli.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "delete");
                Call<GetPembeli> callDelete =
                        mApiInterface.deletePembeli(reqIdPembeli, reqAction);
                callDelete.enqueue(new Callback<GetPembeli>() {
                    @Override
                    public void onResponse(Call<GetPembeli> call, Response<GetPembeli>
                            response) {
                        tvMessage.setText("Retrofit Delete \n Status = " +
                                response.body().getStatus() + "\n" +
                                "Message = " + response.body().getMessage() + "\n");
                    }

                    @Override
                    public void onFailure(Call<GetPembeli> call, Throwable t) {
                        tvMessage.setText("Retrofit Delete \n Status = " +
                                t.getMessage());
                    }
                });
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tempIntent = new Intent(mContext, LayarListPembeli.class);
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
    }
}

    }
}
