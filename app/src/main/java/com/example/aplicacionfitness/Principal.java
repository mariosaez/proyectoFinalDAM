package com.example.aplicacionfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().hide();

        Button perfilButton = findViewById(R.id.perfilButton);
        perfilButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, MiPerfil.class);
                startActivity(intent);
            }
        });

        Button rutinasButton = findViewById(R.id.rutinasButton);
        rutinasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, MisRutinas.class);
                startActivity(intent);
            }
        });

        Button ejerciciosButton = findViewById(R.id.ejerciciosButton);
        ejerciciosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, Ejercicios.class);
                startActivity(intent);
            }
        });

        Button salirButton = findViewById(R.id.salirButton);
        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra todas las actividades y sale de la aplicación
                finishAffinity();
            }
        });

    }
}