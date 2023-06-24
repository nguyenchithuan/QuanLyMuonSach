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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.adapter.SachAdapter;
import edu.poly.assigment_ph26023.adapter.spinner_adapter.LoaiSachSpinnerAdapter;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.LoaiSachDao;
import edu.poly.assigment_ph26023.dao.PhieuMuonDao;
import edu.poly.assigment_ph26023.dao.SachDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.PhieuMuon;
import edu.poly.assigment_ph26023.objects.Sach;

public class SachFragment extends Fragment implements ObjectInterface {
    private RecyclerView rcvSach;
    private FloatingActionButton fabSach;
    private ArrayList<Sach> list;
    private SachDao dao;
    private SachAdapter adapter;
    private Sach sach;

    // thông tin loại sách
    private LoaiSachSpinnerAdapter spinnerAdapter;
    private LoaiSachDao loaiSachDao;
    private ArrayList<LoaiSach> listLoaiSach;
    private int maLoaiSach;

    private EditText edSearch;

    public static SachFragment newInstance() {
        SachFragment fragment = new SachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edSearch = view.findViewById(R.id.ed_search);
        fabSach = view.findViewById(R.id.fab_sach);
        rcvSach = view.findViewById(R.id.rcv_sach);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvSach.setLayoutManager(layoutManager);

        dao = new SachDao(getActivity());
        list = dao.getAll();
        adapter = new SachAdapter(getActivity(), this);
        adapter.setData(list);
        adapter.setTypeAnimation(0);
        rcvSach.setAdapter(adapter);

        fabSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpenDialog(getActivity(), 0);
                adapter.setTypeAnimation(1);
            }
        });

        sach = new Sach();

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
        sach = (Sach) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Bạn có chắc chắn muốn xóa hay không ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if(checkData(sach)) {
                   dao.delete(sach.getMaSach() + "");
                   restartAdapter();
                   adapter.setTypeAnimation(1);
                   CustomToast.showMessage(getActivity(), "Xóa thành công!");
               } else {
                   CustomToast.showMessage(getActivity(), "Xóa thất bại!");
               }
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    // nếu dữ liệu bên gọi ra vẫn còn dữ liệu thì sẽ không cho xóa
    private boolean checkData(Sach sach) {
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getActivity());
        ArrayList<PhieuMuon> listPhieuMuon = phieuMuonDao.getAll();
        for (int i = 0; i < listPhieuMuon.size(); i++) {
            if(sach.getMaSach() == listPhieuMuon.get(i).getMaSach()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClickUpdate(Object object) {
        sach = (Sach) object;
        onpenDialog(getActivity(), 1);
        adapter.setTypeAnimation(1);
    }

    private void onpenDialog(Context context, int type) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_item_sach_dialog);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMaSach = dialog.findViewById(R.id.tv_maSach);
        EditText edTenSach = dialog.findViewById(R.id.ed_tenSach);
        EditText edGiaThue = dialog.findViewById(R.id.ed_giaThue);
        Spinner spinner = dialog.findViewById(R.id.spn_loaiSach);
        Button btnSave = dialog.findViewById(R.id.btn_send);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog);
        tvTitle.setText("Thêm sách");
        tvMaSach.setVisibility(View.GONE);

        loaiSachDao = new LoaiSachDao(getActivity());
        listLoaiSach = loaiSachDao.getAll();
        spinnerAdapter = new LoaiSachSpinnerAdapter(getActivity(), listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        // lấy dữ liệu từ spinner (lấy mã loại sách)
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(type != 0) {
            tvMaSach.setVisibility(View.VISIBLE);
            tvMaSach.setText("Mã sách: " + sach.getMaSach());
            edTenSach.setText(sach.getTenSach());
            edGiaThue.setText(sach.getGiaThue() + "");

            for (int i = 0; i < listLoaiSach.size(); i++) {
                if(sach.getMaLoaiSach() == listLoaiSach.get(i).getMaLoai()) {
                    spinner.setSelection(i);
                }
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenSach = edTenSach.getText().toString().trim();
                String strGiaThue = edGiaThue.getText().toString().trim();
                sach.setTenSach(strTenSach);
                if(!strGiaThue.isEmpty()) {
                    sach.setGiaThue(Integer.parseInt(strGiaThue));
                }
                sach.setMaLoaiSach(maLoaiSach);

                if(validate(sach)) {
                    if(type == 0) {
                        if(dao.insert(sach) > 0) {
                            CustomToast.showMessage(getActivity(), "Thêm thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Thêm thất bại!");
                        }
                    } else {
                        if(dao.update(sach) > 0) {
                            CustomToast.showMessage(getActivity(), "Sửa thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Sửa thất bại!");
                        }
                    }

                    restartAdapter();
                    dialog.dismiss();
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

    private boolean validate(Sach sach) {
        String strTenSach = sach.getTenSach();
        String strGiaThue = sach.getGiaThue() + "";

        if(strTenSach.isEmpty() || strGiaThue.isEmpty()) {
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