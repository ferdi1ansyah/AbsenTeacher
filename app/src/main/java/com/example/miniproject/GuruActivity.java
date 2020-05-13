package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Service.ApiInterface;
import com.example.miniproject.adapter.loginAdapter;
import com.example.miniproject.helper.ServiceGenerator;
import com.example.miniproject.model.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Login> loginList;
    Button logout;
    String username;
    String id;
    private loginAdapter loginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        recyclerView = findViewById(R.id.rvLogin);
        logout = findViewById(R.id.buttonlogout);
        loginList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loginAdapter = new loginAdapter(loginList, getApplicationContext());
        recyclerView.setAdapter(loginAdapter);
        Bundle ekstra = getIntent().getExtras();
        username = ekstra.getString("nama");
        String password = ekstra.getString("password");
        id = ekstra.getString("id");
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Login>> call = service.listLogin(username, password);
        call.enqueue(new Callback<List<Login>>() {
            @Override
            public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                loginList = response.body();
                loginAdapter.setLoginList(loginList);

            }

            @Override
            public void onFailure(Call<List<Login>> call, Throwable t) {
                Toast.makeText(GuruActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void handleLogout(View view) {

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        String tgl_jam = sdf.format(dt);
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Login> call = service.updateLogin(id, tgl_jam);
        call.enqueue(new Callback<Login>() {

            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GuruActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuruActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(GuruActivity.this, "Gagal", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
