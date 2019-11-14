package com.cpunisher.ludowikakiller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SentenceDao {

    public static SentenceDao instance;

    private SQLiteDatabase mDatabase;

    private SentenceDao(Context context) {
        mDatabase = DatabaseHelper.getInstance(context).getReadableDatabase();
    }

    public String getSentenceById(int id) {
        Cursor cursor = mDatabase.rawQuery("SELECT sentence FROM " + References.TABLE_TEST + " WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return null;
    }

    public int getSentenceCount() {
        Cursor cursor = mDatabase.rawQuery("SELECT COUNT(*) FROM " + References.TABLE_TEST, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public static SentenceDao getInstance(Context context) {
        if (instance == null) {
            instance = new SentenceDao(context);
        }
        return instance;
    }
}
