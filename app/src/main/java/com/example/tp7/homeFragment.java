package com.example.tp7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static TeacherAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<Teacher> teacherList=new ArrayList<>();
    private static DatabaseOpenHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        db = new DatabaseOpenHelper(getActivity());
        //db.generateFakeTeachers(1);
        this.teacherList = db.getAllTeachers();
        mAdapter = new TeacherAdapter(teacherList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public static void deleteTeacher(Teacher teacher) {
        if (mAdapter != null) {
            db.deleteTeacher(teacher.getId()); // Delete from DB
            teacherList.remove(teacher);  // Remove from list
            mAdapter.notifyItemRemoved(teacherList.indexOf(teacher));  // Notify adapter of removal
        }
    }

    public static void addTeacher(Teacher teacher) {
        if (mAdapter != null) {
            db.addTeacher(teacher);  // Add to DB
            teacherList.add(teacher);  // Add to list
            mAdapter.notifyItemInserted(teacherList.size() - 1);  // Notify adapter of insertion
        }
    }
}


