package com.example.studeninfonsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabasesHelper myDb;
    EditText EditId, EditName, EditDob;
    Button btnInsert;
    Button btnVeiw;
    Button btnDelete;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabasesHelper(this);

        EditId = (EditText)findViewById(R.id.sid);
        EditName = (EditText)findViewById(R.id.sname);
        EditDob = (EditText)findViewById(R.id.sdob);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnVeiw = (Button)findViewById(R.id.btnView);

        InsertData();
        UpdateData();
        DeleteData();
        ShowData();

        //For create data direct
        //myDb.insertData("1","Virak","07/07/1999");
        //myDb.insertData("2","Chhunheng", "10/12/1998");

    }

    public void InsertData(){
        btnInsert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(EditId.getText().toString(),
                                                            EditName.getText().toString(),
                                                            EditDob.getText().toString());
                        if(isInserted == true) Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(EditId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(EditId.getText().toString(),
                                EditName.getText().toString(),
                                EditDob.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void ShowData() {
        btnVeiw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :"+ res.getString(0)+"\n");
                            buffer.append("NAME :"+ res.getString(1)+"\n");
                            buffer.append("DOB :"+ res.getString(2)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
