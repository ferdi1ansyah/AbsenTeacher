package com.example.miniproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Service.ApiInterface;
import com.example.miniproject.adapter.guruAdapter;
import com.example.miniproject.helper.ServiceGenerator;
import com.example.miniproject.model.Guru;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends Activity {
    ArrayList<Guru> guru;
    guruAdapter adapter;
    private ConstraintLayout mGuruLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        guru = new ArrayList<>();
        RecyclerView guruView = findViewById(R.id.rvGuru);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        guruView.setLayoutManager(layoutManager);

        adapter = new guruAdapter(guru);
        guruView.setAdapter(adapter);
        mGuruLayout = findViewById(R.id.guruLayout);
        doLoad();

    }

    public void doRecipe() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Guru>> call = service.listGuru();
        call.enqueue(new Callback<List<Guru>>() {
            @Override
            public void onResponse(Call<List<Guru>> call, Response<List<Guru>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        int id = response.body().get(i).getId();
                        String nama = response.body().get(i).getNama();
                        String alamat = response.body().get(i).getAlamat();
                        String jenisKelamin = response.body().get(i).getJenis_kelamin();
                        String telp = response.body().get(i).getNo_telp();
                        String username = response.body().get(i).getUsername();
                        String password = response.body().get(i).getPassword();
                        String foto = response.body().get(i).getFoto();
                        guru.add(new Guru(id, nama, alamat, jenisKelamin, telp, foto, username, password));
                    }
                    Snackbar snackbar = Snackbar.make(mGuruLayout, "Memuat data, Mohon ditunggu", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(mGuruLayout, "Gagal mengambil data", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<List<Guru>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(mGuruLayout, "Gagal koneksi", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    public void doLoad() {
        guru.clear();
        adapter.notifyDataSetChanged();

        doRecipe();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    public void handleUpload(View view) {
        Intent i = new Intent(AdminActivity.this, AdminActivity.class);
        startActivity(i);
    }
}
