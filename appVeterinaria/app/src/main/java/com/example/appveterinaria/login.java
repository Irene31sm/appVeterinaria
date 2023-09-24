package com.example.appveterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    EditText etdni, etClave;
    Button btAcceder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadUI();

    }

    private void loadUI(){
        etdni = findViewById(R.id.etDNI);
        etClave = findViewById(R.id.etClave);
        btAcceder = findViewById(R.id.btAcceder);
    }
}