package com.example.appveterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarMascota extends AppCompatActivity {

    Spinner spAnimal, spRaza;
    EditText etNombreMascota, etColor;
    Button btnfotografia, btnRegistrarMascota;
    ImageView ivVisorfoto;
    RadioGroup rgGenero;
    RadioButton rbHembra, rbMacho;

    int idcliente, idRaza;
    String nombreMascota, color, genero;

    final String URL = "http://192.168.1.15/appveterinaria/controllers/razas.php";
    final String URL2 = "http://192.168.1.15/appveterinaria/controllers/mascotas.php";

    private ArrayList<Animales> animales = new ArrayList<>();
    private ArrayList<Razas> razas = new ArrayList<>();

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
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    // Obtén el objeto seleccionado del Spinner de razas
                    Animales animalSeleccionado = animales.get(i);
                    // Obtén el valor de la raza seleccionada
                    int idanimal = animalSeleccionado.getValue();
                    Log.i("idseleccionado", String.valueOf(idanimal));
                    listarRazas(idanimal);
                }else{
                    Toast.makeText(getApplicationContext(), "Debes seleccionar un animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spRaza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Razas razaSeleccionada = razas.get(i);
                idRaza = razaSeleccionada.getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarFormulario();
            }
        });

    }

    private void validarFormulario(){
        nombreMascota = etNombreMascota.getText().toString().trim();
        color = etColor.getText().toString().trim();

        if (nombreMascota.isEmpty()){
            etNombreMascota.setError("Campo necesario");
        } else if (color.isEmpty()) {
            etColor.setError("campo necesario");
        }else {
            pregunta();
        }
    }

    private void pregunta(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria PATITAS");
        dialogo.setMessage("¿Desea registrar a su mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrar();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogo.show();
    }
    private void registrar(){
        if(rgGenero.getCheckedRadioButtonId() == R.id.rbMacho){
            genero = rbMacho.getText().toString();
        }else if(rgGenero.getCheckedRadioButtonId() == R.id.rbHembra){
            genero = rbHembra.getText().toString();
        }
        Toast.makeText(getApplicationContext(), genero + String.valueOf(idcliente) + String.valueOf(idRaza), Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Comunicacion exitosa



                if(response.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Registradi Correctamente, inicie sesion", Toast.LENGTH_SHORT).show();
//                    spAnimal.requestFocus();
                    Intent intent = new Intent(getApplicationContext(), Listar.class);
                    startActivity(intent);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("Error comunicación", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("operacion", "registrar");
                parametros.put("idcliente", String.valueOf(idcliente));
                parametros.put("idraza", String.valueOf(idRaza));
                parametros.put("nombre", nombreMascota);
                parametros.put("fotografia", "");
                parametros.put("color", color);
                parametros.put("genero", genero);

                return parametros;
            }
        };
        //Enviamos la solicitud al WS
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void listarRazas(int idanimal){
        Uri.Builder NEWURL = Uri.parse(URL).buildUpon();
        NEWURL.appendQueryParameter("operacion", "filtroRaza");
        NEWURL.appendQueryParameter("idanimal", String.valueOf(idanimal));
        if (razas.isEmpty()) {
            razas.add(new Razas(0, "Seleccione"));
        }
        String urlUpdate = NEWURL.build().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUpdate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int value = jsonObject.getInt("idraza");
                        String item = jsonObject.getString("nombreRaza");
                        //Log.d("animales", jsonObject.toString());
                        razas.add(new Razas(value, item));
                    }catch (JSONException e){
                        Log.d("Error spRazas",e.toString());
                    }
                }
                ArrayAdapter<Razas> adapter = new ArrayAdapter<>(RegistrarMascota.this, android.R.layout.simple_spinner_item, razas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaza.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void listarAnimales(){
        Uri.Builder NEWURL = Uri.parse(URL).buildUpon();
        NEWURL.appendQueryParameter("operacion", "listarAnimal");
        String urlUpdate = NEWURL.build().toString();
        if (animales.isEmpty()) {
            animales.add(new Animales(0, "Seleccione"));
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUpdate, null, new Response.Listener<JSONArray>() {
            @Override
                public void onResponse(JSONArray response) {

                    for(int i=0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int value = jsonObject.getInt("idanimal");
                            String item = jsonObject.getString("nombreamimal");
                            Log.d("animales", jsonObject.toString());

                            animales.add(new Animales(value, item));
                        }catch (JSONException e){
                            Log.d("Error en adapter",e.toString());
                        }
                    }
                    ArrayAdapter<Animales> adapter = new ArrayAdapter<>(RegistrarMascota.this, android.R.layout.simple_spinner_item, animales);
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
        ArrayAdapter<Razas> razasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, razas);
        razasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaza.setAdapter(razasAdapter);
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
        rbHembra = findViewById(R.id.rbHembra);
        rbMacho = findViewById(R.id.rbMacho);
    }
}