package com.ahmedsameha1.journal;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Delete
    void delete(Entry entry);

    @Update
    void update(Entry entry);

    @Query("select * from entry where user_id = :email")
    LiveData<List<Entry>> getEntries(String email);

    @Query("select * from entry where id =:id")
    LiveData<Entry> getEntryById(int id);
}
