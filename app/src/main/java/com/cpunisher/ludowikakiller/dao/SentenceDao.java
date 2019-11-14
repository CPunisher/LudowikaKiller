package com.cpunisher.ludowikakiller.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cpunisher.ludowikakiller.util.DatabaseHelper;
import com.cpunisher.ludowikakiller.References;

public class SentenceDao {

    public static SentenceDao instance;

    private SQLiteDatabase mDatabase;

    private SentenceDao(Context context) {
        mDatabase = DatabaseHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * 通过Id从数据库文件中获取句子
     * @param id 句子Id
     * @return 对应的句子
     */
    public String getSentenceById(int id) {
        Cursor cursor = mDatabase.rawQuery("SELECT sentence FROM " + References.TABLE_TEST + " WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return null;
    }

    /**
     * 获取数据库中全部的句子数量
     * @return 句子数量
     */
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
