package com.ahmedsameha1.journal;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("select * from user where email = :email")
    User getUserByEmail(String email);
}
