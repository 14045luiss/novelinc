package com.example.toshiba.a11_rest.Model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id_review")
    private String id_review;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_buku")
    private String id_buku;
    @SerializedName("isi_review")
    private String isi_review;
    @SerializedName("tanggal_review")
    private String tanggal_review;

    public Review(String id_review,String id_user, String id_buku, String isi_review, String tanggal_review) {
        this.id_review = id_review;
        this.id_user = id_user;
        this.id_buku = id_buku;
        this.isi_review = isi_review;
        this.tanggal_review = tanggal_review;
    }


    public String getId_review() {
        return id_review;
    }

    public void setId_review(String id_review) {
        this.id_review = id_review;
    }

    public String getId_user() { return id_user; }

    public void setId_user(String id_user) { this.id_user = id_user; }

    public String getId_buku() {
        return id_buku;
    }

    public void setId_buku(String id_buku) {
        this.id_buku = id_buku;
    }

    public String getIsi_review() {
        return isi_review;
    }

    public void setIsi_review(String isi_review) {
        this.isi_review = isi_review;
    }

    public String getTanggal_review() {
        return tanggal_review;
    }

    public void setTanggal_review(String tanggal_review) {
        this.tanggal_review = tanggal_review;
    }

}
