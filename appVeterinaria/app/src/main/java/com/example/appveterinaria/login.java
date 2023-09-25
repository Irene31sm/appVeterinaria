package com.example.appveterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText etdni, etClave;
    Button btAcceder, btRegistrase;
    String dni, clave;
    int idCliente;
    final String URL = "http://192.168.1.28/appveterinaria/controllers/clientes.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadUI();
        btAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iniciarSesion();
            }
        });

        btRegistrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirRegistro();
            }
        });

    }
    private void iniciarSesion(){
        dni = etdni.getText().toString().trim();
        clave = etClave.getText().toString().trim();

        Uri.Builder urlnueva = Uri.parse(URL).buildUpon();
        urlnueva.appendQueryParameter("operacion", "inicioSesion");
        urlnueva.appendQueryParameter("dni", dni);
        urlnueva.appendQueryParameter("claveAcceso", clave);

        String URLN = urlnueva.build().toString();

            /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response.length() > 0) {
                        try {
                            // Analizar la respuesta JSON aquí
                            JSONObject jsonObject = response.getJSONObject(0);
                            String claveRespuesta = jsonObject.getString("claveacceso");
                            idCliente = jsonObject.getInt("idcliente");
                            if(claveRespuesta.equals(clave)){
                                Toast.makeText(getApplicationContext(),"has iniciado sesion",Toast.LENGTH_SHORT).show();
                                abrirActivityPrincipal(idCliente);
                            }else{
                                Toast.makeText(getApplicationContext(),"error en contraseña",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // La respuesta está vacía, maneja este caso adecuadamente
                        Log.e("Error", "Respuesta vacía");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                }
            }); */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLN, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        Log.e("REspuesta", response.toString());
                        if (response.getBoolean("sesion")) {
                            Toast.makeText(getApplicationContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                            idCliente = response.getInt("idcliente");
                            abrirActivityPrincipal(idCliente);
                        } else {
                            Toast.makeText(getApplicationContext(), response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            Log.e("Inicio de sesión", response.getString("mensaje"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // La respuesta está vacía, maneja este caso adecuadamente
                    Log.e("Error", "Respuesta vacía");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void abrirActivityPrincipal(int idCliente){
        Intent intent = new Intent(getApplicationContext(), Listar.class);
        intent.putExtra("idcliente", idCliente);
        startActivity(intent);
    }

    private  void abrirRegistro(){
        Intent intent = new Intent(getApplicationContext(), Registrar.class);
        startActivity(intent);
    }

    private void loadUI(){
        etdni = findViewById(R.id.etDNI);
        etClave = findViewById(R.id.etClave);
        btAcceder = findViewById(R.id.btAcceder);
        btRegistrase = findViewById(R.id.btRegistrase);
    }
}