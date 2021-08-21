package com.tugas.is3_10518122.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tugas.is3_10518122.model.Todo;

import java.util.ArrayList;

public class TodoRepository {
    private Context context;
    private Database database;

    public TodoRepository(Context context) {
        this.context = context;
        database = new Database(context);
    }

    //fetch all pending todos from the database
    public ArrayList<Todo> getPendingTodos() {
        SQLiteDatabase sqLiteDatabase = this.database.getReadableDatabase();
        ArrayList<Todo> pendingTodos = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TODO_TABLE_NAME +
                " WHERE " + Database.COL_TODO_STATUS + "=?" +
                " ORDER BY " + Database.TODO_TABLE_NAME + "." + Database.COL_TODO_ID + " DESC";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{Database.VAL_TODO_STATUS_PENDING});

        while (cursor.moveToNext()) {
            Todo todo = new Todo();
            todo.setId(cursor.getInt(cursor.getColumnIndex(Database.COL_TODO_ID)));
            todo.setTitle(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_TITLE)));
            todo.setStatus(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_STATUS)));
            todo.setDeadline(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_DEADLINE)));
            pendingTodos.add(todo);
        }

        cursor.close();
        sqLiteDatabase.close();
        return pendingTodos;
    }

    //fetch all completed todos from the database
    public ArrayList<Todo> getCompletedTodos() {
        SQLiteDatabase sqLiteDatabase = this.database.getReadableDatabase();
        ArrayList<Todo> completedTodos = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TODO_TABLE_NAME +
                " WHERE " + Database.COL_TODO_STATUS + "=?" +
                " ORDER BY " + Database.TODO_TABLE_NAME + "." + Database.COL_TODO_ID + " DESC";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{Database.VAL_TODO_STATUS_COMPLETED});

        while (cursor.moveToNext()) {
            Todo todo = new Todo();
            todo.setId(cursor.getInt(cursor.getColumnIndex(Database.COL_TODO_ID)));
            todo.setTitle(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_TITLE)));
            todo.setStatus(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_STATUS)));
            todo.setDeadline(cursor.getString(cursor.getColumnIndex(Database.COL_TODO_DEADLINE)));
            completedTodos.add(todo);
        }

        cursor.close();
        sqLiteDatabase.close();
        return completedTodos;
    }

    // Add new todos into the database
    public boolean addTodo(Todo todoModel) {
        SQLiteDatabase sqLiteDatabase = this.database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_TODO_TITLE, todoModel.getTitle());
        contentValues.put(Database.COL_TODO_STATUS, todoModel.getStatus());
        contentValues.put(Database.COL_TODO_DEADLINE, todoModel.getDeadline());
        sqLiteDatabase.insert(Database.TODO_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return true;
    }

    // Update todos by id
    public boolean updateTodo(int id, String title, String deadline, String status) {
        SQLiteDatabase sqLiteDatabase = this.database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_TODO_TITLE, title);
        contentValues.put(Database.COL_TODO_STATUS, status);
        contentValues.put(Database.COL_TODO_DEADLINE, deadline);
        sqLiteDatabase.update(Database.TODO_TABLE_NAME, contentValues, Database.COL_TODO_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return true;
    }

    // Delete todos by id
    public boolean deleteTodo(int idTodo) {
        SQLiteDatabase sqLiteDatabase = this.database.getReadableDatabase();
        sqLiteDatabase.delete(Database.TODO_TABLE_NAME, Database.COL_TODO_ID + "=?", new String[]{String.valueOf(idTodo)});
        sqLiteDatabase.close();
        return true;
    }
}

