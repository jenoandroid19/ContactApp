package com.example.contactapp;

import android.view.ViewDebug;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1,exportSchema = false)


public abstract class MyAppDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

}
