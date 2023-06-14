package com.example.aplicacionfitness;

import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class UpdateUserTask extends AsyncTask<String, Void, Void> {

    private WeakReference<MiPerfil> activityReference;
    private Usuario usuario;

    UpdateUserTask(MiPerfil context, Usuario usuario) {
        activityReference = new WeakReference<>(context);
        this.usuario = usuario;
    }

    @Override
    protected Void doInBackground(String... params) {
        MiPerfil activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return null;

        String nuevoNombreUsuario = params[0];
        String nuevaContraseña = params[1];

        usuario.setNombre(nuevoNombreUsuario);
        usuario.setContraseña(nuevaContraseña);
        activity.usuarioDao.actualizarUsuario(usuario);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MiPerfil activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;

        Toast.makeText(activity, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();
    }
}

