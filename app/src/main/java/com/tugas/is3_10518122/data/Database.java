package com.tugas.is3_10518122.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "IS3_10518122";

    // Tables
    public final static String TODO_TABLE_NAME = "todos";

    // Todos Table Columns
    public static final String COL_TODO_ID = "id";
    public static final String COL_TODO_TITLE = "title";
    public static final String COL_TODO_STATUS = "status";
    public static final String VAL_TODO_STATUS_PENDING = "pending";
    public static final String VAL_TODO_STATUS_COMPLETED = "done";
    public static final String COL_TODO_DEADLINE = "deadline";

    // Todos Table Queries
    private final static String TODO_CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE_NAME +
            " (" +
            COL_TODO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COL_TODO_TITLE + " TEXT NOT NULL, " +
            COL_TODO_STATUS + " TEXT NOT NULL, " +
            COL_TODO_DEADLINE + " TEXT NOT NULL" +
            ")";
    private final static String TODO_DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TODO_TABLE_NAME;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TODO_DROP_TABLE_QUERY);
        onCreate(db);
    }
}
