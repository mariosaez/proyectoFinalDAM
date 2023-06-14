package com.example.aplicacionfitness;

import static com.example.aplicacionfitness.DatabaseHelper.DAY_COL_1;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<String> cardTitles;
    private List<Integer> cardImageResources;
    private Context context;
    private DatabaseHelper myDb;

    public CardAdapter(Context context, List<String> cardTitles, List<Integer> cardImageResources, DatabaseHelper myDb) {
        this.context = context;
        this.cardTitles = cardTitles;
        this.cardImageResources = cardImageResources;
        this.myDb = myDb;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_adapter, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.title.setText(cardTitles.get(position));
        holder.image.setImageResource(cardImageResources.get(position));

        // Iniciar la animación si el recurso es una lista de animaciones
        if (holder.image.getDrawable() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) holder.image.getDrawable();
            animationDrawable.start();
        }

        holder.addButton.setOnClickListener(v -> {
            if (myDb != null) {
                Cursor cursor = myDb.getAllData();
                List<String> routineNames = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int columnIndex = cursor.getColumnIndex(DatabaseHelper.COL_2);
                    if (columnIndex != -1) {
                        routineNames.add(cursor.getString(columnIndex));
                    }
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Elige una rutina")
                        .setItems(routineNames.toArray(new String[0]), (dialog, which) -> {
                            Cursor dayCursor = myDb.getDaysByRoutineName(routineNames.get(which));
                            List<String> dayNames = new ArrayList<>();
                            while (dayCursor.moveToNext()) {
                                int columnIndex = dayCursor.getColumnIndex(DatabaseHelper.DAY_COL_4);
                                if (columnIndex != -1) {
                                    dayNames.add(dayCursor.getString(columnIndex));
                                }
                            }

                            final AlertDialog.Builder dayBuilder = new AlertDialog.Builder(context);
                            dayBuilder.setTitle("Elige un día")
                                    .setItems(dayNames.toArray(new String[0]), (dayDialog, dayWhich) -> {
                                        // Movemos el cursor a la posición seleccionada
                                        if (dayCursor.moveToPosition(dayWhich)) {
                                            // Aquí obtenemos el id del día y lo insertamos en la base de datos
                                            int columnIndex = dayCursor.getColumnIndex(DAY_COL_1);
                                            if (columnIndex != -1) {
                                                long dayId = dayCursor.getLong(columnIndex);
                                                myDb.insertExercise(cardTitles.get(position), dayId);
                                            } else {
                                                // manejar el caso cuando la columna no existe
                                            }
                                        }
                                    });

                            dayBuilder.show();
                            //dayCursor.close();
                        });
                builder.show();
                //cursor.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardTitles.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        Button addButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            image = itemView.findViewById(R.id.card_gif);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
