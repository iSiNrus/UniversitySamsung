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

import java.util.List;

import ru.mitryashkin.universitysamsung.Adapters.GroupsAdapter;
import ru.mitryashkin.universitysamsung.R;
import ru.mitryashkin.universitysamsung.ViewModels.GroupsViewModel;
import ru.mitryashkin.universitysamsung.models.Group;

public class GroupsActivity extends AppCompatActivity {

    public static final int ADD_GROUP_REQUEST = 1;
    public static final int EDIT_GROUP_REQUEST = 2;

    private static final String TAG = "GroupsActivity";

    private GroupsViewModel groupsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_group_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GroupsAdapter adapter = new GroupsAdapter();
        recyclerView.setAdapter(adapter);


        findViewById(R.id.add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: addNewGroup");
                Intent intent = new Intent(getApplicationContext(), AddEditGroupActivity.class);
                startActivityForResult(intent, ADD_GROUP_REQUEST);
            }
        });

        ImageView backBut = findViewById(R.id.iv_back);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Back");
                Intent intent = new Intent(GroupsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        groupsViewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        groupsViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(TAG, "onChanged: Changed! "+ groups.size());
                adapter.setGroups(groups);
            }
        });


        adapter.setOnGroupClickListener(new GroupsAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(Group group) {
                Intent intent = new Intent(GroupsActivity.this, AddEditGroupActivity.class);
                intent.putExtra(AddEditGroupActivity.EXTRA_ID, group.getID());
                intent.putExtra(AddEditGroupActivity.EXTRA_DEPARTMENT, group.getDepartment());
                intent.putExtra(AddEditGroupActivity.EXTRA_GROUPNAME, group.getGroupName());
                intent.putExtra(AddEditGroupActivity.EXTRA_SPECIALITY, group.getSpeciality());
                intent.putExtra(AddEditGroupActivity.EXTRA_KURATOR_ID, group.getKuratorID());

                startActivityForResult(intent, EDIT_GROUP_REQUEST);
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
                groupsViewModel.delete(adapter.getGroupAt(viewHolder.getAdapterPosition()));
                Toast.makeText(GroupsActivity.this, "Преподаватель удалён", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == ADD_GROUP_REQUEST)
            {
                String gr_name = data.getStringExtra(AddEditGroupActivity.EXTRA_GROUPNAME);
                String gr_spec = data.getStringExtra(AddEditGroupActivity.EXTRA_SPECIALITY);
                String gr_dep = data.getStringExtra(AddEditGroupActivity.EXTRA_DEPARTMENT);
                int gr_kurID = data.getIntExtra(AddEditGroupActivity.EXTRA_KURATOR_ID, 0);

                Group group = new Group(gr_name, gr_spec, gr_dep, gr_kurID);
                groupsViewModel.insert(group);
                Toast.makeText(this, "Новая группа сохранена", Toast.LENGTH_SHORT).show();
            }
            if(requestCode == EDIT_GROUP_REQUEST)
            {
                int id = data.getIntExtra(AddEditGroupActivity.EXTRA_ID, -1);
                if(id != -1)
                {
                    String gr_name = data.getStringExtra(AddEditGroupActivity.EXTRA_GROUPNAME);
                    String gr_spec = data.getStringExtra(AddEditGroupActivity.EXTRA_SPECIALITY);
                    String gr_dep = data.getStringExtra(AddEditGroupActivity.EXTRA_DEPARTMENT);
                    int gr_kurID = data.getIntExtra(AddEditGroupActivity.EXTRA_KURATOR_ID, 0);

                    Group group = new Group(gr_name, gr_spec, gr_dep, gr_kurID);
                    group.setID(id);
                    groupsViewModel.update(group);
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