package com.example.aplicacionfitness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MisRutinas extends AppCompatActivity {

    private LinearLayout linearLayoutRutinas;
    private DatabaseHelper myDb;

    private List<Button> routineButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_rutinas);
        getSupportActionBar().hide();

        myDb = new DatabaseHelper(this);

        linearLayoutRutinas = findViewById(R.id.linearLayoutRutinas);

        Cursor res = myDb.getAllData();
        if(res.getCount() != 0) {
            // Itera sobre los resultados y crea un botón para cada rutina
            while (res.moveToNext()) {
                String buttonText = res.getString(1); // Asume que el nombre de la rutina está en la segunda columna
                long id = res.getLong(0); // Asume que el ID de la rutina está en la primera columna
                addNewButton(buttonText, id);
            }
        }

        Button btnVolver = findViewById(R.id.volverButton);
        Button addRoutine = findViewById(R.id.añadirRutina);
        Button deleteRoutine = findViewById(R.id.eliminarRutina);

        btnVolver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MisRutinas.this, Principal.class);
                startActivity(intent);
            }
        });

        addRoutine.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MisRutinas.this);
                final EditText editText = new EditText(MisRutinas.this);
                builder.setTitle("Nombre de la Rutina");
                builder.setView(editText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String routineName = editText.getText().toString().trim();
                        if (!routineName.isEmpty()) {
                            // Inserta el nombre de la rutina en la base de datos
                            long id = myDb.insertData(routineName);
                            // Crea un nuevo botón con el nombre de la rutina y el ID recién insertado
                            addNewButton(routineName, id);
                        } else {
                            Toast.makeText(MisRutinas.this, "El nombre de la rutina no puede estar vacío", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });

        deleteRoutine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MisRutinas.this);
                builder.setTitle("Eliminar Rutina");

                String[] routineNames = new String[routineButtons.size()];
                for (int i = 0; i < routineButtons.size(); i++) {
                    routineNames[i] = routineButtons.get(i).getText().toString();
                }

                builder.setItems(routineNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Button buttonToRemove = routineButtons.get(which);
                        // Recupera el ID de la base de datos del botón
                        long id = (long)buttonToRemove.getTag();
                        // Usa el ID para eliminar el dato de la base de datos
                        myDb.deleteData(id);
                        linearLayoutRutinas.removeView(buttonToRemove);
                        routineButtons.remove(which);
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });
    }

    private void addNewButton(String buttonText, long id) {
        Button newButton = new Button(this);
        newButton.setText(buttonText);

        // Establece el ID de la base de datos como una etiqueta para el botón
        newButton.setTag(id);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 20);
        newButton.setLayoutParams(params);

        // Configura el click listener del botón para abrir el DialogFragment
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutineDialogFragment dialog = new RoutineDialogFragment(buttonText, id, myDb);
                dialog.show(getSupportFragmentManager(), "RoutineDialog");
            }
        });

        linearLayoutRutinas.addView(newButton);
        routineButtons.add(newButton);
    }

}
