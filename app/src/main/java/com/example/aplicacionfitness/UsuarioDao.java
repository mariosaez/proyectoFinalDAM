package com.example.aplicacionfitness;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDao {

    @Insert
    void insertarUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND contraseña = :contraseña")
    Usuario obtenerUsuario(String nombre, String contraseña);

    @Update
    void actualizarUsuario(Usuario usuario);

    @Delete
    void eliminarUsuario(Usuario usuario);

}

