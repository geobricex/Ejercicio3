package com.example.ejercicio2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ejercicio2.models.ListElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<ListElement> listElementList;
    JsonArray JSONdata;
    Button btnidRetun;
    TextView idTituloRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initialize();
        JsonParser parser = new JsonParser();
        Bundle bundle = this.getIntent().getExtras();
        JSONdata = parser.parse(bundle.getString("Data")).getAsJsonArray();
        idTituloRevista.setText(bundle.getString("Review"));
        btnidRetun.setOnClickListener(v -> {
            returnActivity();
        });
        init();
    }

    public void initialize() {
        btnidRetun = findViewById(R.id.btnidRetun);
        idTituloRevista =  findViewById(R.id.idTituloRevista);
    }

    public void returnActivity() {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void init() {
        Log.i("Logs", "RecycleView CardView");//Review in LogCar by regex SOUT
        listElementList = new ArrayList<>();
//        loadStatic();// for load static data
        Log.i("Logs", "JSON:" + JSONdata);
        try {
            if (JSONdata.size() > 0) {
                for (int i = 0; i < JSONdata.size(); i++) {
                    JSONObject jsonObject = new JSONObject(JSONdata.get(i).toString());
                    listElementList.add(new ListElement(jsonObject.getString("cover"), jsonObject.getString("title"), jsonObject.getString("doi"), jsonObject.getString("date_published"), jsonObject.getString("year"), jsonObject.getString("volume")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        ListAdapter listAdapter = new ListAdapter(listElementList, this);
        RecyclerView recyclerView = findViewById(R.id.idRecyView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void loadStatic() {
        //String color, String titulo, String doi, String fecha, String anio, String volumen
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "1", "2", "3", "4", "5"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "a", "b", "c", "d", "e"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "6", "7", "8", "9", "10"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "f", "g", "h", "i", "j"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "11", "12", "13", "14", "15"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "k", "l", "m", "n", "ñ"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "16", "17", "18", "19", "20"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "o", "p", "q", "r", "s"));
        listElementList.add(new ListElement("https://revistas.uteq.edu.ec/public/journals/2/cover_issue_30_es_ES.jpg", "21", "22", "23", "24", "25"));

    }
}