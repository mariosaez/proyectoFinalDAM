package com.example.aplicacionfitness;

public class Exercise {
    private long id;
    private String name;
    private long dayId;

    public Exercise(long id, String name, long dayId) {
        this.id = id;
        this.name = name;
        this.dayId = dayId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDayId() {
        return dayId;
    }
}

