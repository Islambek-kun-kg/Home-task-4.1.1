package com.example.lesson_411.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lesson_411.models.NoteModel;

@Database(entities = {NoteModel.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteModelDao noteModelDao();
}
