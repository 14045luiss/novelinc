package com.example.globalstore.novelinc.Model;

import com.google.gson.annotations.SerializedName;

public class Buku {


    @SerializedName("id_buku")
    private String idBuku;
    @SerializedName("judul")
    private String judul;
    @SerializedName("penulis")
    private String penulis;
    @SerializedName("penerbit")
    private String penerbit;
    @SerializedName("tahun_terbit")
    private int tahun_terbit;
    @SerializedName("sinopsis")
    private String sinopsis;
    @SerializedName("photo_url")
    private String photoUrl;
    private String photoId;
    private String action;

    public Buku(String idBuku, String judul, String penulis, String penerbit, int tahun_terbit, String sinopsis, String photoUrl, String action, String photoId) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahun_terbit = tahun_terbit;
        this.sinopsis = sinopsis;
        this.photoUrl = photoUrl;
        this.action = action;
    }
    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_terbit(int tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    }



