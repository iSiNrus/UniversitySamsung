package ru.mitryashkin.universitysamsung.Activities;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import ru.mitryashkin.universitysamsung.R;

public class AddEditTeacherActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_FIRSTNAME = "EXTRA_FirstName";
    public static final String EXTRA_LASTNAME = "EXTRA_LastName";
    public static final String EXTRA_MIDDLENAME = "EXTRA_MiddleName";
    public static final String EXTRA_GRADE = "EXTRA_Grade";
    public static final String EXTRA_BIRTHDAY = "EXTRA_Birthday";

    private static final String TAG = "EditTeacherActivity";

    private EditText et_LastName;
    private EditText et_FirstName;
    private EditText et_MiddleName;
    private EditText et_Grade;
    private EditText et_Birthday;
    private TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher);


        tv_title = findViewById(R.id.tv_title);
        et_LastName = findViewById(R.id.et_teach_last_name);
        et_FirstName = findViewById(R.id.et_teach_first_name);
        et_MiddleName = findViewById(R.id.et_teach_middle_name);
        et_Grade = findViewById(R.id.et_teach_grade);
        et_Birthday = findViewById(R.id.et_teach_birthday);


        Intent intent = getIntent();
        if(!intent.hasExtra(EXTRA_ID)) {
            tv_title.setText("Новый преподаватель");
        } else {
            tv_title.setText("Редактирование");
            et_LastName.setText(intent.getStringExtra(EXTRA_LASTNAME));
            et_FirstName.setText(intent.getStringExtra(EXTRA_FIRSTNAME));
            et_MiddleName.setText(intent.getStringExtra(EXTRA_MIDDLENAME));
            et_Grade.setText(intent.getStringExtra(EXTRA_GRADE));
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

                try {
                    saveTeacher();
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void saveTeacher() throws Exception{
        String lastName = et_LastName.getText().toString();
        String firstName = et_FirstName.getText().toString();
        String middleName = et_MiddleName.getText().toString();
        String grade = et_Grade.getText().toString();
        String birthdayS = et_Birthday.getText().toString();
        Calendar birthdayDate = Calendar.getInstance();
        if(lastName.trim().isEmpty() || firstName.trim().isEmpty()) {
            Log.d(TAG, "saveTeacher: Empty Names");
            Toast.makeText(this, "Empty names!", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] parts = birthdayS.trim().split("/");
        try {
            if (parts.length == 3) {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

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
        data.putExtra(EXTRA_FIRSTNAME, firstName);
        data.putExtra(EXTRA_LASTNAME, lastName);
        data.putExtra(EXTRA_MIDDLENAME, middleName);
        data.putExtra(EXTRA_GRADE, grade);
        data.putExtra(EXTRA_BIRTHDAY, birthdayDate.getTimeInMillis());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, data);
        finish();
    }
}