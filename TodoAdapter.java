package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context context;
    private List<TodoItem> todoList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TodoItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TodoAdapter(Context context, List<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final TodoItem item = todoList.get(position);
        holder.tvTodoText.setText(item.getTask());
        // Optional: Handle click events for update/delete
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    // Optionally add a method to refresh the list
    public void setTodoList(List<TodoItem> list) {
        this.todoList = list;
        notifyDataSetChanged();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTodoText;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoText = itemView.findViewById(R.id.tvTodoText);
        }
    }
}
