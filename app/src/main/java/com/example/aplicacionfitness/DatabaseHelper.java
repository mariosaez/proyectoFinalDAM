package com.example.aplicacionfitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MisRutinas.db";
    public static final String TABLE_NAME = "rutina_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOMBRE";

    public static final String DAY_TABLE_NAME = "day_table";
    public static final String DAY_COL_1 = "ID";
    public static final String DAY_COL_2 = "ROUTINE_ID";
    public static final String DAY_COL_3 = "DAY_NUMBER";
    public static final String DAY_COL_4 = "NAME";

    public static final String EXERCISE_TABLE_NAME = "exercise_table";
    public static final String EXERCISE_COL_1 = "ID";
    public static final String EXERCISE_COL_2 = "NAME";
    public static final String EXERCISE_COL_3 = "DAY_ID";

    // Constantes para la tabla 'training'
    public static final String TRAINING_TABLE_NAME = "training_table";
    public static final String TRAINING_COL_1 = "ID";
    public static final String TRAINING_COL_2 = "DATE";
    public static final String TRAINING_COL_3 = "EXERCISE_ID";

    // Constantes para la tabla 'series'
    public static final String SERIES_TABLE_NAME = "series_table";
    public static final String SERIES_COL_1 = "ID";
    public static final String SERIES_COL_2 = "REPETITIONS";
    public static final String SERIES_COL_3 = "WEIGHT";
    public static final String SERIES_COL_4 = "TRAINING_ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT)");
        db.execSQL("CREATE TABLE " + DAY_TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ROUTINE_ID INTEGER, DAY_NUMBER INTEGER, NAME TEXT, " +
                "FOREIGN KEY(ROUTINE_ID) REFERENCES "+ TABLE_NAME +"(ID) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE " + EXERCISE_TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DAY_ID INTEGER, " +
                "FOREIGN KEY(DAY_ID) REFERENCES "+ DAY_TABLE_NAME +"(ID) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE " + TRAINING_TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, EXERCISE_ID INTEGER, " +
                "FOREIGN KEY(EXERCISE_ID) REFERENCES "+ EXERCISE_TABLE_NAME +"(ID) ON DELETE CASCADE)");

        // Creación de la tabla 'series'
        db.execSQL("CREATE TABLE " + SERIES_TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, REPETITIONS INTEGER, WEIGHT FLOAT, TRAINING_ID INTEGER, " +
                "FOREIGN KEY(TRAINING_ID) REFERENCES "+ TRAINING_TABLE_NAME +"(ID) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DAY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ EXERCISE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TRAINING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ SERIES_TABLE_NAME);
        onCreate(db);
    }

    public long insertExercise(String name, long dayId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_COL_2, name);
        contentValues.put(EXERCISE_COL_3, dayId);
        return db.insert(EXERCISE_TABLE_NAME, null, contentValues);
    }

    public long insertData(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nombre);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return cursor;
    }

    public int deleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + " = ?", new String[] { String.valueOf(id) });
    }

    public long insertDay(Day day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAY_COL_2, day.getRoutineId());
        contentValues.put(DAY_COL_3, day.getDayNumber());
        contentValues.put(DAY_COL_4, day.getName());
        return db.insert(DAY_TABLE_NAME, null, contentValues);
    }

    public Cursor getDaysByRoutineId(long routineId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + DAY_TABLE_NAME + " WHERE " + DAY_COL_2 + " = ?", new String[]{ String.valueOf(routineId) });
    }

    public int deleteDay(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DAY_TABLE_NAME, DAY_COL_1 + " = ?", new String[] { String.valueOf(id) });
    }

    // En la clase DatabaseHelper

    // Obtiene una rutina por su nombre
    public Cursor getRoutineByName(String routineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?", new String[]{ routineName });
    }

    // Obtiene días por el nombre de la rutina
    public Cursor getDaysByRoutineName(String routineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getRoutineByName(routineName);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_1);
            if (columnIndex != -1) {
                long routineId = cursor.getLong(columnIndex);
                return getDaysByRoutineId(routineId);
            } else {
                // manejar el caso cuando la columna no existe
            }
        }
        // Si la rutina no se encuentra o si no existe la columna COL_1, devuelve un cursor vacío
        return cursor;
    }

    public List<Exercise> getExercisesByDayId(long dayId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_COL_3 + " = ?", new String[]{ String.valueOf(dayId) });

        List<Exercise> exercises = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(EXERCISE_COL_1);
            int nameIndex = cursor.getColumnIndex(EXERCISE_COL_2);
            int dayIdIndex = cursor.getColumnIndex(EXERCISE_COL_3);

            if (idIndex == -1 || nameIndex == -1 || dayIdIndex == -1) {
                throw new IllegalArgumentException("Column not found");
            }

            long id = cursor.getLong(idIndex);
            String name = cursor.getString(nameIndex);
            dayId = cursor.getLong(dayIdIndex);

            exercises.add(new Exercise(id, name, dayId));
        }
        cursor.close();
        return exercises;
    }

    public boolean updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_COL_2, exercise.getName());
        contentValues.put(EXERCISE_COL_3, exercise.getDayId());

        int rowsUpdated = db.update(EXERCISE_TABLE_NAME, contentValues, EXERCISE_COL_1 + " = ?", new String[]{ String.valueOf(exercise.getId()) });
        return rowsUpdated > 0;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    public long insertTraining(String date, long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRAINING_COL_2, date);
        contentValues.put(TRAINING_COL_3, exerciseId);
        return db.insert(TRAINING_TABLE_NAME, null, contentValues);
    }

    public long insertSeries(int repetitions, float weight, long trainingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERIES_COL_2, repetitions);
        contentValues.put(SERIES_COL_3, weight);
        contentValues.put(SERIES_COL_4, trainingId);
        return db.insert(SERIES_TABLE_NAME, null, contentValues);
    }

    public List<Training> getTrainingsByExerciseId(long exerciseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TRAINING_TABLE_NAME + " WHERE " + TRAINING_COL_3 + " = ?", new String[]{ String.valueOf(exerciseId) });

        List<Training> trainings = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(TRAINING_COL_1);
            int dateIndex = cursor.getColumnIndex(TRAINING_COL_2);
            int exerciseIdIndex = cursor.getColumnIndex(TRAINING_COL_3);

            if (idIndex == -1 || dateIndex == -1 || exerciseIdIndex == -1) {
                throw new IllegalArgumentException("Column not found");
            }

            long id = cursor.getLong(idIndex);
            String date = cursor.getString(dateIndex);
            exerciseId = cursor.getLong(exerciseIdIndex);

            trainings.add(new Training(id, date, exerciseId));
        }
        cursor.close();
        return trainings;
    }

    public List<Series> getSeriesByTrainingId(long trainingId) {
        List<Series> seriesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + SERIES_TABLE_NAME + " WHERE " + SERIES_COL_4 + " = ?", new String[]{String.valueOf(trainingId)});
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(SERIES_COL_1);
            int repetitionsIndex = cursor.getColumnIndex(SERIES_COL_2);
            int weightIndex = cursor.getColumnIndex(SERIES_COL_3);

            if (idIndex != -1 && repetitionsIndex != -1 && weightIndex != -1) {
                long id = cursor.getLong(idIndex);
                int repetitions = cursor.getInt(repetitionsIndex);
                float weight = cursor.getFloat(weightIndex);

                seriesList.add(new Series(id, repetitions, weight, trainingId));
            } else {
                // Puedes manejar el error aquí, si alguna de las columnas no fue encontrada
                Log.e("DatabaseHelper", "One or more column names were not found in the result set.");
            }
        }
        cursor.close();

        return seriesList;
    }

    public int deleteHistoryByExerciseId(long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Primero, obtenemos todos los entrenamientos (trainings) que corresponden a este ejercicio.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TRAINING_TABLE_NAME + " WHERE " + TRAINING_COL_3 + " = ?", new String[]{ String.valueOf(exerciseId) });

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(TRAINING_COL_1);

            if (idIndex != -1) {
                long trainingId = cursor.getLong(idIndex);

                // Luego, eliminamos todas las series que corresponden a cada entrenamiento.
                db.delete(SERIES_TABLE_NAME, SERIES_COL_4 + " = ?", new String[] { String.valueOf(trainingId) });
            } else {
                // Si no encontramos la columna, puedes manejar el error aquí.
                Log.e("DatabaseHelper", "Column ID not found in the result set.");
            }
        }
        cursor.close();

        // Finalmente, eliminamos todos los entrenamientos correspondientes al ejercicio.
        return db.delete(TRAINING_TABLE_NAME, TRAINING_COL_3 + " = ?", new String[] { String.valueOf(exerciseId) });
    }







}
