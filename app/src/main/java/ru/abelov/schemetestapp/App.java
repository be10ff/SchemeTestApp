package ru.abelov.schemetestapp;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.abelov.schemeTimeComponent.room.ScheduleDb;

/**
 * Created by artem on 22.05.18.
 */

public class App extends Application {
    public static App instance;

    private ScheduleDb database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, ScheduleDb.class, "database")
                .allowMainThreadQueries()

                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public ScheduleDb getDatabase() {
        return database;
    }
}

