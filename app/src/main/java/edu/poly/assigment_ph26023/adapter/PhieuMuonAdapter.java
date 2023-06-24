package edu.poly.assigment_ph26023.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.SachDao;
import edu.poly.assigment_ph26023.dao.ThanhVienDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.objects.PhieuMuon;
import edu.poly.assigment_ph26023.objects.Sach;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private ObjectInterface objectInterface;
    private int type = 0;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonAdapter(Context context, ObjectInterface objectInterface) {
        this.context = context;
        this.objectInterface = objectInterface;
    }

    public void setData(ArrayList<PhieuMuon> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTypeAnimation(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        if(phieuMuon == null) {
            return;
        }

        holder.tvMaPM.setText("Mã phiếu: " + phieuMuon.getMaPM());
        holder.tvMaTT.setText("Thủ thư: " + phieuMuon.getMaTT());
        // Set thành viên
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        ThanhVien thanhVien = thanhVienDao.getOne(phieuMuon.getMaTV() + "");
        holder.tvMaTV.setText("Thành viên: " + thanhVien.getTenTv());
        // Set sách
        SachDao sachDao = new SachDao(context);
        Sach sach = sachDao.getOne(phieuMuon.getMaSach() + "");
        holder.tvMaSach.setText("Tên sách: " + sach.getTenSach());
        holder.tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue() + "vnđ");
        holder.tvNgayThue.setText("Ngày thuê: " +format.format(phieuMuon.getNgayThue()));

        if(phieuMuon.getTraSach() == 1) {
            holder.tvTraSach.setTextColor(Color.BLUE);
            holder.tvTraSach.setText("Đã trả sách");
        } else {
            holder.tvTraSach.setTextColor(Color.RED);
            holder.tvTraSach.setText("Chưa trả sách");
        }

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickUpdate(phieuMuon);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickDelete(phieuMuon);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        if(type == 0) { // tránh khi xóa, thêm, update cũng dùng animation nó làm khó chịu
            holder.layout.startAnimation(animation);
        }

    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaPM;
        private TextView tvMaTT;
        private TextView tvMaTV;
        private TextView tvMaSach;
        private TextView tvTienThue;
        private TextView tvNgayThue;
        private TextView tvTraSach;
        private ImageView imgUpdate;
        private ImageView imgDelete;
        private CardView layout;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tv_maPM);
            tvMaTT = itemView.findViewById(R.id.tv_maTT);
            tvMaTV = itemView.findViewById(R.id.tv_maTv);
            tvMaSach = itemView.findViewById(R.id.tv_maSach);
            tvTienThue = itemView.findViewById(R.id.tv_tienThue);
            tvNgayThue = itemView.findViewById(R.id.tv_ngayThue);
            tvTraSach = itemView.findViewById(R.id.chk_traSach);
            imgUpdate = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            layout = itemView.findViewById(R.id.layout_phieuMuon);
        }
    }
}
