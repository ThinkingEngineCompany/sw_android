package com.sw.beauty.home;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.sw.beauty.R;
import com.sw.beauty.bean.Model;
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
    private String videoUrl;
    private VideoView videoView;

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
                for (int i = 1; i < modelResponse.getData().size(); i++) {
                    PicVideoModel picVideoModel = modelResponse.getData().get(i);
                    if (picVideoModel.getType() == 0) {
                        views.add(getViewItem(picVideoModel.getUrl()));
                    } else {
                        videoUrl = picVideoModel.getUrl();
                    }
                }
                if (!TextUtils.isEmpty(videoUrl)) {
                    views.get(0).findViewById(R.id.play_iv).setVisibility(View.VISIBLE);
                    views.get(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (videoView == null) {
                                videoView = new VideoView(act);
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                params.gravity = Gravity.CENTER_HORIZONTAL;
                                ((FrameLayout) views.get(0)).addView(videoView, params);
                                videoView.setVideoPath(videoUrl);
                                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        videoView.setVisibility(View.GONE);
                                    }
                                });
                            }
                            videoView.setVisibility(View.VISIBLE);
                            videoView.start();
                        }
                    });
                }
                adapter.notifyDataSetChanged();
                pageSize = modelResponse.getData().size();
                pageHint.setText("1/" + pageSize);
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (videoView != null) {
                    videoView.stopPlayback();
                    videoView.setVisibility(View.GONE);
                }
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
