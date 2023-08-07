package com.sw.beauty.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.sw.beauty.R;
import com.sw.beauty.bean.Model;
import com.sw.beauty.bean.ModelResponse;
import com.sw.beauty.bean.PicModelResponse;
import com.sw.beauty.bean.PicVideoModel;

import java.util.ArrayList;
import java.util.List;

public class DetailManager {

    private final ModelDetailActivity act;
    private final HomeViewModel viewModel;
    private final ViewPagerAdapter adapter;
    private final TextView pageHint;
    private ArrayList<View> views = new ArrayList<>();
    private int pageSize = 1;

    public DetailManager(ModelDetailActivity homeActivity) {
        this.act = homeActivity;
        Model model = (Model) homeActivity.getIntent().getExtras().get(ModelDetailActivity.EXTRA_MODEL);


        ViewModelProvider viewModelProvider = new ViewModelProvider(act);
        viewModel = viewModelProvider.get(HomeViewModel.class);
        ArrayList<PicVideoModel> ms = new ArrayList<>();
        ViewPager vp = act.findViewById(R.id.view_pager);

        adapter = new ViewPagerAdapter(getInitView(model.getImg()));
        vp.setAdapter(adapter);
        pageHint = act.findViewById(R.id.page_hint);

        viewModel.getPicM().observe(act, new Observer<PicModelResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(PicModelResponse modelResponse) {
                ms.addAll(modelResponse.getData());
                views.clear();
                for (int i = 0; i < modelResponse.getData().size(); i++) {
                    views.add(getViewItem(modelResponse.getData().get(i).getUrl()));
                }
                adapter.notifyDataSetChanged();
                pageSize = modelResponse.getData().size();
                pageHint.setText("1/" + pageSize);
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageHint.setText((position + 1) + "/" + pageSize);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewModel.getPicModel(model.getId());

        ((TextView) act.findViewById(R.id.title_tv)).setText(model.getName());
        ((TextView) act.findViewById(R.id.desc_tv)).setText(model.getDescription());

    }

    private List<View> getInitView(String url) {
        views.clear();
        views.add(getViewItem(url));
        return views;
    }

    private View getViewItem(String url) {
        View view = LayoutInflater.from(act).inflate(R.layout.item_layout_detail, null);
        ImageView iv = view.findViewById(R.id.show_img_iv);
        Glide.with(act).load(url).into(iv);
        return view;
    }
}
