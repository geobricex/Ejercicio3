package com.example.ejercicio2.interfaces;

import com.example.ejercicio2.models.Revistas;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://revistas.uteq.edu.ec/ws/issues.php?j_id=2

public interface RevistaAPI {
//    @GET("ws/issues.php/{j_id}")
    @GET("ws/issues.php")
    /**
     * Al llamar el objeto del ArrayJSON, por medio de esta Interfaz obtiene esos datos y lo almacena en un Array de tipo  Revistas (modelo)
     * para asi poder almacenar cada uno de los json del JSONArray
     */
    public Call<JsonArray> findJournal(@Query("j_id") String j_id);
}