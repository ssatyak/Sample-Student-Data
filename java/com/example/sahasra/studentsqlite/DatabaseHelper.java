package com.example.sahasra.studentsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Student_db";
    private static final String DATABASE_VER = "2";
    private static final String TABLE_NAME = "Student_Table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FirstName";
    private static final String COL_3 = "LastName";
    private static final String COL_4 = "Marks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table Student_Table ( " + " ID INTEGER PRIMARY KEY, " + "FirstName TEXT, " + "LastName TEXT, "
        + "Marks INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String fname, String lname, String marks, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
            cv.put(COL_1,id);
            cv.put(COL_2, fname);
            cv.put(COL_3, lname);
            cv.put(COL_4, marks);
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1)
                return false;
            else
                return true;
        }

    public Boolean updateData(String id, String fname, String lname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2,fname);
        cv.put(COL_3,lname);
        cv.put(COL_4,marks);
        db.update(TABLE_NAME, cv, "id = ?", new String[] {(id)});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id = ?", new String[] {(id)});
    }

    public Cursor getAllData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
     String query = "select * from " + TABLE_NAME + " where id = " + id;
     Cursor res = db.rawQuery(query,null);

        if (res != null) {
            res.moveToFirst();
        }

        return res;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}
