package ru.mitryashkin.universitysamsung.Adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Student;
import ru.mitryashkin.universitysamsung.models.Teacher;
import ru.mitryashkin.universitysamsung.repos.GroupRepository;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {

    private List<Student> students = new ArrayList<>();
    private StudentAdapter.OnStudentClickListener mListener;

    public Student getStudentAt(int position) {
        return students.get(position);
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student student = students.get(position);
        holder.stud_name.setText(student.getFullName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
        notifyDataSetChanged();
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        private TextView stud_name;

        public StudentHolder(@NonNull View view) {
            super(view);
            stud_name = itemView.findViewById(R.id.tv_student_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onStudentClick(students.get(position));
                    }
                }
            });
        }
    }

    public void setOnStudClickListener(OnStudentClickListener listener) {
        this.mListener = listener;
    }

    public interface OnStudentClickListener{
        void onStudentClick(Student student);
    }

}
