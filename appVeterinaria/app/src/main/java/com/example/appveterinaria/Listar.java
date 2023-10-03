package com.example.appveterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;

public class Listar extends AppCompatActivity {

    ListView lvMascotas;

    TextView tvNombreCompleto;
    Button btRegistrarMascota,btnCerrarSesion;

    private List<String> datalist = new ArrayList<>();
    private List<Integer> dataID = new ArrayList<>();
    int idcliente;
    String nombreCompleto;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        loadUI();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            idcliente = parametros.getInt("idcliente");
            nombreCompleto = "Bienvenid@: " + parametros.getString("nombreCompleto");
            obtenerDatos(idcliente);
        }

        tvNombreCompleto.setText(nombreCompleto);
        lvMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                abrirDetalle(dataID.get(position));
            }
        });
        btRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(idcliente);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }
    private void cerrarSesion(){
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);

        finishAffinity();
    }
    private void abrirDetalle(int idmascota){

        Intent intent = new Intent(getApplicationContext(), DetalleMascota.class);
        intent.putExtra("idmascota", idmascota);
        startActivity(intent);
    }
    private void obtenerDatos(int idcliente){
        dataID.clear();
        datalist.clear();
        adapter = new CustomAdapter(this, datalist);
        lvMascotas.setAdapter(adapter);

        Uri.Builder NEWURL = Uri.parse(Direccion.URLMascotas).buildUpon();
        NEWURL.appendQueryParameter("operacion", "listar");
        NEWURL.appendQueryParameter("idcliente", String.valueOf(idcliente));
        String urlUpdate = NEWURL.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUpdate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        datalist.add(jsonObject.getString("nombre"));
                        dataID.add(jsonObject.getInt("idmascota"));
                    }
                    adapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", toString());
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);


    }

    private void abrirActivity(int idcliente){
        Intent intent = new Intent(getApplicationContext(), RegistrarMascota.class);
        intent.putExtra("idcliente", idcliente);
        startActivity(intent);
    }
    private void loadUI(){
        lvMascotas = findViewById(R.id.lvMascotas);
        btRegistrarMascota = findViewById(R.id.btRegistrarMascota);
        tvNombreCompleto = findViewById(R.id.tvNombreCompleto);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
    }
}