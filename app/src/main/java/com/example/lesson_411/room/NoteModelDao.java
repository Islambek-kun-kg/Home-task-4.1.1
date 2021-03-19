package com.example.lesson_411.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lesson_411.models.NoteModel;

import java.util.List;

@Dao
public interface NoteModelDao {
    @Query("SELECT * FROM notemodel")
    List<NoteModel> getAll();

    @Insert
    void insert(NoteModel noteModel);

    @Delete
    void delete(NoteModel noteModel);

    @Update
    void update(NoteModel noteModel);

    @Query("SELECT * FROM notemodel ORDER BY title ASC")
    List<NoteModel> sortAll();
}