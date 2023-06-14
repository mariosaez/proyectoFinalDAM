package com.example.aplicacionfitness;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class LoadUserDataTask extends AsyncTask<Void, Void, Usuario> {

    private WeakReference<MiPerfil> activityReference;
    private String username;
    private String password;

    LoadUserDataTask(MiPerfil context, String username, String password) {
        activityReference = new WeakReference<>(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected Usuario doInBackground(Void... voids) {
        MiPerfil activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return null;
        return activity.usuarioDao.obtenerUsuario(username, password);
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        MiPerfil activity = activityReference.get();
        if (activity == null || activity.isFinishing() || usuario == null) return;

        activity.usuarioActual = usuario;
        activity.tvNombreUsuario.setText(usuario.getNombre());
        //activity..setText(usuario.getContraseña());
    }
}

