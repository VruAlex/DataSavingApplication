package com.example.datasavingapplication.database;

import android.provider.BaseColumns;

public final class UserDatabaseContract {

    public UserDatabaseContract() {
    }

    public static class UserDatabase implements BaseColumns {
        public static final String TABLE_NAME = "user_details";
        public static final String COLUMN_NAME_COL1 = "one";
        public static final String COLUMN_NAME_COL2 = "two";
        public static final String COLUMN_NAME_COL3 = "three";
        public static final String COLUMN_NAME_COL4 = "four";
        public static final String COLUMN_NAME_COL5 = "five";

    }
}
