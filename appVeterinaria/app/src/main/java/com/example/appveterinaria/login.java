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
    String nombrecompleto;
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

        Uri.Builder urlnueva = Uri.parse(Direccion.URLClientes).buildUpon();
        urlnueva.appendQueryParameter("operacion", "inicioSesion");
        urlnueva.appendQueryParameter("dni", dni);
        urlnueva.appendQueryParameter("claveAcceso", clave);

        String URLN = urlnueva.build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLN, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        Log.e("REspuesta", response.toString());
                        if (response.getBoolean("sesion")) {
                            Toast.makeText(getApplicationContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                            idCliente = response.getInt("idcliente");
                            nombrecompleto = response.getString("nombre") + ", " + response.getString("apellidos");

                            Log.d("nombre", nombrecompleto);
                            abrirActivityPrincipal(idCliente, nombrecompleto);
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

    private void abrirActivityPrincipal(int idCliente, String nombrecompleto){
        Intent intent = new Intent(getApplicationContext(), Listar.class);
        intent.putExtra("idcliente", idCliente);
        intent.putExtra("nombreCompleto", nombrecompleto);
        startActivity(intent);
        finishAffinity();
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