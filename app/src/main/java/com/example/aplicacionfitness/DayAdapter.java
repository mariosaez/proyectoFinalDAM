package com.example.aplicacionfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<Day> days;
    private DatabaseHelper db;

    public DayAdapter(List<Day> days, DatabaseHelper db) {
        this.days = days;
        this.db = db;
    }


    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = days.get(position);
        holder.dayNumber.setText("Día " + (position + 1));

        // Aquí obtenemos los ejercicios de la base de datos para el día actual
        List<Exercise> exercises = db.getExercisesByDayId(day.getId());

        // Configuramos el RecyclerView de ejercicios
        ExerciseAdapter adapter = new ExerciseAdapter(exercises, new ExerciseAdapter.OnExerciseClickListener() {
            @Override
            public void onExerciseClicked(Exercise exercise) {

            }
        });
        holder.exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.exerciseRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayNumber;
        RecyclerView exerciseRecyclerView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayNumber = itemView.findViewById(R.id.dayNumber);
            exerciseRecyclerView = itemView.findViewById(R.id.exerciseRecyclerView);
        }
    }
}
