package edu.poly.assigment_ph26023.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.poly.assigment_ph26023.R;
import edu.poly.assigment_ph26023.objects.Top;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {

    private Context context;
    private ArrayList<Top> list;

    public TopAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Top> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_top_10, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Top top = list.get(position);
        if(top == null) {
            return;
        }

        holder.tvTenSach.setText(top.getTenSach());
        holder.tvSoLuong.setText(top.getSoLuong() + "");
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class  TopViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSach;
        private TextView tvSoLuong;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSach = itemView.findViewById(R.id.tv_tenSach);
            tvSoLuong = itemView.findViewById(R.id.tv_soLuong);
        }
    }
}
