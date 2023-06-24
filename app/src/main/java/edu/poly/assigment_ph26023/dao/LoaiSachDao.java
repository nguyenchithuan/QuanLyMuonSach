package edu.poly.assigment_ph26023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class LoaiSachDao {

    private SQLiteDatabase db;

    public LoaiSachDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<LoaiSach> getData(String sql, String ...selection) {
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));

                list.add(loaiSach);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<LoaiSach> getAll() {
        ArrayList<LoaiSach> list =  new ArrayList<>();
        list = getData("SELECT * FROM LoaiSach");
        return list;
    }

    public LoaiSach getOne(String id) {
        ArrayList<LoaiSach> list = new ArrayList<>();
        list = getData("SELECT * FROM LoaiSach WHERE maLoai = ?", id);

        return list.get(0);
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        return db.insert("LoaiSach", null, values);
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        return db.update("LoaiSach", values, "maLoai = ?", new String[] {loaiSach.getMaLoai() + ""});
    }

    public int delete(String id) {
        return db.delete("LoaiSach", "maLoai = ?", new String[] {id});
    }

    // đây gọi là kiểu search like search like
    public ArrayList<LoaiSach> getSearch(String name) {
        ArrayList<LoaiSach> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiSach WHERE tenLoai LIKE '%' || ? || '%'";
        // còn mysql thì bỏ ||
        list = getData(sql, name);
        return list;
    }
}
