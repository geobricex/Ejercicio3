package com.example.ejercicio2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser;
    EditText txtPass;
    Button btnSend;
    RequestQueue requestQueue;

    private static final String URLJournal = "https://revistas.uteq.edu.ec/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initialize();

        requestQueue = Volley.newRequestQueue(this);//Initialize the RequestQueue object with Google Volley
    }

    public void btnSend(View view){
        stringRequestVolley(txtUser.getText().toString(), txtPass.getText().toString());//Method that returns a string from the web services response using a Google Volley
    }
    /**
     * Method that returns a string from
     * the web services response using a Google Volley
     */
    private void stringRequestVolley(String txtUser, String txtPass){
        //https://revistas.uteq.edu.ec/ws/login.php?usr=cristian&pass=cristian123
        StringRequest request= new StringRequest(Request.Method.GET, (URLJournal + "ws/login.php?usr="+ txtUser + "&pass="+txtPass), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                if(response.equals("Login Correcto!")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("Acceso", txtUser.toUpperCase());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "ERROR LOGIN IN GOOGLE VOLLEY", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
    /**
     * Method that initializes objects in declared variables
     */
    private void initialize(){
        txtUser =  findViewById(R.id.txtUser);

        txtPass =  findViewById(R.id.txtPass);

        btnSend = findViewById(R.id.btnLogin);
    }
}