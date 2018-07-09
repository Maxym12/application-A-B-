package com.example.a.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Link.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LinkDao linkDao();
}
