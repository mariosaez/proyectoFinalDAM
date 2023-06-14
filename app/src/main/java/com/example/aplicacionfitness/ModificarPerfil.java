package com.example.aplicacionfitness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModificarPerfil extends AppCompatActivity {

    private EditText nombreUsuarioTextView;
    private EditText contraseñaTextView;
    private EditText correoTextView;
    private EditText edadTextView;
    private EditText sexoTextView;
    private EditText pesoTextView;

    private Button btnGuardar;
    private Button btnVolver;

    private Usuario currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);
        getSupportActionBar().hide();

        nombreUsuarioTextView = findViewById(R.id.nombreUsuarioTextView);
        contraseñaTextView = findViewById(R.id.contraseñaTextView);
        correoTextView = findViewById(R.id.correoTextView);
        edadTextView = findViewById(R.id.edadTextView);
        sexoTextView = findViewById(R.id.sexoTextView);
        pesoTextView = findViewById(R.id.pesoTextView);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        SharedPreferences prefs = getSharedPreferences("myPrefsFile", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("username", null);  // segundo argumento es el valor por defecto
        String contraseña = prefs.getString("password", null);  // segundo argumento es el valor por defecto

        AppDatabase db = AppDatabase.getDatabase(ModificarPerfil.this);
        if (nombreUsuario != null && contraseña != null) {
            // Aquí debes buscar al usuario en tu base de datos usando el nombre de usuario y la contraseña
            new Thread(() -> {
                currentUser = db.usuarioDao().obtenerUsuario(nombreUsuario, contraseña);
            }).start();
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = nombreUsuarioTextView.getText().toString();
            String contraseñaNueva = contraseñaTextView.getText().toString();
            String correo = correoTextView.getText().toString();
            String edadStr = edadTextView.getText().toString();
            String sexo = sexoTextView.getText().toString();
            String pesoStr = pesoTextView.getText().toString();

            if (!nombre.equals(currentUser.getNombre())) {
                currentUser.setNombre(nombre);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", nombre);
                editor.apply();
            }
            if (!contraseñaNueva.equals(currentUser.getContraseña())) {
                currentUser.setContraseña(contraseñaNueva);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("password", contraseñaNueva);
                editor.apply();
            }
            if (!correo.equals(currentUser.getCorreo())) {
                currentUser.setCorreo(correo);
            }
            if (!edadStr.isEmpty() && !edadStr.equals(String.valueOf(currentUser.getEdad()))) {
                currentUser.setEdad(Integer.parseInt(edadStr));
            }
            if (!sexo.equals(currentUser.getSexo())) {
                currentUser.setSexo(sexo);
            }
            if (!pesoStr.isEmpty() && !pesoStr.equals(String.valueOf(currentUser.getPeso()))) {
                currentUser.setPeso(Float.parseFloat(pesoStr));
            }

            saveUserChanges(currentUser);
        });

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(ModificarPerfil.this, MiPerfil.class);
            startActivity(intent);
        });
    }

    private void saveUserChanges(Usuario user) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getDatabase(ModificarPerfil.this);
            db.usuarioDao().actualizarUsuario(user);
            runOnUiThread(() -> Toast.makeText(ModificarPerfil.this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show());
        }).start();
    }
}
