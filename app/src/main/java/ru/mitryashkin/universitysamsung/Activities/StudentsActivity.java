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

import java.util.Date;
import java.util.List;

import ru.mitryashkin.universitysamsung.Adapters.StudentAdapter;
import ru.mitryashkin.universitysamsung.Adapters.TeacherAdapter;
import ru.mitryashkin.universitysamsung.DAO.GroupDAO;
import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.ViewModels.StudentsViewModel;
import ru.mitryashkin.universitysamsung.models.Group;
import ru.mitryashkin.universitysamsung.models.Student;
import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.GroupRepository;

public class StudentsActivity extends AppCompatActivity {

    private static final String TAG = "StudentsActivity";
    public static final int ADD_STUDENT_REQUEST = 1;
    public static final int EDIT_STUDENT_REQUEST = 2;

    private StudentsViewModel studentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        final RecyclerView recyclerView = findViewById(R.id.rv_stud_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StudentAdapter adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);
        GroupRepository groupRepo = new GroupRepository(getApplication());
        final List<Group> groups = groupRepo.getAllSync();

        studentsViewModel = ViewModelProviders.of(this).get(StudentsViewModel.class);
        studentsViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

        findViewById(R.id.add_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: addNewStudent");
                Intent intent = new Intent(getApplicationContext(), AddEditStudentActivity.class);
                startActivityForResult(intent, ADD_STUDENT_REQUEST);
            }
        });

        ImageView backBut = findViewById(R.id.iv_back);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Back");
                Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
                startActivity(intent);
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
                studentsViewModel.delete(adapter.getStudentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(StudentsActivity.this, "Преподаватель удалён", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnStudClickListener(new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(Student student) {
                Intent intent = new Intent(StudentsActivity.this, AddEditStudentActivity.class);
                intent.putExtra(AddEditStudentActivity.EXTRA_ID, student.getID());
                intent.putExtra(AddEditStudentActivity.EXTRA_STUDBOOKNUM, student.getGradeBookNumber());
                intent.putExtra(AddEditStudentActivity.EXTRA_FIRSTNAME, student.getFirstName());
                intent.putExtra(AddEditStudentActivity.EXTRA_LASTNAME, student.getLastName());
                intent.putExtra(AddEditStudentActivity.EXTRA_MIDDLENAME, student.getMiddleName());
                intent.putExtra(AddEditStudentActivity.EXTRA_GROUPID, student.getGroupID());
                intent.putExtra(AddEditStudentActivity.EXTRA_BIRTHDAY, student.getBirthday().getTime());
                startActivityForResult(intent, EDIT_STUDENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == ADD_STUDENT_REQUEST) {
                int bookNum = data.getIntExtra(AddEditStudentActivity.EXTRA_STUDBOOKNUM, -1);
                String lastName = data.getStringExtra(AddEditStudentActivity.EXTRA_LASTNAME);
                String firstName = data.getStringExtra(AddEditStudentActivity.EXTRA_FIRSTNAME);
                String middleName = data.getStringExtra(AddEditStudentActivity.EXTRA_MIDDLENAME);
                int groupId = data.getIntExtra(AddEditStudentActivity.EXTRA_GROUPID, -1);
                String groupName = data.getStringExtra(AddEditStudentActivity.EXTRA_GROUPNAME);
                Date birthday = new Date();
                birthday.setTime(data.getLongExtra(AddEditTeacherActivity.EXTRA_BIRTHDAY, 0));

                Student student = new Student(bookNum, firstName, lastName, middleName, birthday, groupId);
                studentsViewModel.insert(student);
                Toast.makeText(this, "Новый студент сохранен", Toast.LENGTH_SHORT).show();
            }
            if(requestCode == EDIT_STUDENT_REQUEST)
            {
                int id = data.getIntExtra(AddEditStudentActivity.EXTRA_ID, -1);
                if(id != -1) {
                    int bookNum = data.getIntExtra(AddEditStudentActivity.EXTRA_STUDBOOKNUM, -1);
                    String lastName = data.getStringExtra(AddEditStudentActivity.EXTRA_LASTNAME);
                    String firstName = data.getStringExtra(AddEditStudentActivity.EXTRA_FIRSTNAME);
                    String middleName = data.getStringExtra(AddEditStudentActivity.EXTRA_MIDDLENAME);
                    int groupId = data.getIntExtra(AddEditStudentActivity.EXTRA_GROUPID, -1);
                    String groupName = data.getStringExtra(AddEditStudentActivity.EXTRA_GROUPNAME);
                    Date birthday = new Date();
                    birthday.setTime(data.getLongExtra(AddEditTeacherActivity.EXTRA_BIRTHDAY, 0));

                    Student student = new Student(bookNum, firstName, lastName, middleName, birthday, groupId);
                    student.setID(id);
                    studentsViewModel.update(student);

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