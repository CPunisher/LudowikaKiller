package com.cpunisher.ludowikakiller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordDao {
    public static WordDao instance;

    private SQLiteDatabase mDatabase;

    private WordDao(Context context) {
        mDatabase = DatabaseHelper.getInstance(context).getReadableDatabase();
    }

    public List<Word> getAllWord() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT word, meaning1, meaning2 FROM " + References.TABLE_WORD, null);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setWord(cursor.getString(0));
                word.setMeaning1(cursor.getString(1));
                word.setMeaning2(cursor.getString(2));

                list.add(word);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public Word getWordById(int id) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + References.TABLE_WORD + " WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Word word = new Word();
            word.setWord(cursor.getString(0));
            word.setMeaning1(cursor.getString(1));
            word.setMeaning2(cursor.getString(2));
            return word;
        }
        return null;
    }

    public int getWordCount() {
        Cursor cursor = mDatabase.rawQuery("SELECT COUNT(*) FROM " + References.TABLE_WORD, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public static WordDao getInstance(Context context) {
        if (instance == null) {
            instance = new WordDao(context);
        }
        return instance;
    }
}
