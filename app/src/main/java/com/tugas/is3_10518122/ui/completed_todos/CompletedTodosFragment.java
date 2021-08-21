package com.tugas.is3_10518122.ui.completed_todos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugas.is3_10518122.data.TodoRepository;
import com.tugas.is3_10518122.databinding.FragmentCompletedTodosBinding;
import com.tugas.is3_10518122.model.Todo;
import com.tugas.is3_10518122.ui.todos.TodosAdapter;

import java.util.ArrayList;

public class CompletedTodosFragment extends Fragment {

    private FragmentCompletedTodosBinding binding;
    private ArrayList<Todo> pendingTodos;
    private RecyclerView pendingTodosRV;
    private TodoRepository todoRepository;
    private LinearLayoutManager linearLayoutManager;
    private TodosAdapter todosAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCompletedTodosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        todoRepository = new TodoRepository(getContext());
        loadCompletedTodos();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadCompletedTodos() {
        pendingTodosRV = (RecyclerView) binding.todos;
        todoRepository = new TodoRepository(getContext());

        pendingTodos = new ArrayList<>();
        pendingTodos = todoRepository.getCompletedTodos();
        todosAdapter = new TodosAdapter(pendingTodos, getContext());

        linearLayoutManager = new LinearLayoutManager(getContext());
        pendingTodosRV.setAdapter(todosAdapter);
        pendingTodosRV.setLayoutManager(linearLayoutManager);
    }
}