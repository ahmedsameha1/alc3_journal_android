package com.ahmedsameha1.journal;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database( entities = {User.class, Entry.class} , version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "database";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance (Context context) {
        if ( sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract UserDao userDao();
    public abstract EntryDao entryDao();
}
