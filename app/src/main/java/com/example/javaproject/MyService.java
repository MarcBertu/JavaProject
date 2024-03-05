package com.example.javaproject;

import com.example.javaproject.model.Animal;
import com.example.javaproject.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyService {
    @GET("list/all")
    Call<ResponseModel> getAll();
}
