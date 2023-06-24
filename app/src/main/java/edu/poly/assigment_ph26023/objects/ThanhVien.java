package edu.poly.assigment_ph26023.objects;

public class ThanhVien {
    private int maTv;
    private String tenTv;
    private String gioiTinh;
    private String sdt;
    private int namSinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String tenTv, String gioiTinh, String sdt, int namSinh) {
        this.maTv = maTv;
        this.tenTv = tenTv;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.namSinh = namSinh;
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getTenTv() {
        return tenTv;
    }

    public void setTenTv(String tenTv) {
        this.tenTv = tenTv;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }
}
