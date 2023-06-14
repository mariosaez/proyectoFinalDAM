package com.example.aplicacionfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;
    private TextInputEditText etNombreUsuario;
    private TextInputEditText etContraseña;
    private Button btnIniciarSesion;
    private Button tvRegistrarse;

    String nombreUsuario;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Inicializa la base de datos Room
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        // Obtiene el DAO para Usuario
        usuarioDao = db.usuarioDao();

        // Obtener referencias a los elementos de la interfaz de usuario
        etNombreUsuario = findViewById(R.id.username);
        etContraseña = findViewById(R.id.password);
        btnIniciarSesion = findViewById(R.id.loginButton);
        tvRegistrarse = findViewById(R.id.registerButton);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de entrada de texto del nombre de usuario y la contraseña
                String nombreUsuario = etNombreUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                // Validar las credenciales de inicio de sesión del usuario utilizando AsyncTask
                new ValidarCredencialesTask().execute(nombreUsuario, contraseña);
            }
        });

        // Agregar un Listener de clic al texto "Registrarse"
        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad de registro
                Intent intent = new Intent(MainActivity.this, Registrar.class);
                startActivity(intent);
            }
        });

    }

    private class ValidarCredencialesTask extends AsyncTask<String, Void, Usuario> {

        protected Usuario doInBackground(String... params) {
            nombreUsuario = params[0];
            contraseña = params[1];
            return usuarioDao.obtenerUsuario(nombreUsuario, contraseña);
        }

        protected void onPostExecute(Usuario usuario) {
            if (usuario != null) {
                // Las credenciales de inicio de sesión son válidas, inicia la actividad principal
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("myPrefsFile", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", nombreUsuario);
                editor.putString("password", contraseña);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, Principal.class);
                startActivity(intent);
            } else {
                // Las credenciales de inicio de sesión son inválidas, muestra un mensaje de error
                Toast.makeText(MainActivity.this, "Credenciales de inicio de sesión inválidas", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
