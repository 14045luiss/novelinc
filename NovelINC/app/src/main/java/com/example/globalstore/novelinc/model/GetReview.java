package com.example.globalstore.novelinc.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetReview {


    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Review> listDataReview;
    @SerializedName("message")
    String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Review> getListDataReview() {
        return listDataReview;
    }

    public void setListDataReview(List<Review> listDataReview) {
        this.listDataReview = listDataReview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
