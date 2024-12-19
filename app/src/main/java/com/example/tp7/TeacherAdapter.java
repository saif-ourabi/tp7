package com.example.tp7;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private List<Teacher> teachers;

    public TeacherAdapter(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);
        return new TeacherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.nameTextView.setText(teacher.getName());
        holder.emailTextView.setText(teacher.getEmail());

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete Teacher")
                    .setMessage("Are you sure you want to delete " + teacher.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> homeFragment.deleteTeacher(this.teachers.get(position)))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }
    }


    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    public void sortTeachers(boolean isAscending) {
        if (isAscending) {
            Collections.sort(teachers, (t1, t2) -> t1.getName().compareTo(t2.getName()));
        } else {
            Collections.sort(teachers, (t1, t2) -> t2.getName().compareTo(t1.getName()));
        }
        notifyDataSetChanged();  // Notify the adapter to refresh the sorted list
    }

}
