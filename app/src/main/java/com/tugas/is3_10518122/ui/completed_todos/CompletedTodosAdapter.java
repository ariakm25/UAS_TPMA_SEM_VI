package com.tugas.is3_10518122.ui.completed_todos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugas.is3_10518122.EditTodoActivity;
import com.tugas.is3_10518122.R;
import com.tugas.is3_10518122.data.Database;
import com.tugas.is3_10518122.data.TodoRepository;
import com.tugas.is3_10518122.model.Todo;

import java.util.ArrayList;

public class CompletedTodosAdapter extends RecyclerView.Adapter<CompletedTodosAdapter.MyViewHolder> {

    Context context;
    ArrayList<Todo> todos;
    private TodoRepository todoRepository;

    public CompletedTodosAdapter(ArrayList<Todo> p, Context c) {
        todos = p;
        context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        if (todos.get(i).getStatus().toLowerCase().equals(Database.VAL_TODO_STATUS_COMPLETED)) {
            myViewHolder.itemTodoBtnDone.setVisibility(View.GONE);
        }
        myViewHolder.titleTodo.setText(todos.get(i).getTitle());
        myViewHolder.deadlineTodo.setText(todos.get(i).getDeadline());

        final String titleTodo = todos.get(i).getTitle();
        final String deadlineTodo = todos.get(i).getDeadline();
        final String statusTodo = todos.get(i).getStatus();
        final int idTodo = todos.get(i).getId();

        myViewHolder.itemTodoBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(context, EditTodoActivity.class);
                editIntent.putExtra("titleTodo", titleTodo);
                editIntent.putExtra("deadlineTodo", deadlineTodo);
                editIntent.putExtra("statusTodo", statusTodo);
                editIntent.putExtra("idTodo", idTodo);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                context.startActivity(editIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTodo, deadlineTodo, itemTodoBtnEdit, itemTodoBtnDone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTodo = (TextView) itemView.findViewById(R.id.itemTodoTitle);
            deadlineTodo = (TextView) itemView.findViewById(R.id.itemTodoDeadline);
            itemTodoBtnEdit = (Button) itemView.findViewById(R.id.itemTodoBtnEdit);
            itemTodoBtnDone = (Button) itemView.findViewById(R.id.itemTodoBtnDone);
        }
    }

}
