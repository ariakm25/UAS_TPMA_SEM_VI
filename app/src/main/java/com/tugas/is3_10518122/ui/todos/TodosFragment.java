package com.tugas.is3_10518122.ui.todos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugas.is3_10518122.data.TodoRepository;
import com.tugas.is3_10518122.databinding.FragmentTodosBinding;
import com.tugas.is3_10518122.model.Todo;

import java.util.ArrayList;

public class TodosFragment extends Fragment {
    private FragmentTodosBinding binding;
    private ArrayList<Todo> pendingTodos;
    private RecyclerView pendingTodosRV;
    private TodoRepository todoRepository;
    private LinearLayoutManager linearLayoutManager;
    private TodosAdapter todosAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTodosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        todoRepository = new TodoRepository(getContext());
        loadPendingTodos();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadPendingTodos() {
        pendingTodosRV = (RecyclerView) binding.todos;
        todoRepository = new TodoRepository(getContext());

        pendingTodos = new ArrayList<>();
        pendingTodos = todoRepository.getPendingTodos();
        todosAdapter = new TodosAdapter(pendingTodos, getContext());

        linearLayoutManager = new LinearLayoutManager(getContext());
        pendingTodosRV.setAdapter(todosAdapter);
        pendingTodosRV.setLayoutManager(linearLayoutManager);
    }

}