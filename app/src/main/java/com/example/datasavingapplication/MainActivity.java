package com.example.datasavingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.datasavingapplication.adapter.UserDetailAdapter;
import com.example.datasavingapplication.database.UserDatabaseContract;
import com.example.datasavingapplication.database.UserDatabaseHelper;
import com.example.datasavingapplication.model.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnRegister;
    List<UserDetails> userDetailsList;
    SQLiteDatabase db;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Exit...!!!");
            builder.setIcon(R.drawable.son);
            builder.setMessage("Are you sure you want to Exit ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        recyclerView = findViewById(R.id.rv_user);
        btnRegister = findViewById(R.id.insetData);

        Button back = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer);
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        Cursor c1 = db.query(UserDatabaseContract.UserDatabase.TABLE_NAME, null, null, null, null, null, null);

        if (c1 != null && c1.getCount() != 0) {
            userDetailsList.clear();
            while (c1.moveToNext()) {
                UserDetails userDetailsItem = new UserDetails();
                userDetailsItem.setUserId(c1.getInt(c1.getColumnIndex(UserDatabaseContract.UserDatabase._ID)));
                userDetailsItem.setOne(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL1)));
                userDetailsItem.setTwo(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL2)));
                userDetailsItem.setThree(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL3)));
                userDetailsItem.setFour(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL4)));
                userDetailsItem.setFive(c1.getString(c1.getColumnIndex(UserDatabaseContract.UserDatabase.COLUMN_NAME_COL5)));
                userDetailsList.add(userDetailsItem);
            }
        }

        assert c1 != null;
        c1.close();
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserDetailAdapter(userDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}