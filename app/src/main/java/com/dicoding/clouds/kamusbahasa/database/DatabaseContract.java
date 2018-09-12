package com.dicoding.clouds.kamusbahasa.database;

import android.provider.BaseColumns;

class DatabaseContract {

    static String TABLE_IN = "indonesia";
    static String TABLE_ES = "english";

    static final class WordColumn implements BaseColumns{
        static String KATA = "kata";
        static String ARTI = "arti";
    }

}
