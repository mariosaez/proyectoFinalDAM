package com.example.aplicacionfitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;


public class Registrar extends AppCompatActivity {

    private UsuarioDao usuarioDao;
    private TextInputEditText  etNombreUsuario;
    private TextInputEditText  etContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        getSupportActionBar().hide();

        // Inicializa la base de datos Room
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        // Obtiene el DAO para Usuario
        usuarioDao = db.usuarioDao();

        // Obtener referencias a los elementos de la interfaz de usuario
        etNombreUsuario = findViewById(R.id.etNuevoNombreUsuario);
        etContraseña = findViewById(R.id.password);

        Button btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de entrada de texto del nombre de usuario y la contraseña
                String nombreUsuario = etNombreUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                // Validar que los campos no estén vacíos
                if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(Registrar.this, "Por favor ingrese un nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar el usuario en la base de datos utilizando AsyncTask
                new InsertarUsuarioTask().execute(new Usuario(nombreUsuario, contraseña, null, 0, 0, null));

                // Mostrar un mensaje de éxito
                Toast.makeText(Registrar.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

                // Volver a la actividad principal
                onBackPressed();
            }
        });

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class InsertarUsuarioTask extends AsyncTask<Usuario, Void, Void> {

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDao.insertarUsuario(usuarios[0]);
            return null;
        }
    }
}
