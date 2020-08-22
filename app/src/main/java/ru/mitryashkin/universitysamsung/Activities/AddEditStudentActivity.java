package ru.mitryashkin.universitysamsung.Activities;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Group;
import ru.mitryashkin.universitysamsung.repos.GroupRepository;

public class AddEditStudentActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_STUDBOOKNUM = "EXTRA_StudBookNum";
    public static final String EXTRA_FIRSTNAME = "EXTRA_FirstName";
    public static final String EXTRA_LASTNAME = "EXTRA_LastName";
    public static final String EXTRA_MIDDLENAME = "EXTRA_MiddleName";
    public static final String EXTRA_GROUPID = "EXTRA_GroupId";
    public static final String EXTRA_BIRTHDAY = "EXTRA_Birthday";
    public static final String EXTRA_GROUPNAME = "EXTRA_GroupName";

    private static final String TAG = "AddEditStudentActivity";

    private EditText et_StudBookNum;
    private EditText et_LastName;
    private EditText et_FirstName;
    private EditText et_MiddleName;
    private Spinner spinner_Group;
    private EditText et_Birthday;
    private TextView tv_title;

    List<Group> allGroups;
    GroupRepository grupsRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        tv_title = findViewById(R.id.tv_title);

        et_StudBookNum = findViewById(R.id.et_stud_book_num);
        et_LastName = findViewById(R.id.et_stud_last_name);
        et_FirstName = findViewById(R.id.et_stud_first_name);
        et_MiddleName = findViewById(R.id.et_stud_middle_name);
        spinner_Group = findViewById(R.id.sp_group);
        et_Birthday = findViewById(R.id.et_stud_birthday);

        grupsRepo = new GroupRepository(getApplication());
        allGroups = grupsRepo.getAllSync();

        ArrayAdapter<Group> adapter =
                new ArrayAdapter<Group>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, allGroups);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        Log.d(TAG, "allTeachers size "+ allGroups.size());
        spinner_Group.setAdapter(adapter);

        Intent intent = getIntent();
        if(!intent.hasExtra(EXTRA_ID)) {
            tv_title.setText("Новый студент");
        } else {
            tv_title.setText("Редактирование");
            et_StudBookNum.setText(Integer.toString(intent.getIntExtra(EXTRA_STUDBOOKNUM, 0)));
            et_LastName.setText(intent.getStringExtra(EXTRA_LASTNAME));
            et_FirstName.setText(intent.getStringExtra(EXTRA_FIRSTNAME));
            et_MiddleName.setText(intent.getStringExtra(EXTRA_MIDDLENAME));

            int grId = intent.getIntExtra(EXTRA_GROUPID, -1);
            GroupRepository repo = new GroupRepository(getApplication());
            Group gr = repo.getByIdSync(grId);
            if(gr!=null)
            {
                int pos = allGroups.indexOf(gr);
                spinner_Group.setSelection(pos);
            }

            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(intent.getLongExtra(EXTRA_BIRTHDAY, 0));

            String dateS = date.get(Calendar.DATE)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR);
            et_Birthday.setText(dateS);
        }
        ImageView saveBut = findViewById(R.id.iv_save_status);
        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Save Button");

                try{
                    saveStudent();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Ошибка заполнения. Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView backBut = findViewById(R.id.iv_back);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Back");
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    private void saveStudent() throws Exception {
        int bookNum = Integer.parseInt(et_StudBookNum.getText().toString().trim());
        String lastName = et_LastName.getText().toString();
        String firstName = et_FirstName.getText().toString();
        String middleName = et_MiddleName.getText().toString();
        Group gr = (Group) spinner_Group.getSelectedItem();
        Calendar birthdayDate = Calendar.getInstance();
        int groupID = gr.getID();
        String groupName = gr.getGroupName();
        String birthdayS = et_Birthday.getText().toString();
        if(lastName.trim().isEmpty() || firstName.trim().isEmpty()) {
            Log.d(TAG, "saveTeacher: Empty Names");
            Toast.makeText(this, "Пустое имя!", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] parts = birthdayS.trim().split("/");
        try {
            if (parts.length == 3) {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                birthdayDate = Calendar.getInstance();
                birthdayDate.set(year, month-1, day);

                Log.d(TAG, "saveTeacher: Date was created! "+birthdayDate.toString());
            }
            else throw new Exception();
        }catch (Exception e)
        {
            Log.d(TAG, "saveTeacher: Bad Date Expected");
            Toast.makeText(this, "Неверный формат даты! Ожидается: \"dd/mm/yyyy\"", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_STUDBOOKNUM, bookNum);
        data.putExtra(EXTRA_FIRSTNAME, firstName);
        data.putExtra(EXTRA_LASTNAME, lastName);
        data.putExtra(EXTRA_MIDDLENAME, middleName);
        data.putExtra(EXTRA_GROUPID, groupID);
        data.putExtra(EXTRA_GROUPNAME, groupName);
        data.putExtra(EXTRA_BIRTHDAY, birthdayDate.getTimeInMillis());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, data);
        finish();
    }
}