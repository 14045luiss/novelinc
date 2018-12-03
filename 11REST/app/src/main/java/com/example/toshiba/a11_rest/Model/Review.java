package com.example.toshiba.a11_rest.Model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id_review")
    private int id_review;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("id_buku")
    private int id_buku;
    @SerializedName("isi_review")
    private String isi_review;
    @SerializedName("tanggal_review")
    private int tanggal_review;

    public Review(int id_review,int id_user, int id_buku, String isi_review, int tanggal_review) {
        this.id_review = id_review;
        this.id_user = id_user;
        this.id_buku = id_buku;
        this.isi_review = isi_review;
        this.tanggal_review = tanggal_review;
    }


    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public int getId_user() { return id_user; }

    public void setId_user(int id_user) { this.id_user = id_user; }

    public int getId_buku() {
        return id_buku;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public String getIsi_review() {
        return isi_review;
    }

    public void setIsi_review(String isi_review) {
        this.isi_review = isi_review;
    }

    public int getTanggal_review() {
        return tanggal_review;
    }

    public void setTanggal_review(int tanggal_review) {
        this.tanggal_review = tanggal_review;
    }

}
