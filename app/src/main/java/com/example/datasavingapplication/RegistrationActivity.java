package com.example.datasavingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Button btRegister;

    String text1, text2, text3, text4, text5;
    private EditText etText1, etText2, etText3, etText4, etText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        etText1 = findViewById(R.id.et_one);
        etText2 = findViewById(R.id.et_two);
        etText3 = findViewById(R.id.et_three);
        etText4 = findViewById(R.id.et_four);
        etText5 = findViewById(R.id.et_five);

        btRegister = findViewById(R.id.bt_registration);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = etText1.getText().toString();
                text2 = etText2.getText().toString();
                text3 = etText3.getText().toString();
                text4 = etText4.getText().toString();
                text5 = etText5.getText().toString();

                ContentValues values = new ContentValues();
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL1, text1);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL2, text2);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL3, text3);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL4, text4);
                values.put(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL5, text5);

                long rowId = db.insert(UserDatabaseContract.UserDatabase.TABLE_NAME, null, values);
                if (rowId != -1) {
                    Toast.makeText(RegistrationActivity.this, "User Reistration successfully done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
