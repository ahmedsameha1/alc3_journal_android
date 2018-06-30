package com.ahmedsameha1.journal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;
    private final Executor executor;

    private AppExecutor(Executor executor) {
        this.executor = executor;
    }

    public static AppExecutor getsInstance() {
        if ( sInstance == null ) {
            synchronized (LOCK) {
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getExecutor() {return  executor;}
}
