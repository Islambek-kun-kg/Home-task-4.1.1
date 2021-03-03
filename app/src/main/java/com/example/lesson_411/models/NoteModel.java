package com.example.lesson_411.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NoteModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String title;
    private String createdAt;

    public NoteModel() {
    }

    public NoteModel(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}