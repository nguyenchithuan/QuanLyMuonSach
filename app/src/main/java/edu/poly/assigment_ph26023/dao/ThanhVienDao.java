package edu.poly.assigment_ph26023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class ThanhVienDao {

    private SQLiteDatabase db;

    public ThanhVienDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<ThanhVien> getData(String sql, String ...selection) {
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThanhVien thanhVien = new ThanhVien();

                thanhVien.setMaTv(cursor.getInt(0));
                thanhVien.setTenTv(cursor.getString(1));
                thanhVien.setGioiTinh(cursor.getString(2));
                thanhVien.setSdt(cursor.getString(3));
                thanhVien.setNamSinh(cursor.getInt(4));

                list.add(thanhVien);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<ThanhVien> getAll() {
        ArrayList<ThanhVien> list =  new ArrayList<>();
        list = getData("SELECT * FROM ThanhVien");
        return list;
    }

    public ThanhVien getOne(String id) {
        ArrayList<ThanhVien> list = new ArrayList<>();
        list = getData("SELECT * FROM ThanhVien WHERE maTv = ?", id);

        return list.get(0);
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("tenTv", thanhVien.getTenTv());
        values.put("gioiTinh", thanhVien.getGioiTinh());
        values.put("sdt", thanhVien.getSdt());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.insert("ThanhVien", null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("tenTv", thanhVien.getTenTv());
        values.put("gioiTinh", thanhVien.getGioiTinh());
        values.put("sdt", thanhVien.getSdt());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.update("ThanhVien", values, "maTv = ?", new String[] {thanhVien.getMaTv() + ""});
    }

    public int delete(String id) {
        return db.delete("ThanhVien", "maTv = ?", new String[] {id});
    }


    // đây gọi là kiểu search like search like
    public ArrayList<ThanhVien> getSearch(String name) {
        ArrayList<ThanhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM ThanhVien WHERE tenTv LIKE '%' || ? || '%'";
        // còn mysql thì bỏ ||
        list = getData(sql, name);
        return list;
    }
}
