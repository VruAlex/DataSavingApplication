package com.example.datasavingapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserRegistration.dp";

    public UserDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dp){
        dp.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dp, int oldVersion, int newVersion){
        dp.execSQL(DELETE_USER_TABLE);
        onCreate(dp);
    }

    public  void onDowngrade(SQLiteDatabase dp, int oldVersion, int newVersion){
        onUpgrade(dp, oldVersion,newVersion);
    }

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserDatabaseContract.UserDatabase.TABLE_NAME +
            "( " + UserDatabaseContract.UserDatabase._ID + " INTEGER PRIMARY KEY," +
            UserDatabaseContract.UserDatabase.COLUMN_NAME_COL1 + " text," +
            UserDatabaseContract.UserDatabase.COLUMN_NAME_COL2 + " text," +
            UserDatabaseContract.UserDatabase.COLUMN_NAME_COL3 + " text," +
            UserDatabaseContract.UserDatabase.COLUMN_NAME_COL4 + " text," +
            UserDatabaseContract.UserDatabase.COLUMN_NAME_COL5 + " text)";
    private static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " +UserDatabaseContract.UserDatabase.TABLE_NAME;

}




