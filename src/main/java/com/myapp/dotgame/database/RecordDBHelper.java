package com.myapp.dotgame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myapp.dotgame.RecordView;
import com.myapp.dotgame.database.DotGameContract.*;

import java.util.ArrayList;
import java.util.List;

public class RecordDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DotGameEntry.TABLE_NAME + " (" +
                    DotGameEntry._ID + " INTEGER PRIMARY KEY," +
                    DotGameEntry.COLUMN_NAME_PLAYERNAME + " TEXT," +
                    DotGameEntry.COLUMN_NAME_SCORE + " INTEGER)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DotGameEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GameRecords.db";

    public RecordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.i("[INFO]", "Table Created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addHandler(Record record) {
        ContentValues val = new ContentValues();
        val.put(DotGameEntry.COLUMN_NAME_PLAYERNAME, record.getPlayerName());
        val.put(DotGameEntry.COLUMN_NAME_SCORE, record.getScore());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DotGameEntry.TABLE_NAME, null, val);
        db.close();
    }

    public List<Record> getTop10Scores() {
        String query = "SELECT DISTINCT " + DotGameEntry.COLUMN_NAME_PLAYERNAME + " , " + DotGameEntry.COLUMN_NAME_SCORE + " FROM " + DotGameEntry.TABLE_NAME + " ORDER BY " + DotGameEntry.COLUMN_NAME_SCORE + " DESC LIMIT 5";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Record> records = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setPlayerName(cursor.getString(0));
                record.setScore(cursor.getInt(1));
                records.add(record);
            } while (cursor.moveToNext());
        }
        return records;
    }

}
