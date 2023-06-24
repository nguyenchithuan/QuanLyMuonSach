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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.SachDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.adapter.LoaiSachAdapter;
import edu.poly.assigment_ph26023.dao.LoaiSachDao;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.Sach;

public class LoaiSachFragment extends Fragment implements ObjectInterface {
    private RecyclerView rcvLoaiSach;
    private LoaiSachAdapter adapter;
    private ArrayList<LoaiSach> list;
    private LoaiSachDao dao;
    private FloatingActionButton falLoaiSach;
    private LoaiSach loaiSach;

    private EditText edSearch;

    public static LoaiSachFragment newInstance() {
        LoaiSachFragment fragment = new LoaiSachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edSearch = view.findViewById(R.id.ed_search);
        falLoaiSach = view.findViewById(R.id.fab_loai_sach);
        rcvLoaiSach = view.findViewById(R.id.rcv_loai_sach);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvLoaiSach.setLayoutManager(layoutManager);

        dao = new LoaiSachDao(getActivity());
        list = dao.getAll();

        adapter = new LoaiSachAdapter(getActivity(), this);
        adapter.setData(list);
        adapter.setTypeAnimation(0);
        rcvLoaiSach.setAdapter(adapter);

        falLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpenDialog(getActivity(), 0); // thêm
                adapter.setTypeAnimation(1);
            }
        });

        loaiSach = new LoaiSach();

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
        loaiSach = (LoaiSach) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkData(loaiSach)) {
                    dao.delete(loaiSach.getMaLoai() + "");
                    restartAdapter();
                    adapter.setTypeAnimation(1);
                    CustomToast.showMessage(getActivity(), "Delete thành công");
                } else {
                    CustomToast.showMessage(getActivity(), "Delete thất bại!");
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
    private boolean checkData(LoaiSach loaiSach) {
        SachDao sachDao = new SachDao(getActivity());
        ArrayList<Sach> listSach = sachDao.getAll();
        for (int i = 0; i < listSach.size(); i++) {
            if(loaiSach.getMaLoai() == listSach.get(i).getMaLoaiSach()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClickUpdate(Object object) {
        loaiSach = (LoaiSach) object;
        onpenDialog(getActivity(), 1); // update
        adapter.setTypeAnimation(1);
    }

    public void restartAdapter() {
        list = dao.getAll();
        adapter.setData(list);
    }

    public void onpenDialog(Context context, int type) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_item_loai_sach_dialog);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvMaLoai = dialog.findViewById(R.id.tv_maLoai);
        EditText edTenLoai = dialog.findViewById(R.id.ed_ten_loai);
        Button btnSave = dialog.findViewById(R.id.btn_send);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        tvMaLoai.setVisibility(View.GONE);
        tvTitle.setText("Thêm loại sách");

        // nếu type khác 0 tức là nó update
        if(type != 0) {
            tvTitle.setText("Sửa loại sách");
            tvMaLoai.setVisibility(View.VISIBLE);
            tvMaLoai.setText("Mã loại: " + loaiSach.getMaLoai());
            edTenLoai.setText(loaiSach.getTenLoai());
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
                String strTenLoai = edTenLoai.getText().toString().trim();
                loaiSach.setTenLoai(strTenLoai);

                if(validate(loaiSach)) {

                    if(type == 0) { // dialog thêm
                        if(dao.insert(loaiSach) > 0) {
                            CustomToast.showMessage(getActivity(), "Thêm thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Thêm thất bại!");
                        }
                    } else {
                        if(dao.update(loaiSach) > 0) {
                            CustomToast.showMessage(getActivity(), "Sửa thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Sửa thất bại!");
                        }
                    }
                    dialog.dismiss();
                    restartAdapter();

                } else {
                    CustomToast.showMessage(getActivity(), "Mời nhập dữ liệu!");
                }

            }
        });


        dialog.show();
    }

    private boolean validate(LoaiSach loaiSach) {
        String strTenLoai = loaiSach.getTenLoai();
        if(strTenLoai.isEmpty()) {
            return false;
        }
        return true;
    }
}