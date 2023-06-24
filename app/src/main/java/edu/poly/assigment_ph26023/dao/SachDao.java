package edu.poly.assigment_ph26023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class SachDao {

    private SQLiteDatabase db;

    public SachDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Sach> getData(String sql, String ...selection) {
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Sach sach = new Sach();

                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoaiSach(cursor.getInt(3));

                list.add(sach);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<Sach> getAll() {
        ArrayList<Sach> list =  new ArrayList<>();
        list = getData("SELECT * FROM Sach");
        return list;
    }

    public Sach getOne(String id) {
        ArrayList<Sach> list = new ArrayList<>();
        list = getData("SELECT * FROM Sach WHERE maSach = ?", id);

        return list.get(0);
    }

    public long insert(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoaiSach());
        return db.insert("Sach", null, values);
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoaiSach());
        return db.update("Sach", values, "maSach = ?", new String[] {sach.getMaSach() + ""});
    }

    public int delete(String id) {
        return db.delete("Sach", "maSach = ?", new String[] {id});
    }

    // đây gọi là kiểu search like search like
    public ArrayList<Sach> getSearch(String name) {
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM Sach WHERE tenSach LIKE '%' || ? || '%'";
        // còn mysql thì bỏ ||
        list = getData(sql, name);
        return list;
    }
}
