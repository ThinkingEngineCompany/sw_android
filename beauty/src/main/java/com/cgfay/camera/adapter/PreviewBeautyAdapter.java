package com.cgfay.camera.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cgfay.video.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 美颜列表适配器
 */
public class PreviewBeautyAdapter extends RecyclerView.Adapter<PreviewBeautyAdapter.ImageHolder> {

    private Context mContext;
    private int mSelected = 0;
    // 滤镜名称
//    private List<String> mItemNames;

    private ArrayList<BeautyData> bd;

    public PreviewBeautyAdapter(Context context) {
        mContext = context;
        String[] beautyLists = mContext.getResources().getStringArray(R.array.preview_beauty);
//        mItemNames = Arrays.asList(beautyLists);
    }

    public PreviewBeautyAdapter(Context context, ArrayList<BeautyData> bd) {
        mContext = context;
        this.bd = bd;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_preview_beauty_view, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        final int p = position;
        ViewGroup.LayoutParams layoutParams = holder.beautyRoot.getLayoutParams();
        if (!bd.get(position).isShow) {
            layoutParams.width = 0;
            holder.beautyRoot.setLayoutParams(layoutParams);
            return;
        }
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.beautyRoot.setLayoutParams(layoutParams);

        holder.beautyName.setText(bd.get(position).name);
        if (bd.get(position).iconRes == 0) {
            holder.imageView.setVisibility(View.GONE);
        } else {
            holder.imageView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(),
                    bd.get(position).iconRes, null));
            holder.imageView.setVisibility(View.VISIBLE);
        }
        if (position == mSelected && bd.get(position).type == 0) {
            holder.beautyPanel.setBackgroundResource(R.drawable.ic_camera_effect_selected);
        } else {
            holder.beautyPanel.setBackgroundResource(0);
        }
        holder.beautyRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected == p) {
                    return;
                }

                int lastSelected = mSelected;
                if (bd.get(p).type == 1 || bd.get(p).type == 2) {
                    mSelected = -1;
                } else {
                    mSelected = p;
                }
                if (lastSelected != -1) {
                    notifyItemChanged(lastSelected, 0);
                }
                notifyItemChanged(p, 0);
                if (mBeautySelectedListener != null) {
                    mBeautySelectedListener.onBeautySelected(p, bd.get(p));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (bd == null) ? 0 : bd.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        // 根布局
        public LinearLayout beautyRoot;
        // 背景框
        public FrameLayout beautyPanel;
        // 预览文字
        public TextView beautyName;
        public ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            beautyRoot = (LinearLayout) itemView.findViewById(R.id.item_beauty_root);
            beautyPanel = (FrameLayout) itemView.findViewById(R.id.item_beauty_panel);
            beautyName = (TextView) itemView.findViewById(R.id.item_beauty_name);
            imageView = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }

    public interface OnBeautySelectedListener {
        void onBeautySelected(int position, BeautyData beautyData);
    }

    private OnBeautySelectedListener mBeautySelectedListener;

    public void addOnBeautySelectedListener(OnBeautySelectedListener listener) {
        mBeautySelectedListener = listener;
    }

    /**
     * 滚动到当前选中位置
     *
     * @param selected
     */
    public void scrollToCurrentSelected(int selected) {
        int lastSelected = mSelected;
        mSelected = selected;
        notifyItemChanged(lastSelected, 0);
        notifyItemChanged(mSelected, 0);
    }

    /**
     * 获取选中索引
     *
     * @return
     */
    public int getSelectedPosition() {
        return mSelected;
    }

    public static class BeautyData {
        public int iconRes = 0;
        public String name;
        // 0: 普通数据
        // 1: 父条目
        // 2: 返回
        // 3: 子条目
        public int type;
        // 父条目在子条目展示时隐藏
        public boolean isShow = true;
        // 父条目的子条目
        public ArrayList<BeautyData> items;
    }

}
