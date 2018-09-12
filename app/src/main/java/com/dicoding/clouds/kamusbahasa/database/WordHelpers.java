package com.dicoding.clouds.kamusbahasa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.dicoding.clouds.kamusbahasa.models.WordModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.TABLE_ES;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.TABLE_IN;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.WordColumn.ARTI;
import static com.dicoding.clouds.kamusbahasa.database.DatabaseContract.WordColumn.KATA;

public class WordHelpers {
    private Context context;
    private DatabaseHelpers databaseHelpers;
    private SQLiteDatabase database;

    public WordHelpers(Context context) {
        this.context = context;
    }

    public WordHelpers open() throws SQLException{
        databaseHelpers = new DatabaseHelpers(context);
        database = databaseHelpers.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelpers.close();
    }

    public ArrayList<WordModel> getAllID() {
        ArrayList<WordModel> models = new ArrayList<>();
        WordModel wordModel;
        Cursor cursor = database
                .query(TABLE_IN,
                        null,
                        null,
                        null,
                        null,
                        null,
                        _ID + " ASC",
                        null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                wordModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));


                models.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }else{
            Log.i("GET ALL ID", "Data ID: "+models);
        }
        cursor.close();
        return models;
    }

    public ArrayList<WordModel> getAllES() {
        ArrayList<WordModel> models = new ArrayList<>();
        WordModel wordModel;
        Cursor cursor = database
                .query(TABLE_ES,
                        null,
                        null,
                        null,
                        null,
                        null,
                        _ID + " ASC",
                        null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                wordModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));


                models.add(wordModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }else{
            Log.i("GET ALL ES", "Data ES: "+models);
        }
        cursor.close();
        return models;
    }


    public ArrayList<WordModel> getIdWord(String word){
        ArrayList<WordModel> wordList = new ArrayList<>();
        WordModel wordModel;
        Cursor cursor = database
                .query(TABLE_IN,null,
                        KATA+" LIKE ?",
                        new String[]{word},
                        null,
                        null,
                        _ID + " ASC",
                        null);

        cursor.moveToFirst();
        if (cursor.getCount()>0){
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                wordModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                wordList.add(wordModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return wordList;
    }

    public ArrayList<WordModel> getEsWord(String word){
        ArrayList<WordModel> wordList = new ArrayList<>();
        WordModel wordModel;
        Cursor cursor = database
                .query(TABLE_ES,null,
                        KATA+" LIKE ?",
                        new String[]{word},
                        null,
                        null,
                        _ID + " ASC",
                        null);

        cursor.moveToFirst();
        if (cursor.getCount()>0){
            do {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                wordModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));

                wordList.add(wordModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return wordList;
    }

    public long insert(WordModel wordModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA, wordModel.getKata());
        initialValues.put(ARTI, wordModel.getArti());
        return database.insert(TABLE_IN, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertIDTransaction(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_IN + " (" + KATA + ", " + ARTI
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getKata());
        stmt.bindString(2, wordModel.getArti());
        stmt.execute();
        stmt.clearBindings();

    }

    public void insertESTransaction(WordModel wordModel) {
        String sql = "INSERT INTO " + TABLE_ES + " (" + KATA + ", " + ARTI
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, wordModel.getKata());
        stmt.bindString(2, wordModel.getArti());
        stmt.execute();
        stmt.clearBindings();

    }
}
