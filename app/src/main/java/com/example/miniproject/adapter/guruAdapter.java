package com.example.miniproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.R;
import com.example.miniproject.model.Guru;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class guruAdapter extends RecyclerView.Adapter<guruAdapter.ViewHolder> {

    ArrayList<Guru> items;

    public guruAdapter(ArrayList<Guru> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public guruAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guru, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull guruAdapter.ViewHolder holder, int position) {
        Guru item = items.get(position);
        holder.idText.setText(Integer.toString(item.getId()));
        holder.namaText.setText(item.getNama());
        holder.alamatText.setText(item.getAlamat());
        holder.jkText.setText(item.getJenis_kelamin());
        holder.telpText.setText(item.getNo_telp());
        holder.username.setText(item.getUsername());
        holder.password.setText(item.getUsername());
        String url = "/images/" + item.getFoto();
        Picasso.get().load(url).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idText, namaText, alamatText, jkText, telpText, username, password;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.id_guru);
            namaText = itemView.findViewById(R.id.nama_guru);
            alamatText = itemView.findViewById(R.id.alamat_guru);
            jkText = itemView.findViewById(R.id.jenis_kelamin_guru);
            telpText = itemView.findViewById(R.id.telp_guru);
            foto = itemView.findViewById(R.id.foto_profil);
            username = itemView.findViewById(R.id.username_guru);
            password = itemView.findViewById(R.id.password_guru);

        }
    }

}
