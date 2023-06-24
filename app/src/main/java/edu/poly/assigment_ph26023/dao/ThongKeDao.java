package edu.poly.assigment_ph26023.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.Top;

public class ThongKeDao {
    private Context context;
    private SQLiteDatabase db;
    private SachDao sachDao;

    public ThongKeDao(Context context) {
        this.context = context;
        sachDao = new SachDao(context);
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Top> getTopDESC() {
        ArrayList<Top> list = new ArrayList<>();
        String sql = "SELECT maSach, count(maSach) AS soLuong FROM PhieuMuon " +
                "GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Top top = new Top();
                Sach sach = sachDao.getOne(cursor.getInt(0) + "");
                top.setTenSach(sach.getTenSach());
                top.setSoLuong(cursor.getInt(1));

                list.add(top);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return list;
    }

    public ArrayList<Top> getTopASC() {
        ArrayList<Top> list = new ArrayList<>();
        String sql = "SELECT maSach, COUNT(maSach) AS soLuong FROM PhieuMuon " +
                "GROUP BY maSach ORDER BY soLuong ASC LIMIT 10";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Top top = new Top();
                Sach sach = sachDao.getOne(cursor.getString(0));
                top.setTenSach(sach.getTenSach());
                top.setSoLuong(cursor.getInt(1));

                list.add(top);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        ArrayList<Integer> list = new ArrayList<>();

        String sql = "SELECT SUM(tienThue) AS doanhThu FROM PhieuMuon WHERE ngayThue BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay, denNgay});

        if(cursor.moveToFirst()) {
            try {
                list.add(cursor.getInt(0)); // lấy ra doanh thu
            } catch (Exception e){
                list.add(0);
            }
        }
        cursor.close();

        return list.get(0);
    }


}
