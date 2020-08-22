package ru.mitryashkin.universitysamsung.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.TeacherRepository;

public class AddEditGroupActivity extends AppCompatActivity {

    private static final String TAG = "AddEditGroupActivity";

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_GROUPNAME = "EXTRA_GroupName";
    public static final String EXTRA_SPECIALITY = "EXTRA_Speciality";
    public static final String EXTRA_DEPARTMENT = "EXTRA_Department";
    public static final String EXTRA_KURATOR_ID = "EXTRA_Kurator";

    TeacherRepository teacherRepo;

    List<Teacher> allTeachers;

    TextView tv_title;
    ImageView save_iv;
    ImageView back_iv;

    EditText et_groupName;
    EditText et_groupSpeciality;
    EditText et_groupDep;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        teacherRepo = new TeacherRepository(getApplication());

        allTeachers = teacherRepo.getAllSync();

        tv_title = findViewById(R.id.tv_title);
        save_iv = findViewById(R.id.iv_save_status);
        back_iv = findViewById(R.id.iv_back);

        et_groupName = findViewById(R.id.et_group_name);
        et_groupDep = findViewById(R.id.et_group_department);
        et_groupSpeciality = findViewById(R.id.et_group_spec);

        spinner = findViewById(R.id.sp_kurator);

        ArrayAdapter<Teacher> adapter =
                new ArrayAdapter<Teacher>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, allTeachers);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        Log.d(TAG, "allTeachers size "+ allTeachers.size());
        spinner.setAdapter(adapter);

        final Intent intent = getIntent();
        if(!intent.hasExtra(EXTRA_ID))
        {
            tv_title.setText("Новая группа");
        }
        else
        {
            tv_title.setText("Редактирование");
            int groupId = intent.getIntExtra(EXTRA_ID,-1);
            et_groupName.setText(intent.getStringExtra(EXTRA_GROUPNAME));
            et_groupDep.setText(intent.getStringExtra(EXTRA_DEPARTMENT));
            et_groupSpeciality.setText(intent.getStringExtra(EXTRA_SPECIALITY));
            int kurID = intent.getIntExtra(EXTRA_KURATOR_ID,-1);

            Teacher kurator = teacherRepo.getByIdSync(kurID);
            if(kurator!=null) {
                Log.d(TAG, "SetSelected my pos");
                int pos = allTeachers.indexOf(kurator);
                spinner.setSelection(pos);
            }
        }

        save_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Save Button");
                try {
                    saveGroup();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ошибка заполнения. Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Back");
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    private void saveGroup() throws Exception
    {
        Log.d(TAG, "saveGroup: Saving...");
        String gr_name = et_groupName.getText().toString();
        String gr_spec = et_groupSpeciality.getText().toString();
        String gr_dep = et_groupDep.getText().toString();
        Teacher kur = (Teacher) spinner.getSelectedItem();
        int gr_kuratorID = kur.getID();
        Log.d(TAG, "saveGroup: kurID "+ gr_kuratorID);
        Intent data = new Intent();
        data.putExtra(EXTRA_GROUPNAME, gr_name);
        data.putExtra(EXTRA_SPECIALITY, gr_spec);
        data.putExtra(EXTRA_DEPARTMENT, gr_dep);
        data.putExtra(EXTRA_KURATOR_ID, gr_kuratorID);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK,data);
    }
}