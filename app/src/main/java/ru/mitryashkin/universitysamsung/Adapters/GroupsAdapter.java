package ru.mitryashkin.universitysamsung.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.models.Group;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupHolder> {

    private List<Group> groups = new ArrayList<>();
    private OnGroupClickListener mListener;

    public Group getGroupAt(int position) {
        return groups.get(position);
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group currentGr = groups.get(position);
        holder.gr_name.setText(currentGr.getGroupName());
        holder.gr_spec.setText(currentGr.getSpeciality());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    class GroupHolder extends RecyclerView.ViewHolder {
        private TextView gr_name;
        private TextView gr_spec;

        public GroupHolder(@NonNull View view) {
            super(view);
            gr_name = itemView.findViewById(R.id.tv_group_name);
            gr_spec = itemView.findViewById(R.id.tv_speciality);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onGroupClick(groups.get(position));
                    }
                }
            });
        }
    }


    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.mListener = listener;
    }

    public interface OnGroupClickListener{
        void onGroupClick(Group group);
    }
}
