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
        db.execSQL("CREATE TABLE coming (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "stationName TEXT," + "destination TEXT," + "time TEXT," + "day TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS coming");
        onCreate(db);
    }

    public int rowCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select _id from coming", null);
        int row_Count = cursor.getCount();
        cursor.close();
        db.close();

        return row_Count;
    }

    public String[][] load() {
        int index = 0;
        int count = rowCount();
        String stationName, destination, time, day;
        SQLiteDatabase db = getWritableDatabase();
        String[][] dbData = new String[count][4];
        Cursor cursor = db.rawQuery("select * from coming", null);
        //cursor.moveToFirst();

        if (count == 0)
            return null;
        else {
            while (cursor.moveToNext()) {
                stationName = cursor.getString(cursor.getColumnIndex("stationName"));
                destination = cursor.getString(cursor.getColumnIndex("destination"));
                time = cursor.getString(cursor.getColumnIndex("time"));
                day = cursor.getString(cursor.getColumnIndex("day"));

                dbData[index][0] = stationName;
                dbData[index][1] = destination;
                dbData[index][2] = time;
                dbData[index][3] = day;

                index++;
            }//while
        }
        cursor.close();
        db.close();
        return dbData;
    }

    public boolean distinct(String stationName, String destination, String time, String day) {
        SQLiteDatabase db = getWritableDatabase();
        String strLV = stationName + destination + time + day;     //리스트뷰에 띄워져있는 저장하고자하는 데이터
        String str = "";
        Cursor cursor = db.rawQuery("select * from coming", null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            while (cursor.moveToNext()) {
                str = cursor.getString(cursor.getColumnIndex("stationName")) + cursor.getString(cursor.getColumnIndex("destination")) +
                        cursor.getString(cursor.getColumnIndex("time")) + cursor.getString(cursor.getColumnIndex("day"));   //DB에 들어있는 데이터

                if (str.equals(strLV)) {
                    return true;
                }
            }//while
        }//else
        cursor.close();
        db.close();
        return false;
    }

    public void insert(String stationName, String destination, String time, String day) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into coming values( null, '" + stationName + "', '" + destination + "', '" + time + "', '" + day + "');");
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

    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from coming", null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            str += cursor.getString(cursor.getColumnIndex("stationName")) + cursor.getString(cursor.getColumnIndex("destination")) +
                    cursor.getString(cursor.getColumnIndex("time")) + cursor.getString(cursor.getColumnIndex("day")) + '\n';
        }

        cursor.close();
        db.close();

        return str;
    }

}
