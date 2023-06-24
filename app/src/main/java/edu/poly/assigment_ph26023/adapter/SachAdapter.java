package edu.poly.assigment_ph26023.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.custom_toast.CustomToast;
import edu.poly.assigment_ph26023.dao.LoaiSachDao;
import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.objects.LoaiSach;
import edu.poly.assigment_ph26023.objects.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
    private ObjectInterface objectInterface;
    private int type;

    public SachAdapter(Context context, ObjectInterface objectInterface) {
        this.context = context;
        this.objectInterface = objectInterface;
    }

    public void setData(ArrayList<Sach> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTypeAnimation(int type) {
        this.type = type;
    }


    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        if(sach == null) {
            return;
        }

        LoaiSachDao dao = new LoaiSachDao(context);
        LoaiSach loaiSach = dao.getOne(sach.getMaLoaiSach() + "");

        holder.tvMaSach.setText("Mã sách: " + sach.getMaSach());
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());
        holder.tvGiaThue.setText("Giá thuê: " + sach.getGiaThue() + " vnđ");
        holder.tvTenLoai.setText("Tên loại sách: " + loaiSach.getTenLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickDelete(sach);
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickUpdate(sach);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        if(type == 0) { // tránh khi xóa, thêm, update cũng dùng animation nó làm khó chịu
            holder.layoutSach.startAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class  SachViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaSach;
        private TextView tvTenSach;
        private TextView tvGiaThue;
        private TextView tvTenLoai;
        private ImageView imgUpdate;
        private ImageView imgDelete;
        private CardView layoutSach;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaSach = itemView.findViewById(R.id.tv_maSach);
            tvTenSach = itemView.findViewById(R.id.tv_tenSach);
            tvGiaThue = itemView.findViewById(R.id.tv_giaThue);
            tvTenLoai = itemView.findViewById(R.id.tv_tenLoai);
            imgUpdate = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            layoutSach = itemView.findViewById(R.id.layout_sach);
        }
    }
}
