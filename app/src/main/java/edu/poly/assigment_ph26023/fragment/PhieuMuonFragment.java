package edu.poly.assigment_ph26023.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.adapter.PhieuMuonAdapter;
import edu.poly.assigment_ph26023.adapter.spinner_adapter.SachSpinnerAdapter;
import edu.poly.assigment_ph26023.adapter.spinner_adapter.ThanhVienSpinnerAdapter;
import edu.poly.assigment_ph26023.adapter.spinner_adapter.ThuThuSpinnerAdapter;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.PhieuMuonDao;
import edu.poly.assigment_ph26023.dao.SachDao;
import edu.poly.assigment_ph26023.dao.ThanhVienDao;
import edu.poly.assigment_ph26023.dao.ThuThuDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.PhieuMuon;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.ThanhVien;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class PhieuMuonFragment extends Fragment implements ObjectInterface {
    private RecyclerView rcvPhieuMuon;
    private FloatingActionButton fabPhieuMuon;
    private PhieuMuonDao dao;
    private ArrayList<PhieuMuon> list;
    private PhieuMuonAdapter adapter;
    private PhieuMuon phieuMuon;

    // thông tin thành viên
    private ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    private ThanhVienDao thanhVienDao;
    private ArrayList<ThanhVien> listThanhVien;
    private int maThanhVien;

    // thông tin sách
    private SachSpinnerAdapter sachSpinnerAdapter;
    private SachDao sachDao;
    private ArrayList<Sach> listSach;
    private int maSach, tienThue;

    // lấy tên đăng nhập
    SharedPreferences pref;

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static PhieuMuonFragment newInstance() {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabPhieuMuon = view.findViewById(R.id.fab_phieu_muon);
        rcvPhieuMuon = view.findViewById(R.id.rcv_phieu_muon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvPhieuMuon.setLayoutManager(layoutManager);

        dao = new PhieuMuonDao(getContext());
        list = dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this);
        adapter.setData(list);
        adapter.setTypeAnimation(0);
        rcvPhieuMuon.setAdapter(adapter);

        fabPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpenDialog(getActivity(), 0);
                adapter.setTypeAnimation(1);
            }
        });

        phieuMuon = new PhieuMuon();
        pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
    }

    @Override
    public void onClickDelete(Object object) {
        phieuMuon = (PhieuMuon) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn xóa hay không!");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(phieuMuon.getMaPM() + "");
                restartAdapter();
                adapter.setTypeAnimation(1);
                CustomToast.showMessage(getActivity(), "Delete thành công");
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }




    @Override
    public void onClickUpdate(Object object) {
        phieuMuon = (PhieuMuon) object;
        onpenDialog(getActivity(), 1);
        adapter.setTypeAnimation(1);
    }

    private void onpenDialog(Context context, int type) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_item_phieu_muon_dialog);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvMaPM = dialog.findViewById(R.id.tv_maPM);
        TextView tvNgayThue = dialog.findViewById(R.id.tv_ngayThue);
        TextView tvTienThue = dialog.findViewById(R.id.tv_tienThue);
        Spinner spinnerTV = dialog.findViewById(R.id.spn_tv);
        Spinner spinnerSach = dialog.findViewById(R.id.spn_sach);
        CheckBox chkTraSach = dialog.findViewById(R.id.chk_traSach);
        Button btnSave = dialog.findViewById(R.id.btn_send);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        tvTitle.setText("Thêm phiếu mượn");
        tvMaPM.setVisibility(View.GONE);
        tvNgayThue.setVisibility(View.GONE);

        thanhVienDao = new ThanhVienDao(context);
        listThanhVien = thanhVienDao.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, listThanhVien);
        spinnerTV.setAdapter(thanhVienSpinnerAdapter);
        spinnerTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTv();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDao = new SachDao(context);
        listSach = sachDao.getAll();
        sachSpinnerAdapter =  new SachSpinnerAdapter(context, listSach);
        spinnerSach.setAdapter(sachSpinnerAdapter);
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThue = listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê:  " + tienThue + " vnđ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (type != 0) {
            tvMaPM.setVisibility(View.VISIBLE);
            tvNgayThue.setVisibility(View.VISIBLE);
            tvTitle.setText("Sửa phiếu mượn");
            tvMaPM.setText("Mã phiếu mượn: " + phieuMuon.getMaPM());
            tvNgayThue.setText("Ngày thuê: " + format.format(phieuMuon.getNgayThue()));
            tvTienThue.setText("Tiền thuê:  " + phieuMuon.getTienThue() + " vnđ");

            for (int i = 0; i < listThanhVien.size(); i++) {
                if(phieuMuon.getMaTV() == listThanhVien.get(i).getMaTv()) {
                    spinnerTV.setSelection(i);
                }
            }

            for (int i = 0; i < listSach.size(); i++) {
                if(phieuMuon.getMaSach() == listSach.get(i).getMaSach()) {
                    spinnerSach.setSelection(i);
                }
            }

            if(phieuMuon.getTraSach() == 1) {
                chkTraSach.setChecked(true);
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setMaSach(maSach);
                phieuMuon.setTienThue(tienThue);
                if(chkTraSach.isChecked()) {
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);
                }

                if(type == 0) {
                    String maTT = pref.getString("USERNAME", null);
                    if(maTT == null) {
                        phieuMuon.setMaTT("admin");

                    }
                    phieuMuon.setMaTT(maTT);
                    phieuMuon.setNgayThue(new Date());
                    if(dao.insert(phieuMuon) > 0) {
                        CustomToast.showMessage(getActivity(), "Thêm thành công");
                    } else {
                        CustomToast.showMessage(getActivity(), "Thêm thất bại!");
                    }
                } else {
                    if(dao.update(phieuMuon) > 0) {
                        CustomToast.showMessage(getActivity(), "Sửa thành công");
                    } else {
                        CustomToast.showMessage(getActivity(), "Sửa thất bại!");
                    }
                }

                restartAdapter();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void restartAdapter() {
        list = dao.getAll();
        adapter.setData(list);
    }
}

