package com.example.retrofit_app.network;

import com.example.retrofit_app.model.Alumno;
import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;

public interface GetDataService {
    @GET("todos")
    Call<List<Alumno>> getAlumnos();
}
