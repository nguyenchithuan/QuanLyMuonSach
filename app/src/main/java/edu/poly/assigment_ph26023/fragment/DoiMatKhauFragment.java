package edu.poly.assigment_ph26023.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.DataOutput;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.ThuThuDao;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class DoiMatKhauFragment extends Fragment {

    private EditText edPassOld, edPass, edRePass;
    private Button btnSave, btnCancel;
    private ThuThuDao dao;
    private TextView tvLoiPassOld, tvLoiPass, tvLoiRePass, tvTenNguoiDung;
    SharedPreferences pref;

    public static DoiMatKhauFragment newInstance() {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doi_mat_khau_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edPassOld = view.findViewById(R.id.ed_pass_old);
        edPass = view.findViewById(R.id.ed_pass);
        edRePass = view.findViewById(R.id.ed_re_pass);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
        tvLoiPassOld = view.findViewById(R.id.tv_loi_pass_old);
        tvLoiPass = view.findViewById(R.id.tv_loi_pass);
        tvLoiRePass = view.findViewById(R.id.tv_loi_re_pass);
        tvTenNguoiDung = view.findViewById(R.id.tv_ten_nguoi_dung);


        pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        dao = new ThuThuDao(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
                clearBugForm();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });

        clearBugForm();
        setTenNguoiDung();
    }

    private void changePass() {
        if(validate()) {
            // lấy ra đối tượng nhờ username(maTT)
            String username = pref.getString("USERNAME", null);
            ThuThu thuThu = dao.getOne(username);
            // đổi mật khẩu
            thuThu.setMatKhau(edPass.getText().toString().trim());
            if(dao.update(thuThu) > 0) {
                // Lưu lại giá trị đã đổi vào trong pref
                writePref(edPass.getText().toString().trim());
                CustomToast.showMessage(getActivity(), "Thay đổi mật khẩu thành công");
                clearForm();
            } else {
                CustomToast.showMessage(getActivity(), "Thay đổi mật khẩu thất bại!");
            }
        }
    }

    public boolean validate() {
        String strPassOld = edPassOld.getText().toString();
        String strPass = edPass.getText().toString();
        String strRePass = edRePass.getText().toString();
        String passOld = pref.getString("PASSWORD", null);

        clearBugForm();

        if(strPassOld.isEmpty()) {
            tvLoiPassOld.setText("Mời nhập dữ liệu");
            return false;
        } else if (strPass.isEmpty()) {
            tvLoiPass.setText("Mời nhập dữ liệu");
            return false;
        } else if(strRePass.isEmpty()) {
            tvLoiRePass.setText("Mời nhập dữ liệu");
            return false;
        } else if(!passOld.equals(strPassOld)) {
            tvLoiPassOld.setText("Mật khẩu cũ sai");
            return false;
        } else if(!strPass.equals(strRePass)) {
            tvLoiRePass.setText("Mật khẩu không trùng khớp");
            return false;
        }

        return true;
    }

    public void clearForm() {
        edPassOld.setText("");
        edPass.setText("");
        edRePass.setText("");
    }

    public void clearBugForm() {
        tvLoiPassOld.setText("");
        tvLoiPass.setText("");
        tvLoiRePass.setText("");
    }

    public void writePref(String pass) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("PASSWORD", pass);
        editor.commit();
    }

    public void setTenNguoiDung() {
        String username = pref.getString("USERNAME", null);
        ThuThu thuThu = new ThuThu();
        thuThu = dao.getOne(username);
        tvTenNguoiDung.setText(thuThu.getHoTenTT());
    }
}