package com.example.miniproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.R;
import com.example.miniproject.model.Login;

import java.util.List;

public class loginAdapter extends RecyclerView.Adapter<loginAdapter.ViewHolder> {

    List<Login> loginList;
    Context mContext;

    public loginAdapter(List<Login> loginList, Context mContext) {
        this.loginList = loginList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public loginAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_login, parent, false);
        return new ViewHolder(itemView);
    }

    public void setLoginList(List<Login> loginList) {
        this.loginList = loginList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull loginAdapter.ViewHolder holder, int position) {
        final Login itemLogin = loginList.get(position);
        holder.tvTanggal.setText(itemLogin.getTanggal());
        holder.tvJamlogin.setText(itemLogin.getJam_login());
        holder.tvJamlogout.setText(itemLogin.getJam_logout());

    }

    @Override
    public int getItemCount() {
        return loginList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal;
        TextView tvJamlogin;
        TextView tvJamlogout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.txt_tanggal);
            tvJamlogin = itemView.findViewById(R.id.txt_jam_login);
            tvJamlogout = itemView.findViewById(R.id.txt_jam_logout);
        }
    }

}
