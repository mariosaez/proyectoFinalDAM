package com.example.aplicacionfitness;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MiPerfil extends AppCompatActivity {

    public TextView tvNombreUsuario, tvCorreo, tvEdad, tvPeso, tvSexo;
    public Button btnGuardarCambios;
    public UsuarioDao usuarioDao;
    public Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        getSupportActionBar().hide();

        // Obtener instancia de la base de datos Room
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        // Obtener DAO para Usuario
        usuarioDao = db.usuarioDao();

        // Obtener referencias a los elementos de la interfaz de usuario
        tvNombreUsuario = findViewById(R.id.nombre);
        tvCorreo = findViewById(R.id.Correo);
        tvEdad = findViewById(R.id.Edad);
        tvPeso = findViewById(R.id.Peso);
        tvSexo = findViewById(R.id.Sexo);
        btnGuardarCambios = findViewById(R.id.guardarCambiosButton);

        SharedPreferences prefs = getSharedPreferences("myPrefsFile", MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");

        // Buscar al usuario en la base de datos en un nuevo hilo
        new Thread(() -> {
            usuarioActual = usuarioDao.obtenerUsuario(username, password);

            System.out.println(usuarioActual.getNombre());

            // Actualizar los TextViews en el hilo principal
            runOnUiThread(() -> {
                tvNombreUsuario.setText(usuarioActual.getNombre());
                tvCorreo.setText(usuarioActual.getCorreo());
                tvEdad.setText(String.valueOf(usuarioActual.getEdad()));
                tvPeso.setText(String.valueOf(usuarioActual.getPeso()));
                tvSexo.setText(usuarioActual.getSexo());
            });
        }).start();

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad ModificarDatos
                Intent intent = new Intent(MiPerfil.this, ModificarPerfil.class);
                startActivity(intent);
            }
        });

        // Configurar el botón Volver para regresar a la actividad Principal
        Button btnVolver = findViewById(R.id.volverButton);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiPerfil.this, Principal.class);
                startActivity(intent);
            }
        });
    }
}
