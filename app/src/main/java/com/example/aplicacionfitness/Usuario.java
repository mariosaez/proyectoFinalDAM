package com.example.aplicacionfitness;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "contraseña")
    private String contraseña;

    @ColumnInfo(name = "correo")
    private String correo;

    @ColumnInfo(name = "edad")
    private int edad;

    @ColumnInfo(name = "peso")
    private double peso;

    @ColumnInfo(name = "sexo")
    private String sexo;

    // Constructor vacío requerido por Room
    public Usuario() {}

    public Usuario(String nombre, String contraseña, String correo, int edad, double peso, String sexo) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.edad = edad;
        this.peso = peso;
        this.sexo = sexo;
    }

    // Métodos getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
