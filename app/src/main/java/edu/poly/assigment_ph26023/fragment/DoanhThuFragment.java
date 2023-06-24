package edu.poly.assigment_ph26023.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.PhieuMuonDao;
import edu.poly.assigment_ph26023.dao.ThongKeDao;
import edu.poly.assigment_ph26023.objects.PhieuMuon;

public class DoanhThuFragment extends Fragment {
    private Button btnDoanhThu;
    private TextView tvDoanhThu;
    private TextView tvTuNgay, tvDenNgay;
    private int mYear, mMonth, mDay;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int year, month, day;



    public static DoanhThuFragment newInstance() {
        DoanhThuFragment fragment = new DoanhThuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDoanhThu = view.findViewById(R.id.btn_doanh_thu);
        tvDoanhThu = view.findViewById(R.id.tv_doanhThu);
        tvTuNgay = view.findViewById(R.id.tv_tuNgay);
        tvDenNgay = view.findViewById(R.id.tv_denNgay);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dùng calendar để lấy thời gian hiện tại khi người dùng ấn vào
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // set thời gian cho calendar
                        calendar.set(year, month, dayOfMonth);
                        // lấy thời gian trong calenda
                        tvTuNgay.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day); // 3 tham số này để hiển thị lên DatePickerDialog

                dialog.show();
            }
        });

        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        tvDenNgay.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day);

                dialog.show();
            }
        });



        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = tvTuNgay.getText().toString();
                String denNgay = tvDenNgay.getText().toString();
                ThongKeDao thongKeDao = new ThongKeDao(getActivity());
                tvDoanhThu.setText(thongKeDao.getDoanhThu(tuNgay, denNgay) + " VNĐ");
            }
        });
    }




}