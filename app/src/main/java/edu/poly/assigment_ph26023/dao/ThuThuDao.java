package edu.poly.assigment_ph26023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.dbhelper.DBHelper;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class ThuThuDao {

    private SQLiteDatabase db;

    public ThuThuDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<ThuThu> getData(String sql, String ...selection) {
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTenTT(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<ThuThu> getAll() {
        ArrayList<ThuThu> list =  new ArrayList<>();
        list = getData("SELECT * FROM ThuThu");
        return list;
    }

    public ThuThu getOne(String id) {
        ArrayList<ThuThu> list = new ArrayList<>();
        list = getData("SELECT * FROM ThuThu WHERE maTT = ?", id);

        return list.get(0);
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getMaTT());
        values.put("hoTen", thuThu.getHoTenTT());
        values.put("matKhau", thuThu.getMatKhau());
        return db.insert("ThuThu", null, values);
    }

    public int update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thuThu.getHoTenTT());
        values.put("matKhau", thuThu.getMatKhau());
        return db.update("ThuThu", values, "maTT = ?", new String[] {thuThu.getMaTT()});
    }

    public int delete(String id) {
        return db.delete("ThuThu", "maTT = ?", new String[] {id});
    }


    // kiểm tra xem có tài khoản mật khẩu không
    public int checkLogin(String username, String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?";
        ArrayList<ThuThu> list = getData(sql, username, password);

        if(list.size() == 0) {
            return -1; // không có tài khoản mật khẩu
        }

        return 1;
    }


    // nếu đúng username và sai password
    public int checkPassword(String username, String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ? AND matKhau != ?";
        ArrayList<ThuThu> list = getData(sql, username, password);

        if(list.size() == 0) {
            return 1; // Mật khẩu đúng
        }

        return -1;
    }

    // kiểm tra xem đã có username chưa
    public int checkUserName(String username) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ?";
        ArrayList<ThuThu> list = getData(sql, username);

        if(list.size() == 0) {
            return -1; // maTT chứ tồn tại
        }

        return 1;
    }
}
