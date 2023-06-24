package edu.poly.assigment_ph26023.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "duAnMau";
    private static final int DB_VERSION = 8;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ThuThu(" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE LoaiSach(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE ThanhVien(" +
                "maTv INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenTv TEXT NOT NULL," +
                "gioiTinh TEXT NOT NULL, " +
                "sdt TEXT NOT NULL, " +
                "namSinh INTEGER NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(sql);

        sql = "CREATE TABLE PhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES ThuThu(maTT), " +
                "maTV INTEGER REFERENCES ThanhVien(maTv), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngayThue DATE NOT NULL, " +
                "traSach INTEGER NOT NULL)";
        db.execSQL(sql);

        sql = "INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES ('admin', 'Admin', 'admin')";
        db.execSQL(sql);
        sql = "INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES ('chithuan', 'Chí Thuận', 'chithuan123')";
        db.execSQL(sql);


        sql = "INSERT INTO LoaiSach(maLoai, tenLoai) VALUES  (1, 'CNTT')";
        db.execSQL(sql);
        sql = "INSERT INTO LoaiSach(maLoai, tenLoai) VALUES  (2, 'Tình cảm')";
        db.execSQL(sql);


        sql = "INSERT INTO ThanhVien(maTv, tenTv, gioiTinh, sdt, namSinh) " +
                 "VALUES  (1, 'Nguyễn Văn A', 'Nam', '0981234776', 2003)";
        db.execSQL(sql);
        sql = "INSERT INTO ThanhVien(maTv, tenTv, gioiTinh, sdt, namSinh) " +
                "VALUES  (2, 'Trần Thị B', 'Nữ', '0971243212', 2001)";
        db.execSQL(sql);


        sql = "INSERT INTO Sach(maSach, tenSach, giaThue, maLoai) " +
                "VALUES (1, 'Lập trình Java', 20000, 1)";
        db.execSQL(sql);

        sql = "INSERT INTO Sach(maSach, tenSach, giaThue, maLoai) " +
                "VALUES (2, 'Người lái đò', 12000, 2)";
        db.execSQL(sql);


        sql = "INSERT INTO PhieuMuon(maPM, maTT, maTV, maSach, tienThue, ngayThue, traSach) " +
                "VALUES (1, 'admin', 1, 1, 20000, '2021-09-08', 1)";
        db.execSQL(sql);

        sql = "INSERT INTO PhieuMuon(maPM, maTT, maTV, maSach, tienThue, ngayThue, traSach) " +
                "VALUES (2, 'chithuan', 2, 2, 12000, '2022-10-09', 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS PhieuMuon";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS ThuThu";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS LoaiSach";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS ThanhVien";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS Sach";
        db.execSQL(sql);

        onCreate(db);
    }
}
