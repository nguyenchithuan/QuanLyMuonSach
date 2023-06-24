package edu.poly.assigment_ph26023.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.objects.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ObjectInterface objectInterface;
    private int type;

    public ThanhVienAdapter(Context context, ObjectInterface objectInterface) {
        this.context = context;
        this.objectInterface = objectInterface;
    }

    public void setData(ArrayList<ThanhVien> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTypeAnimation(int type) {
        this.type = type;
    }


    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_thanh_vien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if(thanhVien == null) {
            return;
        }

        holder.tvMaTv.setText("Mã thành viên: " + thanhVien.getMaTv());
        holder.tvTenTv.setText("Thành viên: " + thanhVien.getTenTv());
        holder.tvGioiTinh.setText("Giới tính: " + thanhVien.getGioiTinh());
        holder.tvSdt.setText("Số đt: " + thanhVien.getSdt());
        holder.tvNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());

        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] {Manifest.permission.CALL_PHONE},
                            999);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + thanhVien.getSdt()));
                    context.startActivity(intent);
                }
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickDelete(thanhVien);
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickUpdate(thanhVien);
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

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaTv;
        private TextView tvTenTv;
        private TextView tvGioiTinh;
        private TextView tvSdt;
        private TextView tvNamSinh;
        private TextView tvCall;
        private ImageView imgUpdate;
        private ImageView imgDelete;
        private CardView layout;



        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTv = itemView.findViewById(R.id.tv_maTv);
            tvTenTv = itemView.findViewById(R.id.tv_tenTv);
            tvGioiTinh = itemView.findViewById(R.id.tv_gioi_tinh);
            tvSdt = itemView.findViewById(R.id.tv_sdt);
            tvNamSinh = itemView.findViewById(R.id.tv_nam_sinh);
            tvCall = itemView.findViewById(R.id.tv_call);
            imgUpdate = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            layout = itemView.findViewById(R.id.layout_thanhVien);
        }
    }
}

