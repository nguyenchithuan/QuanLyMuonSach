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

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.ThuThuDao;
import edu.poly.assigment_ph26023.objects.ThuThu;

public class TaoTaiKhoanFragment extends Fragment {
    private EditText edAddUser, edAddName, edAddPass, edAddRePass;
    private TextView tvBugAddUser, tvBugAddName, tvBugAddPass, tvBugAddRePass;
    private Button btnSave, btnCancel;
    private ThuThuDao dao;

    public static TaoTaiKhoanFragment newInstance() {
        TaoTaiKhoanFragment fragment = new TaoTaiKhoanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tao_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edAddUser = view.findViewById(R.id.ed_add_user);
        edAddName = view.findViewById(R.id.ed_add_name);
        edAddPass = view.findViewById(R.id.ed_add_pass);
        edAddRePass = view.findViewById(R.id.ed_add_re_pass);
        tvBugAddUser = view.findViewById(R.id.tv_bug_add_user);
        tvBugAddName = view.findViewById(R.id.tv_bug_add_name);
        tvBugAddPass = view.findViewById(R.id.tv_bug_add_pass);
        tvBugAddRePass = view.findViewById(R.id.tv_bug_add_re_pass);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        dao = new ThuThuDao(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
                clearBugForm();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addUser(); 
            }
        });

        clearBugForm();
    }

    private void addUser() {
        if(validate()) {
            ThuThu thuThu = new ThuThu();
            thuThu.setMaTT(edAddUser.getText().toString().trim());
            thuThu.setHoTenTT(edAddName.getText().toString().trim());
            thuThu.setMatKhau(edAddPass.getText().toString().trim());
            if(dao.insert(thuThu) > 0) { // nếu nhập trùng id thì xẩy ra lỗi, và khi đó không trả về index > 0
                CustomToast.showMessage(getActivity(), "Tạo tài khoản thành công");
                clearForm();
            } else {
                CustomToast.showMessage(getActivity(),"Tạo tài khoản thất bại");
            }
        }

    }

    public boolean validate() {
        String strAddUser = edAddUser.getText().toString().trim();
        String strAddName = edAddName.getText().toString().trim();
        String strAddPass = edAddPass.getText().toString().trim();
        String strAddRePass = edAddRePass.getText().toString().trim();

        clearBugForm();

        if(strAddUser.isEmpty()) {
            tvBugAddUser.setText("Mời nhập dữ liệu");
            return false;
        } else if(strAddName.isEmpty()) {
            tvBugAddName.setText("Mời nhập dữ liệu");
            return false;
        } else if(strAddPass.isEmpty()) {
            tvBugAddPass.setText("Mời nhập dữ liệu");
            return false;
        } else if(strAddRePass.isEmpty()) {
            tvBugAddRePass.setText("Mời nhập dữ liệu");
            return false;
        } else if(strAddUser.length() < 5) {
            tvBugAddUser.setText("Tối thiểu 5 ký tự");
            return false;
        } else if(!strAddPass.equals(strAddRePass)) {
            tvBugAddRePass.setText("Mật khẩu không trùng khớp");
            return false;
        } else if(dao.checkUserName(strAddUser) > 0) { // mã đã tồn tại
            tvBugAddUser.setText("Tài khoản đã tồn tại");
            return false;
        }

        return true;
    }

    private void clearBugForm() {
        tvBugAddUser.setText("");
        tvBugAddName.setText("");
        tvBugAddPass.setText("");
        tvBugAddRePass.setText("");
    }

    private void clearForm() {
        edAddUser.setText("");
        edAddName.setText("");
        edAddPass.setText("");
        edAddRePass.setText("");
    }
}