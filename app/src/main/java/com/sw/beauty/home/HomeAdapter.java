package com.sw.beauty.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sw.beauty.R;
import com.sw.beauty.bean.Model;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Model> dataList;

    private ClickL c;

    public HomeAdapter(List<Model> dataList, ClickL onClickListener) {
        this.dataList = dataList;
        this.c = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int p = position;
        holder.bindData(dataList.get(position));
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onClick(dataList.get(p));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView descView;
        private ImageView img;
        public View allView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allView = itemView;
            titleView = itemView.findViewById(R.id.title_tv);
            descView = itemView.findViewById(R.id.desc_tv);
            img = itemView.findViewById(R.id.show_img_iv);
        }

        public void bindData(Model data) {
            titleView.setText(data.getName());
            descView.setText(data.getDescription());
            Glide.with(img).load(data.getImg()).into(img);
        }
    }

    public interface ClickL {
        void onClick(Model model);
    }
}
