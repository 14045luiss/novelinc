package com.example.globalstore.novelinc.Rest;

import com.example.globalstore.novelinc.Model.GetBuku;
import com.example.globalstore.novelinc.Model.GetReview;
import com.example.globalstore.novelinc.Model.PostPutDelReview;

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
        //review
        @GET("review/user")
        Call<GetReview> getReview();

        @FormUrlEncoded
        @POST("review/user")
        Call<PostPutDelReview> postReview(
                 @Field("id_review") String idReview, @Field("id_buku") String idBuku,
                 @Field("isi_review") String isiReview, @Field("tanggal_review") String tanggalReview);

        @FormUrlEncoded
        @PUT("review/user")
        Call<PostPutDelReview> putReview(
                @Field("id_review") String idReview, @Field("id_buku") String idBuku,
                @Field("isi_review") String isiReview, @Field("tanggal_review") String tanggalReview);


        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "review/user", hasBody = true)
        Call<PostPutDelReview> deleteReview(@Field("id_Review") String idReview);



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
