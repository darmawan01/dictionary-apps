package com.dicoding.clouds.kamusbahasa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.TABLE_ES;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.TABLE_IN;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.WordColumn.ARTI;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.WordColumn.KATA;

public class DatabaseHelpers extends SQLiteOpenHelper {

    private static String DATABASE = "dbkamus.db";
    private static final int VERSION = 1;

    private static String CTABLE_IN = "create table "+TABLE_IN+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            ARTI+" text not null);";

    private static String CTABLE_ES = "create table "+TABLE_ES+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            ARTI+" text not null);";

    DatabaseHelpers(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CTABLE_IN);
        db.execSQL(CTABLE_ES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IN);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ES);
        onCreate(db);
    }
}
