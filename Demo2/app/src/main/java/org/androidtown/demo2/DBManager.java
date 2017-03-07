package org.androidtown.demo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by Remember on 2017-02-22.
 */

public class DBManager extends SQLiteOpenHelper implements Serializable {
    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        db.execSQL("CREATE TABLE metro (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "stationName TEXT," + "direction TEXT," +
                "startTimeHour INTEGER," + "startTimeMinute INTEGER," + "days TEXT," + "enable INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS metro");
        onCreate(db);
    }

    public int rowCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select _id from metro", null);
        int row_Count = cursor.getCount();
        cursor.close();
        db.close();

        return row_Count;
    }

    public void load(ItemListAdapter adapter) {
        int count = rowCount();
        String stationName, direction, days;
        int id, startTimeHour, startTimeMinute, enable;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from metro", null);

        if (count == 0)
            return;
        else {
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex("_id"));
                stationName = cursor.getString(cursor.getColumnIndex("stationName"));
                direction = cursor.getString(cursor.getColumnIndex("direction"));
                startTimeHour = cursor.getInt(cursor.getColumnIndex("startTimeHour"));
                startTimeMinute = cursor.getInt(cursor.getColumnIndex("startTimeMinute"));
                days = cursor.getString(cursor.getColumnIndex("days"));
                enable = cursor.getInt(cursor.getColumnIndex("enable"));

                adapter.addItem(id, stationName, direction, startTimeHour, startTimeMinute, days, enable);
            }//while
        }
        cursor.close();
        db.close();
    }

    public int lastID()
    {
        int id;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from metro", null);
        cursor.moveToLast();
        id = cursor.getInt(cursor.getColumnIndex("_id"));

        return id;
    }

    public boolean distinct(String stationName, String direction, int startTimeHour, int startTimeMinute, String days) {
        SQLiteDatabase db = getWritableDatabase();
        String strLV = stationName + direction + startTimeHour + startTimeMinute + days;     //리스트뷰에 띄워져있는 저장하고자하는 데이터
        String str = "";
        Cursor cursor = db.rawQuery("select * from metro", null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            while (cursor.moveToNext()) {
                str = cursor.getString(cursor.getColumnIndex("stationName")) + cursor.getString(cursor.getColumnIndex("direction")) +
                        cursor.getInt(cursor.getColumnIndex("startTimeHour")) +  cursor.getInt(cursor.getColumnIndex("startTimeMinute")) +
                        cursor.getString(cursor.getColumnIndex("days"));   //DB에 들어있는 데이터

                if (str.equals(strLV)) {
                    return true;
                }
            }//while
        }//else
        cursor.close();
        db.close();
        return false;
    }

    public void insert(String stationName, String direction, int startTimeHour, int startTimeMinute, String days, int enable) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into metro values( null, '" + stationName + "', '" + direction + "', '" + startTimeHour + "', '" + startTimeMinute + "', '"
                + days + "', '" + enable + "');");
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

}
