package com.ahmedsameha1.journal;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity( foreignKeys = @ForeignKey(entity = User.class,
                                            parentColumns = "email",
                                            childColumns = "user_id",
                                            onDelete = CASCADE))
public class Entry {
    @PrimaryKey( autoGenerate = true)
    private int id;
    @NonNull
    private String text;
    @NonNull
    private String user_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public void setUser_id(@NonNull String user_id) {
        this.user_id = user_id;
    }

    public Entry(@NonNull String text, @NonNull String user_id) {

        this.text = text;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    public String getUser_id() {
        return user_id;
    }
}
