package com.example.appveterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrarMascota extends AppCompatActivity {

    Spinner spAnimal, spRaza;
    EditText etNombreMascota, etColor;
    Button btnfotografia, btnRegistrarMascota;
    ImageView ivVisorfoto;
    RadioGroup rgGenero;
    int idcliente;

    final String URL = "http://192.168.1.15/appveterinaria/controllers/razas.php";

    private ArrayList<String> animales = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);
        loadUI();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            idcliente = parametros.getInt("idcliente");
        }
        listarAnimales();
    }
    private void listarAnimales(){
        Uri.Builder NEWURL = Uri.parse(URL).buildUpon();
        NEWURL.appendQueryParameter("operacion", "listarAnimal");
        String urlUpdate = NEWURL.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUpdate, null, new Response.Listener<JSONArray>() {
            @Override
                public void onResponse(JSONArray response) {
                    for(int i=0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String item = jsonObject.getString("nombreanimal");

                            animales.add(item);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistrarMascota.this, android.R.layout.simple_spinner_item, animales);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAnimal.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }
    private void loadUI(){
        //Spinner
        spAnimal = findViewById(R.id.spAnimal);
        spRaza = findViewById(R.id.spRaza);
        //EditText
        etNombreMascota = findViewById(R.id.etNombreMascota);
        etColor = findViewById(R.id.etColor);
        //Button
        btnfotografia = findViewById(R.id.btnfotografia);
        btnRegistrarMascota = findViewById(R.id.btnRegistrarMascota);
        //ImageView
        ivVisorfoto = findViewById(R.id.ivVisorfoto);
        //Radio Group
        rgGenero = findViewById(R.id.rgGenero);
    }
}