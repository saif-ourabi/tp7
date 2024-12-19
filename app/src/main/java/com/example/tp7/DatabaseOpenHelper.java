package com.example.tp7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TP8v2.db";
    private static final int DATABASE_VERSION = 3;


    public DatabaseOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE teacher (id INTEGER PRIMARY KEY, name TEXT, email TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        
    }


    public long addTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("email", teacher.getEmail());
        long result = db.insert("teacher", null, values);

        db.close();
        return result;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM teacher";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                Teacher teacher = new Teacher(name, email, id);
                teacherList.add(teacher);
            } while (cursor.moveToNext());
        }
        return teacherList;
    }




    public void generateFakeTeachers(int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        Random random = new Random();

        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Chris", "Anna", "James", "Sophia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia", "Miller", "Davis", "Lopez", "Martinez"};

        for (int i = 0; i < count; i++) {
            // Generate a random full name
            String name = firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];

            // Generate a random email address
            String email = name.toLowerCase().replace(" ", ".") + "@example.com";

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            db.insert("teacher", null, values);
        }

        db.close(); // Close the database connection
    }

    public void deleteTeacher(int teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete teacher by ID
        db.delete("teacher", "id = ?", new String[]{String.valueOf(teacherId)});

        db.close(); // Close the database connection
    }

    public void deleteAllTeacher() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("teacher", null,null);
        db.close(); // Close the database connection
    }

}
