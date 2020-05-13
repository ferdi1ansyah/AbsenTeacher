package com.example.miniproject.Service;

import com.example.miniproject.model.Guru;
import com.example.miniproject.model.Login;
import com.example.miniproject.model.loginResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<loginResponse> doLogin(@Field("username") String username, @Field("password") String password);

    @GET("readGuru.php")
    Call<List<Guru>> listGuru();

    @Multipart
    @POST("insertGuru.php")
    Call<ResponseBody> doUpload(@Part MultipartBody.Part photo, @PartMap Map<String, RequestBody> text);

    @GET("readLogin.php")
    Call<List<Login>> listLogin(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("updateGuru.php")
    Call<Login> updateLogin(@Field("unique_id") String uid,
                            @Field("jam_logout") String jam);
}
