package com.example.aplicacionfitness;

public class Training {
    private long id;
    private String date;
    private long exerciseId;

    public Training(long id, String date, long exerciseId) {
        this.id = id;
        this.date = date;
        this.exerciseId = exerciseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }
}

