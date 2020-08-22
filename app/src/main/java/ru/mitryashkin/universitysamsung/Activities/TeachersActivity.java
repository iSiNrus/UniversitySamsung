package ru.mitryashkin.universitysamsung.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.mitryashkin.universitysamsung.Adapters.TeacherAdapter;
import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.ViewModels.TeachersViewModel;
import ru.mitryashkin.universitysamsung.models.Teacher;

public class TeachersActivity extends AppCompatActivity {

    private static final String TAG = "TeachersActivity";
    public static final int ADD_TEACHER_REQUEST = 1;
    public static final int EDIT_TEACHER_REQUEST = 2;

    private TeachersViewModel teachersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        final RecyclerView recyclerView = findViewById(R.id.rv_teacher_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeacherAdapter adapter = new TeacherAdapter();
        recyclerView.setAdapter(adapter);

        teachersViewModel = ViewModelProviders.of(this).get(TeachersViewModel.class);
        teachersViewModel.getAllTeachers().observe(this, new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                adapter.setTeachers(teachers);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                teachersViewModel.delete(adapter.getTeacherAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TeachersActivity.this, "Преподаватель удалён", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        findViewById(R.id.add_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: addNewGroup");
                Intent intent = new Intent(getApplicationContext(), AddEditTeacherActivity.class);
                startActivityForResult(intent, ADD_TEACHER_REQUEST);
            }
        });

        ImageView backBut = findViewById(R.id.iv_back);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Back");
                Intent intent = new Intent(TeachersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Teacher teacher) {
                Intent intent = new Intent(TeachersActivity.this, AddEditTeacherActivity.class);
                intent.putExtra(AddEditTeacherActivity.EXTRA_ID, teacher.getID());
                intent.putExtra(AddEditTeacherActivity.EXTRA_FIRSTNAME, teacher.getFirstName());
                intent.putExtra(AddEditTeacherActivity.EXTRA_LASTNAME, teacher.getLastName());
                intent.putExtra(AddEditTeacherActivity.EXTRA_MIDDLENAME, teacher.getMiddleName());
                intent.putExtra(AddEditTeacherActivity.EXTRA_GRADE, teacher.getGrade());
                intent.putExtra(AddEditTeacherActivity.EXTRA_BIRTHDAY, teacher.getBirthday().getTime());

                startActivityForResult(intent, EDIT_TEACHER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == ADD_TEACHER_REQUEST) {
                String lastName = data.getStringExtra(AddEditTeacherActivity.EXTRA_LASTNAME);
                String firstName = data.getStringExtra(AddEditTeacherActivity.EXTRA_FIRSTNAME);
                String middleName = data.getStringExtra(AddEditTeacherActivity.EXTRA_MIDDLENAME);
                String grade = data.getStringExtra(AddEditTeacherActivity.EXTRA_GRADE);
                Calendar birthday = Calendar.getInstance();
                birthday.setTimeInMillis(data.getLongExtra(AddEditTeacherActivity.EXTRA_BIRTHDAY, 0));

                Teacher teacher = new Teacher(firstName, lastName, middleName, new Date(birthday.getTimeInMillis()), grade);
                teachersViewModel.insert(teacher);
                Toast.makeText(this, "Новый преподаватель сохранен", Toast.LENGTH_SHORT).show();
            }
            if(requestCode == EDIT_TEACHER_REQUEST)
            {
                int id = data.getIntExtra(AddEditTeacherActivity.EXTRA_ID, -1);
                if(id != -1) {
                    String lastName = data.getStringExtra(AddEditTeacherActivity.EXTRA_LASTNAME);
                    String firstName = data.getStringExtra(AddEditTeacherActivity.EXTRA_FIRSTNAME);
                    String middleName = data.getStringExtra(AddEditTeacherActivity.EXTRA_MIDDLENAME);
                    String grade = data.getStringExtra(AddEditTeacherActivity.EXTRA_GRADE);
                    Calendar birthday = Calendar.getInstance();
                    birthday.setTimeInMillis(data.getLongExtra(AddEditTeacherActivity.EXTRA_BIRTHDAY, 0));

                    Teacher teacher = new Teacher(firstName, lastName, middleName, new Date(birthday.getTimeInMillis()), grade);
                    teacher.setID(id);
                    teachersViewModel.update(teacher);

                    Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        else {
            Toast.makeText(this, "Не сохранено", Toast.LENGTH_SHORT).show();
        }
    }
}