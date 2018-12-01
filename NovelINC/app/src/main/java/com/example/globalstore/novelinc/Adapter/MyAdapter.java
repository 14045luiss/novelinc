package com.example.globalstore.novelinc.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.globalstore.novelinc.Model.Review;
import com.example.globalstore.novelinc.R;
import com.example.globalstore.novelinc.Rest.ApiClient;
import com.example.globalstore.novelinc.Rest.ApiInterface;
import com.example.globalstore.novelinc.list_review;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    List<Review> mReviewList;


    public MyAdapter(List<Review> reviewList) {
        mReviewList = reviewList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextViewIdReview.setText("Id Review   :  " + mReviewList.get(position)
                .getId_review());
        holder.mTextViewIdBuku.setText("Id Buku        :  " + mReviewList.get(position)
                .getId_buku());
        holder.mTextViewIsiReview.setText("Isi            :  " + mReviewList.get(position).getIsi_review());
        holder.mTextViewTanggalReview.setText("Tanggal      :  " + mReviewList.get(position).getTanggal_review());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), list_review.class);
                mIntent.putExtra("id_review",mReviewList.get(position).getId_review());
                mIntent.putExtra("id_buku",mReviewList.get(position).getId_buku());
                mIntent.putExtra("isi_review",mReviewList.get(position).getIsi_review());
                mIntent.putExtra("tanggal_review",mReviewList.get(position).getTanggal_review());
                view.getContext().startActivity(mIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewIdReview, mTextViewIdBuku, mTextViewIsiReview,mTextViewTanggalReview;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewIdReview = (TextView) itemView.findViewById(R.id.tvIdReview);
            mTextViewIdBuku = (TextView) itemView.findViewById(R.id.tvIdBuku);
            mTextViewIsiReview = (TextView) itemView.findViewById(R.id.tvIdIsiReview);
            mTextViewTanggalReview = (TextView) itemView.findViewById(R.id.tvTanggalReview);

        }
    }

}
