package com.example.toshiba.a11_rest.Rest;

import com.example.toshiba.a11_rest.Model.GetBuku;
import com.example.toshiba.a11_rest.Model.GetReview;
import com.example.toshiba.a11_rest.Model.PostPutDelReview;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiInterface {
    @GET("review/all")
    Call<GetReview> getReview();

    @FormUrlEncoded
    @POST("review/all")
    Call<PostPutDelReview>postReview(
            @Field("id_review") String idReview, @Field("id_buku") String idBuku,
            @Field("id_user") String idUser, @Field("isi_review") String isiReview, @Field("tanggal_review") String tanggalReview);

    @FormUrlEncoded
    @PUT("review/all")
    Call<PostPutDelReview> putReview(
            @Field("id_review") String idReview, @Field("id_buku") String idBuku,
            @Field("id_user") String idUser, @Field("isi_review") String isiReview, @Field("tanggal_review") String tanggalReview);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "review/all", hasBody = true)
    Call<PostPutDelReview> deleteReview(@Field("id_review") String idReview);



    @GET("buku/all")
    Call<GetBuku> getBuku();
    @Multipart
    @POST("buku/all")
    Call<GetBuku> postBuku(
            @Part MultipartBody.Part file,
            @Part("judul") RequestBody judul,
            @Part("penulis") RequestBody penulis,
            @Part("penerbit") RequestBody penerbit,
            @Part("tahun_terbit") RequestBody tahun_terbit,
            @Part("sinopsis") RequestBody sinopsis,
            @Part("action") RequestBody action
    );
    @Multipart
    @POST("buku/all")
    Call<GetBuku> putBuku(
            @Part MultipartBody.Part file,
            @Part("id_buku") RequestBody idBuku,
            @Part("judul") RequestBody judul,
            @Part("penulis") RequestBody penulis,
            @Part("penerbit") RequestBody penerbit,
            @Part("tahun_terbit") RequestBody tahun_terbit,
            @Part("sinopsis") RequestBody sinopsis,
            @Part("action") RequestBody action
    );
    @Multipart
    @POST("buku/all")
    Call<GetBuku> deleteBuku(
            @Part("id_buku") RequestBody idbuku,
            @Part("action") RequestBody action);
}
