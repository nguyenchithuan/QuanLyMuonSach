package edu.poly.assigment_ph26023.adapter.spinner_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class ThanhVienSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ThanhVien> list;

    public ThanhVienSpinnerAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_custom_spinner_selected, parent, false);
        }

        TextView tvMa = view.findViewById(R.id.tv_ma_selected);
        TextView tvTen = view.findViewById(R.id.tv_data_selected);

        ThanhVien thanhVien = list.get(position);

        if(thanhVien != null) {
            tvMa.setText(thanhVien.getMaTv() + ". ");
            tvTen.setText(thanhVien.getTenTv());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_custom_spinner_drop_down, parent, false);
        }

        TextView tvMa = view.findViewById(R.id.tv_ma_dropdown);
        TextView tvTen = view.findViewById(R.id.tv_data_dropdown);

        ThanhVien thanhVien = list.get(position);

        if(thanhVien != null) {
            tvMa.setText(thanhVien.getMaTv() + ". ");
            tvTen.setText(thanhVien.getTenTv());
        }

        return view;
    }
}
