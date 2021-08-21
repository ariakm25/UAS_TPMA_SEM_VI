package com.tugas.is3_10518122.ui.add_todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.tugas.is3_10518122.R;
import com.tugas.is3_10518122.data.TodoRepository;
import com.tugas.is3_10518122.databinding.FragmentAddTodoBinding;
import com.tugas.is3_10518122.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTodoFragment extends Fragment implements View.OnClickListener {

    private FragmentAddTodoBinding binding;
    private TodoRepository todoRepository;
    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        todoRepository = new TodoRepository(getContext());

        final Button btnAddTodo = binding.btnAddTodo;
        btnAddTodo.setOnClickListener(this);

        binding.deadlineTodo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTodo:
                saveTodo(binding.titleTodo, binding.deadlineTodo);
                break;
        }
    }

    private void saveTodo(EditText title, EditText deadline) {
        if (TextUtils.isEmpty(title.getText())) {
            title.setError("Title is required!");
            return;
        }

        if (TextUtils.isEmpty(deadline.getText())) {
            deadline.setText("-");
        }

        Todo todo = new Todo(title.getText().toString(), deadline.getText().toString(), "pending");
        todoRepository.addTodo(todo);

        Toast.makeText(getActivity(), "Todo Added Successfully", Toast.LENGTH_SHORT).show();
        binding.btnAddTodo.setEnabled(true);
        binding.titleTodo.setText("");
        binding.deadlineTodo.setText("");
    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        binding.deadlineTodo.setText(sdf.format(myCalendar.getTime()));
    }
}