package ru.mitryashkin.universitysamsung.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Teacher;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherHolder> {

    private List<Teacher> teachers = new ArrayList<>();
    private OnItemClickListener mListener;

    public Teacher getTeacherAt(int position) {
        return teachers.get(position);
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_item, parent, false);
        return new TeacherHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherHolder holder, int position) {
        Teacher currentTeacher = teachers.get(position);
        holder.Name_tv.setText(currentTeacher.getFullName());
        holder.Grade_tv.setText(currentTeacher.getGrade());
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    class TeacherHolder extends RecyclerView.ViewHolder {
        private TextView Name_tv;
        private TextView Grade_tv;

        public TeacherHolder(@NonNull View view) {
            super(view);
            Name_tv = itemView.findViewById(R.id.tv_teacher_name);
            Grade_tv = itemView.findViewById(R.id.tv_teacher_grade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(teachers.get(position));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Teacher position);
    }
}
