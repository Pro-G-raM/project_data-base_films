package com.example.demo.model;

public class ActorDTO {
    private String fullName;
    private long filmCount;

    public ActorDTO(String fullName, long filmCount) {
        this.fullName = fullName;
        this.filmCount = filmCount;
    }

    public String getFullName() {
        return fullName;
    }

    public long getFilmCount() {
        return filmCount;
    }
}

