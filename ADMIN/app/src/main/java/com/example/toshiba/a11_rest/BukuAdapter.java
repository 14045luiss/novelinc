package com.example.toshiba.a11_rest;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toshiba.a11_rest.Model.Buku;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {
    List<Buku> listBuku;
    public BukuAdapter(List<Buku> listBuku) {
        this.listBuku = listBuku;
    }
    @Override
    public BukuAdapter.BukuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buku, parent,false);
        BukuViewHolder mHolder = new BukuViewHolder(view);
        return mHolder;
    }
    @Override
    public void onBindViewHolder(BukuAdapter.BukuViewHolder holder, final int position) {
        holder.tvIdBuku.setText(" "+listBuku.get(position).getIdBuku());
        holder.tvJudul.setText(" "+listBuku.get(position).getJudul());
        holder.tvPenulis.setText(" "+listBuku.get(position).getPenulis());
        holder.tvPenerbit.setText(""+ listBuku.get(position).getPenerbit());
        holder.tvTahunTerbit.setText(" "+listBuku.get(position).getTahun_terbit());
        holder.tvSinopsis.setText(""+ listBuku.get(position).getSinopsis());
        if (listBuku.get(position).getPhotoUrl() != null ){

            //    Picasso.with(holder.itemView.getContext()).load(ApiClient.BASE_URL+listPembeli.get(position).getPhotoId()).into(holder.mPhotoURL);
            //Glide.with(holder.itemView.getContext()).load(listBuku.get(position).getPhotoUrl()).into(holder.mPhotoURL);
        } else {

            // Picasso.with(holder.itemView.getContext()).load(R.drawable.photoid).into(holder.mPhotoURL);
            Glide.with(holder.itemView.getContext()).load(R.drawable.default_buku).into(holder.mPhotoURL);
        }
    }
    @Override
    public int getItemCount() {
        return listBuku.size();
    }
    public class BukuViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoURL;
        TextView tvIdBuku, tvJudul, tvPenulis, tvPenerbit,  tvTahunTerbit,  tvSinopsis;

        public BukuViewHolder(View itemView) {
            super(itemView);
            mPhotoURL = (ImageView) itemView.findViewById(R.id.imgPhotoId);
            tvIdBuku = (TextView) itemView.findViewById(R.id.tvIdBuku);
            tvJudul = (TextView) itemView.findViewById(R.id.tvJudul);
            tvPenulis = (TextView) itemView.findViewById(R.id.tvPenulis);
            tvPenerbit = (TextView) itemView.findViewById(R.id.tvPenerbit);
            tvTahunTerbit = (TextView) itemView.findViewById(R.id.tvTahunterbit);
            tvSinopsis = (TextView) itemView.findViewById(R.id.tvSinopsis);

        }
    }
}

