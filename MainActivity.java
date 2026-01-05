package com.example.todolistapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTodoItems;
    private TodoAdapter adapter;
    private ArrayList<TodoItem> todoList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper and list
        dbHelper = new DatabaseHelper(this);
        todoList = new ArrayList<>();

        // Setup RecyclerView
        rvTodoItems = findViewById(R.id.rvTodoItems);
        rvTodoItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this, todoList);
        rvTodoItems.setAdapter(adapter);

        // Load existing tasks
        loadTodoItems();

        // Set FAB click listener to add a new task
        FloatingActionButton fab = findViewById(R.id.fabAddItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTodoDialog();
            }
        });
    }

    // Load all tasks from the database into the todoList
    private void loadTodoItems() {
        todoList.clear();
        Cursor cursor = dbHelper.getAllTodoItems();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String task = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK));
                todoList.add(new TodoItem(id, task));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    // Display dialog to add a new task
    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New To-Do");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter task");
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String task = input.getText().toString().trim();
                if (!task.isEmpty()) {
                    long newId = dbHelper.addTodoItem(new TodoItem(task));
                    if (newId != -1) {
                        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error adding task", Toast.LENGTH_SHORT).show();
                    }
                    loadTodoItems();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
