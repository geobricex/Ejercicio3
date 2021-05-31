package com.example.ejercicio2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejercicio2.interfaces.RevistaAPI;
import com.example.ejercicio2.models.Revistas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView dataJSON;
    TextView txtID;
    ImageButton btnView;
    Button btnSingOff;
    Spinner option;
    RadioGroup rgButton;
    RadioButton rbMRetrofit;
    TextView lblGreetings;
//    RadioButton rbMVolley;

    RequestQueue requestQueue;
    private static final String URLJournal = "https://revistas.uteq.edu.ec/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Logs","STARTING THE APPLICATION");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);//Initialize the RequestQueue object with Google Volley

        initialize();//Method that initializes objects in declared variables

        Bundle bundle = this.getIntent().getExtras();

        lblGreetings.setText("Hola!, Bienvenido " + bundle.getString("Acceso"));

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.option, android.R.layout.simple_spinner_item);

        option.setAdapter(adapter);//Fill the Spinner

        btnView.setOnClickListener(v -> {
            findJournal(String.valueOf(option.getSelectedItemPosition()+1));
//                if (!txtID.getText().toString().equals("") && Integer.parseInt(txtID.getText().toString()) >0)
//                    findJournal(txtID.getText().toString());
//                else{
//                    Toast.makeText(MainActivity.this, "Fill in the required fields correctly", Toast.LENGTH_LONG).show();
//                    dataJSON.setText("");//empty container
//                }
        });
        btnSingOff.setOnClickListener(v ->{
            singOff();
        });
    }

    /**
     * It receives the identifier of the category and is in charge of being able to go through the Magazines type Array in order to add it to the Multiline
     */
    private  void findJournal(String j_id){
        if (rgButton.getCheckedRadioButtonId() == -1) { // no radio buttons are checked
            Toast.makeText(MainActivity.this, "Fill in the required fields correctly", Toast.LENGTH_LONG).show();
        } else { // one of the radio buttons is checked
            if (rbMRetrofit.isChecked()){
                Toast.makeText(MainActivity.this, "RETROFIT", Toast.LENGTH_SHORT).show();
                arrayRequestRetrofit(j_id);//Method that returns a string from the web services response using a Google Volley
            }else{
                Toast.makeText(MainActivity.this, "GOOGLE VOLLEY", Toast.LENGTH_SHORT).show();
//                stringRequestVolley(j_id);//Method that returns a string from the web services response using a Google Volley
                jsonArrayRequestVolley(j_id); //Method that returns a Json Array of the response from the web services using a Google Volley
            }
        }

    }

    /**
     * Method that returns a string from
     * the web services response using a Google Volley
     */
    private void arrayRequestRetrofit(String j_id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLJournal)//link where the web services query will be made
                .addConverterFactory(GsonConverterFactory.create()).build();

        RevistaAPI revistaAPI = retrofit.create(RevistaAPI.class);

        Call <Revistas[]> call = revistaAPI.findJournal(j_id);//Receives the interface call for the parameter searched

//      Call<Revistas> call = revistaAPI.find(j_id);//Be able to get from a single Json

        call.enqueue(new Callback<Revistas[]>() {//Journals
            /**
             * //Run if response arrives
             */
            @Override
            public void onResponse(Call<Revistas[]> call, Response <Revistas[]> response) {//Run if response arrives
                try {
                    if(response.isSuccessful()){//If the answer was correct
                        dataJSON.setText("");
                        Revistas[] revistas = response.body();//Revista
                        String JSON ="";
                        Log.i("Logs","Length: " + revistas.length);

                        for(int i = 0; i < revistas.length; i++){// get data from object
                            JSON = JSON + " issue_id: "+ revistas[i].getIssue_id();
                            JSON = JSON + "\n volume: "+ revistas[i].getVolume();
                            JSON = JSON + "\n number: "+ revistas[i].getNumber();
                            JSON = JSON + "\n year: "+ revistas[i].getYear();
                            JSON = JSON + "\n date_published: "+ revistas[i].getDate_published();
                            JSON = JSON + "\n title: "+ revistas[i].getTitle();
                            JSON = JSON + "\n doi: "+ revistas[i].getDoi();
                            JSON = JSON + "\n cover: "+ revistas[i].getCover() + "\n \n \n";
                        }

                        Log.i("Logs","valor: " + JSON);//Review in LogCar by regex SOUT
                        dataJSON.setText(JSON);// load the container
                    }
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    dataJSON.setText("");//empty container
                }
            }
            /**
             * R connection error or bad syntax
             */
            @Override
            public void onFailure(Call<Revistas[]> call, Throwable t) {// R connection error or bad syntax
                Toast.makeText(MainActivity.this, "ERROR IN RETROFIT", Toast.LENGTH_SHORT).show();
                dataJSON.setText("");//empty container
            }
        });
    }

    /**
     * Method that returns a string from
     * the web services response using a Google Volley
     */
    private void stringRequestVolley(String j_id){
        StringRequest  request= new StringRequest(Request.Method.GET, (URLJournal + "ws/issues.php?j_id="+ j_id), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataJSON.setText(response);// load the container
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR IN GOOGLE VOLLEY", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    /**
     * Method that returns a Json Array of the response from
     * the web services using a Google Volley
     */
    private void jsonArrayRequestVolley(String j_id){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,  (URLJournal + "ws/issues.php?j_id="+ j_id), null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String JSON ="";
                Log.i("Logs","Length: " + response.length());
                for (int i =0; i< response.length(); i++){
                    try {
                        JSONObject jsonObject = new JSONObject(response.get(i).toString());
                        JSON = JSON + " issue_id: "+ jsonObject.getString("issue_id");
                        JSON = JSON + "\n volume: "+ jsonObject.getString("volume");
                        JSON = JSON + "\n number: "+ jsonObject.getString("number");
                        JSON = JSON + "\n year: "+ jsonObject.getString("year");
                        JSON = JSON + "\n date_published: "+ jsonObject.getString("date_published");
                        JSON = JSON + "\n title: "+ jsonObject.getString("title");
                        JSON = JSON + "\n doi: "+ jsonObject.getString("doi");
                        JSON = JSON + "\n cover: "+ jsonObject.getString("cover") + "\n \n \n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Logs","valor: " + JSON);//Review in LogCar by regex SOUT
                dataJSON.setText(JSON);// load the container
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR IN GOOGLE VOLLEY", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Method that initializes objects in declared variables
     */
    private void initialize(){
        option = (Spinner) findViewById(R.id.spSelect);

        txtID =  findViewById(R.id.txtID);

        dataJSON =  findViewById(R.id.lblMView);

        btnView = findViewById(R.id.btnView);

        btnSingOff = findViewById(R.id.btnSingOff);

        rgButton = findViewById(R.id.rdG);

        rbMRetrofit = findViewById(R.id.rbM1);
//        rbMVolley = findViewById(R.id.rbM2);

        lblGreetings = findViewById(R.id.lblGreetings);
    }
    /**
     * Method to be able to close the session of
     * the mobile application
     */
    private void singOff(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Bundle b = new Bundle();
        b.putString("Acceso", "");
        intent.putExtras(b);
        startActivity(intent);
    }
}