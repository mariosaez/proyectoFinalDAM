package com.example.aplicacionfitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Ejercicios extends AppCompatActivity {

    private Context context;
    private DatabaseHelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        getSupportActionBar().hide();

        myDb = new DatabaseHelper(this);

        Button btnVolver = findViewById(R.id.volverButton);
        btnVolver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Ejercicios.this, Principal.class);
                startActivity(intent);
            }
        });

        Button btnEspalda = findViewById(R.id.espalda);
        btnEspalda.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitles = new ArrayList<>();
                List<Integer> cardImageResources = new ArrayList<>();
                    cardTitles.add("Pull Up");
                    cardImageResources.add(R.drawable.pull_up);
                    cardTitles.add("Remo");
                    cardImageResources.add(R.drawable.remo);
                    cardTitles.add("Peso muerto");
                    cardImageResources.add(R.drawable.pesomuerto);
                    cardTitles.add("Bar row");
                    cardImageResources.add(R.drawable.barrow);
                    cardTitles.add("Pull down");
                    cardImageResources.add(R.drawable.pulldown);
                    cardTitles.add("Seated row");
                    cardImageResources.add(R.drawable.seatedrow);


                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitles, cardImageResources, myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });

        Button btnBiceps = findViewById(R.id.biceps);
        btnBiceps.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitlesBiceps = new ArrayList<>();
                List<Integer> cardImageResourcesBiceps = new ArrayList<>();
                cardTitlesBiceps.add("Curl mancuernas");
                cardImageResourcesBiceps.add(R.drawable.curlmancuerna);
                cardTitlesBiceps.add("Curl barra");
                cardImageResourcesBiceps.add(R.drawable.curlbarra);
                cardTitlesBiceps.add("Curl barra z");
                cardImageResourcesBiceps.add(R.drawable.curlbarraz);
                cardTitlesBiceps.add("Curl predicador");
                cardImageResourcesBiceps.add(R.drawable.curlpredicador);

                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitlesBiceps, cardImageResourcesBiceps, myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });

        Button btnTriceps = findViewById(R.id.triceps);
        btnTriceps.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitlesBiceps = new ArrayList<>();
                List<Integer> cardImageResourcesBiceps = new ArrayList<>();
                cardTitlesBiceps.add("Fondos");
                cardImageResourcesBiceps.add(R.drawable.fondos);
                cardTitlesBiceps.add("Press frances mancuernas");
                cardImageResourcesBiceps.add(R.drawable.pressfrancesmancuernas);
                cardTitlesBiceps.add("Press frances barra");
                cardImageResourcesBiceps.add(R.drawable.pressfrancesbarra);
                cardTitlesBiceps.add("Extensiones triceps");
                cardImageResourcesBiceps.add(R.drawable.extensionestriceps);

                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitlesBiceps, cardImageResourcesBiceps,myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });

        Button btnHombro = findViewById(R.id.hombro);
        btnHombro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitlesBiceps = new ArrayList<>();
                List<Integer> cardImageResourcesBiceps = new ArrayList<>();
                cardTitlesBiceps.add("Press militar barra");
                cardImageResourcesBiceps.add(R.drawable.pressmilitarbarra);
                cardTitlesBiceps.add("Elevaciones laterales");
                cardImageResourcesBiceps.add(R.drawable.elevacioneslaterales);
                cardTitlesBiceps.add("Remo menton");
                cardImageResourcesBiceps.add(R.drawable.remomenton);

                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitlesBiceps, cardImageResourcesBiceps, myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });

        Button btnPectoral = findViewById(R.id.pectoral);
        btnPectoral.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitlesBiceps = new ArrayList<>();
                List<Integer> cardImageResourcesBiceps = new ArrayList<>();
                cardTitlesBiceps.add("Press banca");
                cardImageResourcesBiceps.add(R.drawable.pressbanca);
                cardTitlesBiceps.add("Cruces poleas");
                cardImageResourcesBiceps.add(R.drawable.crucespoleas);

                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitlesBiceps, cardImageResourcesBiceps, myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });

        Button btnPierna = findViewById(R.id.pierna);
        btnPierna.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Dialog popupDialog = new Dialog(Ejercicios.this);
                popupDialog.setContentView(R.layout.popup_window);
                popupDialog.setCancelable(true);

                RecyclerView recyclerView = popupDialog.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Ejercicios.this));

                // Puedes reemplazar estas listas de ejemplo con tus propios datos
                List<String> cardTitlesBiceps = new ArrayList<>();
                List<Integer> cardImageResourcesBiceps = new ArrayList<>();
                cardTitlesBiceps.add("Sentadillas");
                cardImageResourcesBiceps.add(R.drawable.sentadillas);
                cardTitlesBiceps.add("Curl femoral");
                cardImageResourcesBiceps.add(R.drawable.curlfemoral);
                cardTitlesBiceps.add("Prensa");
                cardImageResourcesBiceps.add(R.drawable.prensa);


                CardAdapter cardAdapter = new CardAdapter(Ejercicios.this, cardTitlesBiceps, cardImageResourcesBiceps, myDb);
                recyclerView.setAdapter(cardAdapter);

                // Establece el ancho del diálogo al ancho de la pantalla
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(popupDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                popupDialog.getWindow().setAttributes(layoutParams);

                popupDialog.show();
            }

        });
    }
}