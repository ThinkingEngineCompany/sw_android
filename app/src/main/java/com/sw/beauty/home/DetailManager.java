package com.sw.beauty.home;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.sw.beauty.PlayActivity;
import com.sw.beauty.R;
import com.sw.beauty.bean.Model;
import com.sw.beauty.bean.PicModelResponse;
import com.sw.beauty.bean.PicVideoModel;
import com.sw.beauty.network.DownloadTask;
import com.sw.beauty.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetailManager {

    private final ModelDetailActivity act;
    private final HomeViewModel viewModel;
    private final ViewPagerAdapter adapter;
    private final TextView pageHint;
    private final TextView modelTv;
    private final ProgressBar progressBar;
    private ArrayList<View> views = new ArrayList<>();
    private int pageSize = 1;
    private String videoUrl;
    private String modelUrl;
    private VideoView videoView;

    public DetailManager(ModelDetailActivity homeActivity) {
        this.act = homeActivity;
        Model model = (Model) homeActivity.getIntent().getExtras().get(ModelDetailActivity.EXTRA_MODEL);

        ViewModelProvider viewModelProvider = new ViewModelProvider(act);
        viewModel = viewModelProvider.get(HomeViewModel.class);
        ViewPager vp = act.findViewById(R.id.view_pager);
        progressBar = act.findViewById(R.id.progressBar);
        modelTv = act.findViewById(R.id.model_tv);
        adapter = new ViewPagerAdapter(getInitView(model.getImg()));
        vp.setAdapter(adapter);
        pageHint = act.findViewById(R.id.page_hint);

        viewModel.getPicM().observe(act, new Observer<PicModelResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(PicModelResponse modelResponse) {
                for (int i = 1; i < modelResponse.getData().size(); i++) {
                    PicVideoModel picVideoModel = modelResponse.getData().get(i);
                    if (picVideoModel.getType() == 0) {
                        views.add(getViewItem(picVideoModel.getUrl()));
                    } else if (picVideoModel.getType() == 1) {
                        videoUrl = picVideoModel.getUrl();
                    } else {
                        modelUrl = picVideoModel.getUrl();
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
                if (!TextUtils.isEmpty(modelUrl)) {

                    modelTv.setVisibility(View.VISIBLE);
                    String fileName = FileUtils.getModelFile(model);
                    if (new File(fileName).exists()) {
                        showComplete(model);
                    } else {
                        modelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // download to sd
                                modelTv.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                startDownload(model);
                            }
                        });
                    }
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

    private void startDownload(Model model) {
        DownloadTask.DownloadCallback callback = new DownloadTask.DownloadCallback() {
            @Override
            public void onProgressUpdate(int progress) {
                // 处理下载进度更新
                // 更新UI，显示下载进度
                progressBar.setProgress(progress);
            }

            @Override
            public void onDownloadComplete() {
                // 处理下载完成的逻辑
                // 更新UI，显示下载完成提示
                Toast.makeText(act, "下载成功", Toast.LENGTH_SHORT).show();
                showComplete(model);
            }

            @Override
            public void onDownloadFailed() {
                // 处理下载失败的逻辑
                // 更新UI，显示下载失败提示
                Toast.makeText(act, "下载失败", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                modelTv.setVisibility(View.VISIBLE);
            }
        };

        // 创建并执行下载任务
        DownloadTask downloadTask = new DownloadTask(callback);
        // filePath:/storage/emulated/0/Android/data/com.sw.beauty/files/3dmodel/4_xxx.model
        String filePath = FileUtils.getModelFile(model);
        downloadTask.execute(modelUrl, filePath);
    }

    private void showComplete(final Model model) {
        progressBar.setVisibility(View.GONE);
        modelTv.setVisibility(View.VISIBLE);
        modelTv.setText("使用");
        modelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开场景并使用
                PlayActivity.start(act, model);
            }
        });
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
