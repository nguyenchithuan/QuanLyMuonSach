package edu.poly.assigment_ph26023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.PhieuMuon;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class PhieuMuonDao {

    private SQLiteDatabase db;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getData(String sql, String ...selection) {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PhieuMuon phieuMuon = new PhieuMuon();

                phieuMuon.setMaPM(cursor.getInt(0));
                phieuMuon.setMaTT(cursor.getString(1));
                phieuMuon.setMaTV(cursor.getInt(2));
                phieuMuon.setMaSach(cursor.getInt(3));
                phieuMuon.setTienThue(cursor.getInt(4));
                Date date = null;
                try {
                    date = format.parse(cursor.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                phieuMuon.setNgayThue(date);
                phieuMuon.setTraSach(cursor.getInt(6));

                list.add(phieuMuon);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<PhieuMuon> getAll() {
        ArrayList<PhieuMuon> list =  new ArrayList<>();
        list = getData("SELECT * FROM PhieuMuon");
        return list;
    }

    public PhieuMuon getOne(String id) {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        list = getData("SELECT * FROM PhieuMuon WHERE maPM = ?", id);

        return list.get(0);
    }

    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayThue", format.format(phieuMuon.getNgayThue()));
        values.put("traSach", phieuMuon.getTraSach());
        return db.insert("PhieuMuon", null, values);
    }

    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayThue", format.format(phieuMuon.getNgayThue()));
        values.put("traSach", phieuMuon.getTraSach());
        return db.update("PhieuMuon", values, "maPM = ?", new String[] {phieuMuon.getMaPM() + ""});
    }

    public int delete(String id) {
        return db.delete("PhieuMuon", "maPM = ?", new String[] {id});
    }
}
