package edu.poly.assigment_ph26023.adapter;

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

import edu.poly.assigment_ph26023.doi_tuong_Interface.ObjectInterface;
import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.objects.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private ObjectInterface objectInterface;
    private int type;

    public LoaiSachAdapter(Context context, ObjectInterface objectInterface) {
        this.context = context;
        this.objectInterface = objectInterface;
    }

    public void setData(ArrayList<LoaiSach> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTypeAnimation(int type) {
        this.type = type;
    }


    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);

        if(loaiSach == null) {
            return;
        }

        holder.tvMaLoai.setText("Mã loại: " + loaiSach.getMaLoai());
        holder.tvTenLoai.setText("Tên loại: " + loaiSach.getTenLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickDelete(loaiSach);
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickUpdate(loaiSach);
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

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaLoai;
        private TextView tvTenLoai;
        private ImageView imgDelete;
        private ImageView imgUpdate;
        private CardView layout;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaLoai = itemView.findViewById(R.id.tv_maLoai);
            tvTenLoai = itemView.findViewById(R.id.tv_tenLoai);
            imgDelete = itemView.findViewById(R.id.img_delete);
            imgUpdate = itemView.findViewById(R.id.img_edit);
            layout = itemView.findViewById(R.id.layout_loaiSach);
        }
    }

}
