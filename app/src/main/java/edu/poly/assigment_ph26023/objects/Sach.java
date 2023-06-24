package edu.poly.assigment_ph26023.objects;

public class Sach {
    private int maSach;
    private String tenSach;
    private int giaThue;
    private int maLoaiSach;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaThue, int maLoaiSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }
}
