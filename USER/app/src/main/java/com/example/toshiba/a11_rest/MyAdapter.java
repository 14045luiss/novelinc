package com.example.toshiba.a11_rest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.a11_rest.Model.Review;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Review> mReview;
    Context mContext;

    public MyAdapter(List<Review> reviewList, Context context) {
        mReview = reviewList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mTextViewIdPembelian.setText(" "+ mReview.get(position).getId_review());
        holder.mTextViewIdPembeli.setText(" "+mReview.get(position).getId_user());
        holder.mTextViewIdTiket.setText(" "+ mReview.get(position).getId_buku());
        holder.mTextViewTanggal.setText(" "+mReview.get(position).getTanggal_review());
        holder.mTextViewTotalHarga.setText(" "+ mReview.get(position).getIsi_review());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LayarDetail.class);
                intent.putExtra("id_review", mReview.get(position).getId_review());
                intent.putExtra("id_user", mReview.get(position).getId_user());
                intent.putExtra("id_buku", mReview.get(position).getId_buku());
                intent.putExtra("isi_review", mReview.get(position).getIsi_review());
                intent.putExtra("tanggal_review",String.valueOf(mReview.get(position).getTanggal_review()));

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView mTextViewIdPembelian, mTextViewIdPembeli, mTextViewTanggal,mTextViewIdTiket,mTextViewTotalHarga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            mTextViewIdPembelian = (TextView) itemView.findViewById(R.id.tvIdReview);
            mTextViewIdPembeli = (TextView) itemView.findViewById(R.id.tvIdUser);
            mTextViewTanggal = (TextView) itemView.findViewById(R.id.tvTanggalreview);
            mTextViewIdTiket = (TextView) itemView.findViewById(R.id.tvIdBuku);
            mTextViewTotalHarga = (TextView) itemView.findViewById(R.id.tvIsireview);

        }
    }
}
