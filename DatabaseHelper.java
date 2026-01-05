package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoList.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TODO = "todos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK = "task";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TODO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    // CREATE: Add a new task
    public long addTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, item.getTask());
        long id = db.insert(TABLE_TODO, null, values);
        db.close();
        return id;
    }

    // READ: Retrieve all tasks
    public Cursor getAllTodoItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TODO;
        return db.rawQuery(query, null);
    }

    // UPDATE: Modify an existing task by id
    public int updateTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, item.getTask());
        int rowsAffected = db.update(TABLE_TODO, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
        return rowsAffected;
    }

    // DELETE: Remove a task by id
    public int deleteTodoItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TODO, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
