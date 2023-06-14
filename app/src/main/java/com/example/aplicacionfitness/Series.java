package com.example.aplicacionfitness;

public class Series {
    private long id;
    private int repetitions;
    private float weight;
    private long trainingId;

    public Series(long id, int repetitions, float weight, long trainingId) {
        this.id = id;
        this.repetitions = repetitions;
        this.weight = weight;
        this.trainingId = trainingId;
    }

    public long getId() {
        return id;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public float getWeight() {
        return weight;
    }

    public long getTrainingId() {
        return trainingId;
    }
}
