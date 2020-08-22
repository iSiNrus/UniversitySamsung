package ru.mitryashkin.universitysamsung.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import ru.mitryashkin.universitysamsung.DB.UniversityDatabase;
import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Teacher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button groupsBut;
    private Button teachersBut;
    private Button studentsBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupsBut = findViewById(R.id.group_but);
        teachersBut = findViewById(R.id.teachers_but);
        studentsBut = findViewById(R.id.students_but);

        groupsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GroupsActivity.class);
                startActivity(intent);
            }
        });

        teachersBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TeachersActivity.class);
                startActivity(intent);
            }
        });

        studentsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentsActivity.class);
                startActivity(intent);
            }
        });
    }


}