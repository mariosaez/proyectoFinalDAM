package com.example.aplicacionfitness;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.List;

public class ExerciseDialogFragment extends DialogFragment {
    private Exercise exercise;
    private Button openCalendarButton;
    private EditText editTextSeries;
    private String date;
    private LinearLayout seriesLayout;

    public ExerciseDialogFragment(Exercise exercise) {
        this.exercise = exercise;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_card, container, false);

        Button deleteExerciseButton = view.findViewById(R.id.delete_button);
        deleteExerciseButton.setOnClickListener(v -> deleteExercise());

        Button saveTrainingButton = view.findViewById(R.id.save_button);
        saveTrainingButton.setOnClickListener(v -> saveTraining());

        TextView exerciseName = view.findViewById(R.id.exercise_name);
        exerciseName.setText(exercise.getName());

        Button historyButton = view.findViewById(R.id.history_button);
        historyButton.setOnClickListener(v -> openHistory());

        openCalendarButton = view.findViewById(R.id.open_calendar_button);
        openCalendarButton.setOnClickListener(v -> openCalendar());

        editTextSeries = view.findViewById(R.id.edit_text_series);
        seriesLayout = view.findViewById(R.id.series_layout);

        editTextSeries.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextSeries.setVisibility(View.VISIBLE);

        editTextSeries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                seriesLayout.removeAllViews();

                int seriesCount = 0;
                try {
                    seriesCount = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < seriesCount; i++) {
                    EditText repetitionsEditText = new EditText(getContext());
                    repetitionsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    repetitionsEditText.setHint("Repeticiones para la serie " + (i + 1));

                    EditText weightEditText = new EditText(getContext());
                    weightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    weightEditText.setHint("Peso para la serie " + (i + 1));

                    seriesLayout.addView(repetitionsEditText);
                    seriesLayout.addView(weightEditText);
                }
            }
        });

        return view;
    }

    private void openHistory() {
        HistoryDialogFragment historyDialog = new HistoryDialogFragment((int)exercise.getId());
        historyDialog.show(getChildFragmentManager(), "HistoryDialogFragment");
    }

    private void openCalendar() {
        // Aquí puedes abrir tu DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Guarda la fecha en la variable de instancia
                        date = year + "-" + (month+1) + "-" + dayOfMonth;
                        openCalendarButton.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void deleteExercise() {
        // Necesitarás el id del ejercicio para eliminarlo
        // Aquí asumiré que tu clase Exercise tiene un método getId()
        DatabaseHelper db = new DatabaseHelper(getContext());
        db.deleteData(exercise.getId());

        // Luego puedes cerrar el diálogo
        dismiss();
    }

    private void saveTraining() {
        // Aquí asumiré que has establecido la fecha usando un DatePicker y la has guardado en una variable de instancia `date`
        DatabaseHelper db = new DatabaseHelper(getContext());

        // Inserta el entrenamiento en la base de datos y obtén su id
        long trainingId = db.insertTraining(date, exercise.getId());

        // Inserta cada serie en la base de datos
        int childCount = seriesLayout.getChildCount();
        for (int i = 0; i < childCount; i += 2) {
            EditText repetitionsEditText = (EditText) seriesLayout.getChildAt(i);
            EditText weightEditText = (EditText) seriesLayout.getChildAt(i + 1);

            int repetitions = Integer.parseInt(repetitionsEditText.getText().toString());
            float weight = Float.parseFloat(weightEditText.getText().toString());

            db.insertSeries(repetitions, weight, trainingId);
        }

        // Luego puedes cerrar el diálogo
        dismiss();
    }

    public static class HistoryDialogFragment extends DialogFragment {
        private int exerciseId;
        private String previousDate = null;
        public HistoryDialogFragment(int exerciseId) {
            this.exerciseId = exerciseId;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.history_dialog, container, false);

            Button clearHistoryButton = view.findViewById(R.id.clear_history_button);
            clearHistoryButton.setOnClickListener(v -> clearHistory());

            TableLayout tableLayout = view.findViewById(R.id.table_layout);
            DatabaseHelper db = new DatabaseHelper(getContext());
            List<Training> trainings = db.getTrainingsByExerciseId(exerciseId);

            for (Training training : trainings) {
                // Si la fecha ha cambiado, añade una línea de separación
                if (previousDate != null && !previousDate.equals(training.getDate())) {
                    View separator = new View(getContext());
                    separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    separator.setBackgroundColor(Color.GRAY);
                    tableLayout.addView(separator);
                }
                previousDate = training.getDate();

                // Recupera las series para este entrenamiento
                List<Series> seriesList = db.getSeriesByTrainingId(training.getId());

                // Para cada serie, crea una nueva fila en la tabla
                for (Series series : seriesList) {
                    TableRow row = new TableRow(getContext());

                    TextView dateView = new TextView(getContext());
                    dateView.setText(training.getDate());
                    row.addView(dateView);

                    TextView repetitionsView = new TextView(getContext());
                    repetitionsView.setText(String.valueOf(series.getRepetitions()));
                    row.addView(repetitionsView);

                    TextView weightView = new TextView(getContext());
                    weightView.setText(String.valueOf(series.getWeight()));
                    row.addView(weightView);

                    tableLayout.addView(row);
                }
            }

            return view;
        }

        private void clearHistory() {
            // Llama a la función de la base de datos para borrar el historial
            DatabaseHelper db = new DatabaseHelper(getContext());
            db.deleteHistoryByExerciseId(exerciseId);

            // Cierra el diálogo
            dismiss();
        }
    }
}
