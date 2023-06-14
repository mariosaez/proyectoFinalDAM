package com.example.aplicacionfitness;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleInstrumentedTest {

    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario();
    }

    @Test
    public void testNombre() {
        usuario.setNombre("Test");
        assertEquals("Test", usuario.getNombre());
    }

    @Test
    public void testContraseña() {
        usuario.setContraseña("TestPass");
        assertEquals("TestPass", usuario.getContraseña());
    }

    @Test
    public void usuarioConstructor_isCorrect() {
        Usuario usuario = new Usuario("nombre", "contraseña", "correo@test.com", 30, 75.5, "masculino");
        assertEquals("nombre", usuario.getNombre());
        assertEquals("contraseña", usuario.getContraseña());
        assertEquals("correo@test.com", usuario.getCorreo());
        assertEquals(30, usuario.getEdad());
        assertEquals(75.5, usuario.getPeso(), 0.01);
        assertEquals("masculino", usuario.getSexo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrown() {
        Usuario usuario = new Usuario();
        usuario.setEdad(-1);
    }
}