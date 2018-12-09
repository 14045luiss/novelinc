package com.example.toshiba.a11_rest.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReview {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Review> listDataReview;
    @SerializedName("message")
    String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Review> getListDataReview() {
        return listDataReview;
    }

    public void setListDataReview(List<Review> listDataReview) {
        this.listDataReview = listDataReview;
    }

}
