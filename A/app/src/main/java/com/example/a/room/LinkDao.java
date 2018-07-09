package com.example.a.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LinkDao {

    @Query("SELECT * FROM link")
    List<Link> getAll();

    @Insert
    void insert(Link... link);

    @Update
    void update(Link... link);

    @Delete
    void delete(Link... link);

    @Query("SELECT * FROM link WHERE id = :id")
    Link getById(long id);

    @Query("DELETE from link WHERE id = :id")
    void deleteById(long... id);
}
