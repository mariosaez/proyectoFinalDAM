package com.example.aplicacionfitness;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RoutineDialogFragment extends DialogFragment {
    private String routineName;
    private long routineId;
    private DatabaseHelper myDb;
    private LinearLayout dayLayout;
    private List<Day> days = new ArrayList<>();

    public RoutineDialogFragment(String routineName, long routineId, DatabaseHelper myDb) {
        this.routineName = routineName;
        this.routineId = routineId;
        this.myDb = myDb;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.routine_dialog, container, false);

        dayLayout = view.findViewById(R.id.dayLayout);

        Button addDayButton = view.findViewById(R.id.addDayButton);
        Button removeDayButton = view.findViewById(R.id.removeDayButton);

        // Cargar los días existentes desde la base de datos
        Cursor cursor = myDb.getDaysByRoutineId(routineId);
        while (cursor.moveToNext()) {
            Day day = new Day();

            int idIndex = cursor.getColumnIndex(DatabaseHelper.DAY_COL_1);
            if (idIndex != -1) {
                day.setId(cursor.getLong(idIndex));
            }

            int routineIdIndex = cursor.getColumnIndex(DatabaseHelper.DAY_COL_2);
            if (routineIdIndex != -1) {
                day.setRoutineId(cursor.getLong(routineIdIndex));
            }

            int nameIndex = cursor.getColumnIndex(DatabaseHelper.DAY_COL_4);
            if (nameIndex != -1) {
                day.setName(cursor.getString(nameIndex));
            }

            days.add(day);

            View dayView = createDayView(day);
            dayLayout.addView(dayView);
        }
        cursor.close();

        addDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Añadir un nuevo día a la base de datos
                Day newDay = new Day();
                newDay.setRoutineId(routineId);
                newDay.setName("Dia " + (days.size() + 1));
                long dayId = myDb.insertDay(newDay);

                if (dayId != -1) {
                    newDay.setId(dayId);
                    days.add(newDay);
                    View dayView = createDayView(newDay);
                    dayLayout.addView(dayView);
                }
            }
        });

        removeDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar día
                if (!days.isEmpty()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final String[] dayNames = new String[days.size()];
                    for (int i = 0; i < days.size(); i++) {
                        dayNames[i] = days.get(i).getName();
                    }
                    builder.setTitle("Elige un día para eliminar")
                            .setItems(dayNames, (dialog, which) -> {
                                myDb.deleteDay(days.get(which).getId());
                                dayLayout.removeViewAt(which);
                                days.remove(which);
                            });
                    builder.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }

    private View createDayView(Day day) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dayView = inflater.inflate(R.layout.day_card, null);
        RecyclerView exerciseRecyclerView = dayView.findViewById(R.id.exerciseRecyclerView);

        // Aquí obtenemos los ejercicios de la base de datos para el día actual
        List<Exercise> exercises = myDb.getExercisesByDayId(day.getId());

        // Configuramos el RecyclerView de ejercicios
        ExerciseAdapter adapter = new ExerciseAdapter(exercises, new ExerciseAdapter.OnExerciseClickListener() {
            @Override
            public void onExerciseClicked(Exercise exercise) {
                // Crea un AlertDialog que muestra el archivo exercise_card.xml
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.exercise_card, null);
                builder.setView(view);

                // Aquí se pueden añadir los detalles del ejercicio a la vista del diálogo, por ejemplo:
                TextView exerciseName = view.findViewById(R.id.exercise_name);
                exerciseName.setText(exercise.getName());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exerciseRecyclerView.setAdapter(adapter);

        TextView dayNumber = dayView.findViewById(R.id.dayNumber);
        dayNumber.setText(day.getName());

        return dayView;
    }
}
