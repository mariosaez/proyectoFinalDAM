package com.example.aplicacionfitness;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Usuario.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();

    private static volatile AppDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Crear nueva tabla temporal
            database.execSQL(
                    "CREATE TABLE usuarios_new (id INTEGER PRIMARY KEY NOT NULL, correo TEXT, edad INTEGER NOT NULL DEFAULT 0, peso REAL NOT NULL DEFAULT 0, sexo TEXT, nombre TEXT, contraseña TEXT)"
            );

            // Copiar datos de la tabla antigua a la nueva
            database.execSQL(
                    "INSERT INTO usuarios_new (id, nombre, contraseña) SELECT id, nombre, contraseña FROM usuarios"
            );

            // Eliminar la tabla antigua
            database.execSQL("DROP TABLE usuarios");

            // Cambiar el nombre de la tabla nueva para que reemplace la antigua
            database.execSQL("ALTER TABLE usuarios_new RENAME TO usuarios");
        }
    };

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "fitness-db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


