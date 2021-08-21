package com.tugas.is3_10518122;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.tugas.is3_10518122.data.Database;
import com.tugas.is3_10518122.data.TodoRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTodoActivity extends AppCompatActivity implements View.OnClickListener {
    protected String titleTodo, deadlineTodo, statusTodo;
    protected int idTodo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_todo);
        todoRepository = new TodoRepository(this);

        Intent intent = getIntent();
        titleTodo = intent.getStringExtra("titleTodo");
        deadlineTodo = intent.getStringExtra("deadlineTodo");
        statusTodo = intent.getStringExtra("statusTodo");
        idTodo = intent.getIntExtra("idTodo", 1);

        EditText title = findViewById(R.id.titleTodo);
        EditText deadline = findViewById(R.id.deadlineTodo);
        RadioButton statusDone = findViewById(R.id.status_done);
        RadioButton statusPending = findViewById(R.id.status_pending);

        deadline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (statusTodo.toLowerCase().equals(Database.VAL_TODO_STATUS_COMPLETED)) {
            statusDone.setChecked(true);
            statusPending.setChecked(false);
        } else {
            statusPending.setChecked(true);
            statusDone.setChecked(false);
        }

        title.setText(titleTodo);
        deadline.setText(deadlineTodo);

        final Button btnDeleteTodo = findViewById(R.id.btnDeleteTodo);
        btnDeleteTodo.setOnClickListener(this);

        final Button btnUpdateTodo = findViewById(R.id.btnUpdateTodo);
        btnUpdateTodo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdateTodo:
                updateTodo(findViewById(R.id.titleTodo), findViewById(R.id.deadlineTodo), findViewById(R.id.status_pending), findViewById(R.id.status_done), idTodo);
                break;
            case R.id.btnDeleteTodo:
                deleteTodo(idTodo);
                break;
        }
    }

    private void updateTodo(EditText title, EditText deadline, RadioButton statusPending, RadioButton statusDone, int id) {
        String finalStatus = Database.VAL_TODO_STATUS_PENDING;

        if (TextUtils.isEmpty(title.getText())) {
            title.setError("Title is required!");
            return;
        }

        if (TextUtils.isEmpty(deadline.getText())) {
            deadline.setText("-");
        }

        if (statusPending.isChecked()) {
            finalStatus = Database.VAL_TODO_STATUS_PENDING;
        } else {
            finalStatus = Database.VAL_TODO_STATUS_COMPLETED;
        }

        todoRepository.updateTodo(id, title.getText().toString(), deadline.getText().toString(), finalStatus);

        Toast.makeText(this, "Todo Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    private void deleteTodo(int idTodo) {
        todoRepository.deleteTodo(idTodo);
        Toast.makeText(this, "Todo Deleted Successfully", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();
    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText deadline = findViewById(R.id.deadlineTodo);
        deadline.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();
    }
}
