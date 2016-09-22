package com.example.sahasra.studentsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper mydb;

    private EditText firstname, lastname, marks, id;
    private Button addbutton, viewallbutton, viewidbutton, updatebutton, delbutton, clearbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);

        firstname = (EditText) findViewById(R.id.fname);
        lastname = (EditText) findViewById(R.id.lname);
        marks = (EditText) findViewById(R.id.marks);
        id = (EditText) findViewById(R.id.StudentId);
        addbutton = (Button) findViewById(R.id.buttonadd);
        viewallbutton = (Button) findViewById(R.id.buttonviewall);
        viewidbutton = (Button) findViewById(R.id.buttonviewid);
        updatebutton = (Button) findViewById(R.id.buttonupdate);
        delbutton = (Button) findViewById(R.id.buttondelete);
        clearbutton = (Button) findViewById(R.id.buttonclear);

        addbutton.setOnClickListener(this);
        viewallbutton.setOnClickListener(this);
        viewidbutton.setOnClickListener(this);
        updatebutton.setOnClickListener(this);
        delbutton.setOnClickListener(this);
        clearbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == addbutton) {
            addData();
        }
        else if (view == viewallbutton) {
            viewAllData();
        }
        else if (view == viewidbutton) {
            viewIdData();
        }
        else if (view == updatebutton) {
            updateData();
        }
        else if (view == delbutton) {
            deleteData();
        }
        else if (view == clearbutton) {
            clearData();
        }
}

    private void clearData() {
        firstname.setText(" ");
        lastname.setText(" ");
        marks.setText(" ");
        id.setText(" ");
    }

    private void deleteData() {
        Integer deletedRows = mydb.deleteData(id.getText().toString());
        if (deletedRows == 0) {
                if (id.getText().toString().isEmpty()) {
                    Toast.makeText(this,"Pls enter the id for deletion", Toast.LENGTH_SHORT).show();
                }
                 else {
                    Toast.makeText(this, "No rows that match the criteria", Toast.LENGTH_SHORT).show();
                }
        }
        else {
            Toast.makeText(this,"Row has been deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void addData() {
        Boolean isInserted = mydb.insertData(firstname.getText().toString(), lastname.getText().toString(), marks.getText().toString(), id.getText().toString());
        if (isInserted = true) {
            if (firstname.getText().toString().isEmpty()) {
                Toast.makeText(this, "First name cannot be blank", Toast.LENGTH_SHORT).show();
            } else if ((lastname.getText().toString().isEmpty())) {
                Toast.makeText(this, "Last name cannot be blank", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data has been inserted", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Couldn't insert the data", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewAllData() {
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0) {
            Toast.makeText(this,"There is no data to display",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringBuffer buffer = new StringBuffer();
             while (res.moveToNext()) {
                buffer.append("ID: " + res.getString(0) + "\n" );
                buffer.append("FirstName: " + res.getString(1) + "\n" );
                buffer.append("LastName: " + res.getString(2) + "\n" );
                buffer.append("Marks: " + res.getString(3) + "\n\n" );
            }
            ShowMessage("Data" , buffer.toString());
        }
    }

    private void viewIdData() {
        if (id.getText().toString().isEmpty() ){
          //  throw new IllegalArgumentException("The id cannot be blank");
            Toast.makeText(this,"Id cannot be blank",Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor res = mydb.getAllData(id.getText().toString());
        if(res.getCount() == 0) {
            Toast.makeText(this,"There is no data to display",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringBuffer buffer = new StringBuffer();
            //  while (res.moveToNext()) {
            buffer.append("ID: " + res.getString(0) + "\n" );
            buffer.append("FirstName: " + res.getString(1) + "\n" );
            buffer.append("LastName: " + res.getString(2) + "\n" );
            buffer.append("Marks: " + res.getString(3) + "\n\n" );
            //     }
            ShowMessage("Data" , buffer.toString());
        }
    }
    private void updateData() {
        Boolean isUpdate = mydb.updateData(id.getText().toString(),
                firstname.getText().toString(),
                lastname.getText().toString(),
                marks.getText().toString());
        if (isUpdate == true) {
            Toast.makeText(this,"Row has been updated",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Prob with the row updation",Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

