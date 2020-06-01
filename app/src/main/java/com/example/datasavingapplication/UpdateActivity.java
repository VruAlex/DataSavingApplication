package com.example.datasavingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datasavingapplication.database.UserDatabaseContract;
import com.example.datasavingapplication.database.UserDatabaseHelper;
import com.example.datasavingapplication.model.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Button btUpdate;

    String text1, text2, text3, text4, text5;
    private EditText upText1, upText2, upText3, upText4, upText5;

    List<UserDetails> userDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        upText1 = findViewById(R.id.et_up_one);
        upText2 = findViewById(R.id.et_up_two);
        upText3 = findViewById(R.id.et_up_three);
        upText4 = findViewById(R.id.et_up_four);
        upText5 = findViewById(R.id.et_up_five);
        btUpdate = findViewById(R.id.bt_update);

        final int rowId = getIntent().getIntExtra("USERID", -1);
        Cursor c1 = db.query(UserDatabaseContract.UserDatabase.TABLE_NAME, null, UserDatabaseContract.UserDatabase._ID + "+ " + rowId, null, null, null, null);
        userDetailsList = new ArrayList<>();
        userDetailsList.clear();

        if (c1 != null && c1.getCount() != 0) {
            while (c1.moveToNext()) {
                upText1.setText(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL1)));
                upText2.setText(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL2)));
                upText3.setText(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL3)));
                upText4.setText(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL4)));
                upText5.setText(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL5)));
            }
        }

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = upText1.getText().toString();
                text2 = upText2.getText().toString();
                text3 = upText3.getText().toString();
                text4 = upText4.getText().toString();
                text5 = upText5.getText().toString();

                ContentValues values = new ContentValues();
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL1, text1);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL2, text2);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL3, text3);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL4, text4);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL5, text5);

                int updateId = db.update(UserDatabaseContract.UserDatabase.TABLE_NAME, values, UserDatabaseContract.UserDatabase._ID + " = " + rowId, null);
                if (updateId != -1) {
                    Toast.makeText(UpdateActivity.this, "User Details Updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "User Updating failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}