package com.example.miniproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Service.ApiInterface;
import com.example.miniproject.helper.ServiceGenerator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {

    private Uri imageUri;
    private Bitmap imageBitmap;
    private ImageButton foto;
    private String nama, alamat, jk, telp, username, password;
    private EditText namaTxt, alamatTxt, telpTxt, usernameTxt, passwordTxt;
    private RadioGroup radioGroup;
    private RadioButton selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        namaTxt = findViewById(R.id.edt_nama);
        alamatTxt = findViewById(R.id.edt_alamat);
        telpTxt = findViewById(R.id.edt_telp);
        usernameTxt = findViewById(R.id.edt_username);
        passwordTxt = findViewById(R.id.edt_password);
        foto = findViewById(R.id.imageButton);
        radioGroup = findViewById(R.id.group_jk);
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            showDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "_image.png");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle("Need Permission");
        builder.setMessage("Apk ini membutuhkan akses camera anda ");
        builder.setPositiveButton("BOLEH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openCamera();
            }
        });
        builder.setNegativeButton("JANGAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void doUpload() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        nama = namaTxt.getText().toString();
        alamat = alamatTxt.getText().toString();
        selected = findViewById(radioGroup.getCheckedRadioButtonId());
        jk = "";
        if (selected != null) {
            jk = selected.getText().toString();
        }
        telp = telpTxt.getText().toString();
        username = usernameTxt.getText().toString();
        password = passwordTxt.getText().toString();

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("fk_user", createPartFromString("1"));
        map.put("nama", createPartFromString(nama));
        map.put("alamat", createPartFromString(alamat));
        map.put("jenis_kelamin", createPartFromString(jk));
        map.put("no_telp", createPartFromString(telp));
        map.put("username", createPartFromString(username));
        map.put("password", createPartFromString(password));
        File file = createTempFile(imageBitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto", file.getName(), reqFile);

        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<ResponseBody> call = service.doUpload(body, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FormActivity.this, "Upload sukses", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FormActivity.this, AdminActivity.class);
                    startActivity(i);
                } else {

                    if (nama == null || nama.trim().length() == 0) {
                        Toast.makeText(FormActivity.this, "nama is required", Toast.LENGTH_SHORT).show();
                    } else if (alamat == null || alamat.trim().length() == 0) {
                        Toast.makeText(FormActivity.this, "alamat is required", Toast.LENGTH_SHORT).show();
                    } else if (telp == null || telp.trim().length() == 0) {
                        Toast.makeText(FormActivity.this, "telepon is required", Toast.LENGTH_SHORT).show();
                    } else if (username == null || username.trim().length() == 0) {
                        Toast.makeText(FormActivity.this, "username is required", Toast.LENGTH_SHORT).show();
                    } else if (password == null || password.trim().length() == 0) {
                        Toast.makeText(FormActivity.this, "password is required", Toast.LENGTH_SHORT).show();
                    } else if (foto == null) {
                        Toast.makeText(FormActivity.this, "foto is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FormActivity.this, "Koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == 1) {
            if (data != null) {
                imageUri = data.getData();
                imageBitmap = (Bitmap) data.getExtras().get("data");
                foto.setImageBitmap(imageBitmap);
            }
        }
    }

    public void handlePilihGambar(View view) {
        requestCameraPermission();
    }

    public void handleUploadBaru(View view) {
        if (imageBitmap != null) {
            doUpload();
        } else {
            Toast.makeText(this, "Capture image first", Toast.LENGTH_SHORT).show();
        }
    }
}
