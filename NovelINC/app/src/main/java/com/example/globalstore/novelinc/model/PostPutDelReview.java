package com.example.globalstore.novelinc.Model;

import com.google.gson.annotations.SerializedName;

public class PostPutDelReview {
    @SerializedName("id_review")
    private String id_review;
    @SerializedName("id_buku")
    private String id_buku;
    @SerializedName("isi_review")
    private String isi_review;

    public String getId_review() {
        return id_review;
    }

    public void setId_review(String id_review) {
        this.id_review = id_review;
    }

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



}
