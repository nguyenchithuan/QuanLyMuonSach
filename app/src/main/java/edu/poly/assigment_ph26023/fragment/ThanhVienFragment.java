package edu.poly.assigment_ph26023.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.PhieuMuonDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.adapter.ThanhVienAdapter;
import edu.poly.assigment_ph26023.dao.ThanhVienDao;
import edu.poly.assigment_ph26023.objects.PhieuMuon;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class ThanhVienFragment extends Fragment implements ObjectInterface {
    private RecyclerView rcvThanhVien;
    private FloatingActionButton fabThanhVien;
    private ArrayList<ThanhVien> list;
    private ThanhVienDao dao;
    private ThanhVien thanhVien;
    private ThanhVienAdapter adapter;
    private EditText edSearch;

    public static ThanhVienFragment newInstance() {
        ThanhVienFragment fragment = new ThanhVienFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edSearch = view.findViewById(R.id.ed_search);
        fabThanhVien = view.findViewById(R.id.fab_thanh_vien);
        rcvThanhVien = view.findViewById(R.id.rcv_thanh_vien);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvThanhVien.setLayoutManager(layoutManager);

        dao = new ThanhVienDao(getActivity());
        list = dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this);
        adapter.setData(list);
        adapter.setTypeAnimation(0);
        rcvThanhVien.setAdapter(adapter);

        fabThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpenDialog(getActivity(), 0);
                adapter.setTypeAnimation(1);
            }
        });

        thanhVien = new ThanhVien();

        // ấn vào nút search trên bàn phím
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) { // bắt sự kiện khi ấn vào action search
                    list = dao.getSearch(edSearch.getText().toString().trim());
                    adapter.setData(list);
                }
                return false;
            }
        });
    }

    @Override
    public void onClickDelete(Object object) {
        thanhVien = (ThanhVien) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn xóa hay không ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkData(thanhVien)) {
                    dao.delete(thanhVien.getMaTv() + "");
                    restartAdapter();
                    adapter.setTypeAnimation(1);
                    CustomToast.showMessage(getActivity(), "Xóa thành công");
                } else {
                    CustomToast.showMessage(getActivity(), "Xóa thất bại");
                }
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onpenDialog(getActivity(), 1);
            }
        });

        builder.show();
    }

    // nếu dữ liệu bên gọi ra vẫn còn dữ liệu thì sẽ không cho xóa
    private boolean checkData(ThanhVien thanhVien) {
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getActivity());
        ArrayList<PhieuMuon> listPhieuMuon = phieuMuonDao.getAll();
        for (int i = 0; i < listPhieuMuon.size(); i++) {
            if(thanhVien.getMaTv() == listPhieuMuon.get(i).getMaTV()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClickUpdate(Object object) {
        thanhVien = (ThanhVien) object;
        onpenDialog(getActivity(), 1);
        adapter.setTypeAnimation(1);
    }


    private void onpenDialog(Context context, int type) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_item_thanh_vien_dialog);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvMaTv = dialog.findViewById(R.id.tv_maTv);
        EditText edTenTv = dialog.findViewById(R.id.ed_tenTv);
        EditText edSdt = dialog.findViewById(R.id.ed_sdt);
        EditText edNamSinh = dialog.findViewById(R.id.ed_namSinh);
        RadioButton rdoNam = dialog.findViewById(R.id.rdo_nam);
        RadioButton rdoNu = dialog.findViewById(R.id.rdo_nu);
        Button btnSave = dialog.findViewById(R.id.btn_send);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        tvTitle.setText("Thêm thành viên");
        tvMaTv.setVisibility(View.GONE);

        if(type != 0) {
            tvTitle.setText("Sửa thành viên");
            tvMaTv.setVisibility(View.VISIBLE);
            tvMaTv.setText("Mã thành viên: " + thanhVien.getMaTv());
            edTenTv.setText(thanhVien.getTenTv());
            edSdt.setText(thanhVien.getSdt());
            edNamSinh.setText(thanhVien.getNamSinh() + "");
            // kiểm tra giới tính
            if(thanhVien.getGioiTinh().equalsIgnoreCase("Nam")) {
                rdoNam.setChecked(true);
            } else {
                rdoNu.setChecked(true);
            }
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set dữ liệu vào trong đối tượng khi nhấp save
                String strTenTv = edTenTv.getText().toString().trim();
                String strSdt = edSdt.getText().toString().trim();
                String strNamSinh = edNamSinh.getText().toString().trim();
                thanhVien.setTenTv(strTenTv);
                thanhVien.setSdt(strSdt);
                if(!strNamSinh.isEmpty()) { // chuỗi rỗng không ép được
                    thanhVien.setNamSinh(Integer.parseInt(strNamSinh));
                }
                if(rdoNam.isChecked()) {
                    thanhVien.setGioiTinh("Nam");
                } else {
                    thanhVien.setGioiTinh("Nữ");
                }

                if(validate(thanhVien)) {

                    if(type == 0) { // thêm
                        if(dao.insert(thanhVien) > 0) {
                            CustomToast.showMessage(getActivity(), "Thêm thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Thêm thất bại!");
                        }
                    } else { // sửa
                        if(dao.update(thanhVien) > 0) {
//                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            CustomToast.showMessage(getActivity(), "Sửa thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Sửa thất bại!");
                        }
                    }

                    dialog.dismiss();
                    restartAdapter(); // cập nhật lại dữ liệu
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validate(ThanhVien thanhVien) {
        String strTenTv = thanhVien.getTenTv();
        String strSdt = thanhVien.getSdt();
        String strNamSinh = thanhVien.getNamSinh() + "";
        if(strTenTv.isEmpty() || strSdt.isEmpty() || strNamSinh.isEmpty()) {
            CustomToast.showMessage(getActivity(), "Mời nhập đầu đủ dữ liệu!");
            return false;
        }
        return true;
    }

    public void restartAdapter() {
        list = dao.getAll();
        adapter.setData(list);
    }
}
