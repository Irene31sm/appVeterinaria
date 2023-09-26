package com.example.appveterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {

    EditText etApellidos, etNombres, etDNI, etClave1;
    Button btRegistrar;
    String apellidos, nombres, dni, clave;
    boolean inicioSesion = false;
    final String URL = "http://192.168.59.25/appveterinaria/controllers/clientes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        loadUI();

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCajas();
            }
        });
    }

    private void validarCajas(){
        apellidos = etApellidos.getText().toString().trim();
        nombres = etNombres.getText().toString().trim();
        dni = etDNI.getText().toString().trim();
        clave = etClave1.getText().toString().trim();

        if (apellidos.isEmpty()) {
            etApellidos.setError("Complete este campo");
            etApellidos.requestFocus();
        } else if (nombres.isEmpty()) {
            etNombres.setError("Complete este campo");
            etNombres.requestFocus();
        } else if (dni.isEmpty() || dni.length() > 8)  {
            etDNI.setError("Complete este campo");
            etDNI.requestFocus();
        } else if (clave.isEmpty() || clave.length() < 5){
            etClave1.setError("contraseña muy corta");
            etClave1.requestFocus();
        } else {
            pregunta();
        }
    }

    private void pregunta(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria PATITAS");
        dialogo.setMessage("¿Desea registrarse?");
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
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Registradi Correctamente, inicie sesion", Toast.LENGTH_SHORT).show();
                    etApellidos.requestFocus();
                    inicioSesion = true;
                    if(inicioSesion){
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                    }
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
                parametros.put("apellidos", apellidos);
                parametros.put("nombres", nombres);
                parametros.put("dni", dni);
                parametros.put("claveAcceso", clave);

                return parametros;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }
    private void loadUI(){
        etApellidos = findViewById(R.id.etApellidos);
        etNombres = findViewById(R.id.etNombres);
        etDNI = findViewById(R.id.etDNI);
        etClave1 = findViewById(R.id.etClave1);

        btRegistrar = findViewById(R.id.btRegistrar);
    }
}