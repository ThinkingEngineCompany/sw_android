package com.sw.beauty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cgfay.camera.fragment.CameraPreviewFragment;
import com.cgfay.facedetect.engine.FaceTracker;
import com.cgfay.uitls.utils.NotchUtils;
import com.sw.beauty.ui.CameraPreviewFragmentX;

public class MatAndBeautyActivity extends AppCompatActivity {
    private static final String FRAGMENT_CAMERA = "fragment_camera";

    private CameraPreviewFragmentX mPreviewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cgfay.video.R.layout.activity_camera);
        if (null == savedInstanceState && mPreviewFragment == null) {
            mPreviewFragment = new CameraPreviewFragmentX();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(com.cgfay.video.R.id.fragment_container, mPreviewFragment, FRAGMENT_CAMERA)
                    .commit();
        }
        faceTrackerRequestNetwork();
    }

    /**
     * 人脸检测SDK验证，可以替换成自己的SDK
     */
    private void faceTrackerRequestNetwork() {
        new Thread(() -> FaceTracker.requestFaceNetwork(MatAndBeautyActivity.this)).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleFullScreen();
        registerHomeReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterHomeReceiver();
    }

    private void handleFullScreen() {
        // 是否全面屏
        if (NotchUtils.hasNotchScreen(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                getWindow().setAttributes(lp);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mPreviewFragment == null || !mPreviewFragment.onBackPressed()) {
            super.onBackPressed();
            overridePendingTransition(0, com.cgfay.video.R.anim.anim_slide_down);
        }
    }

    /**
     * 注册服务
     */
    private void registerHomeReceiver() {
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomePressReceiver, homeFilter);
    }

    /**
     * 注销服务
     */
    private void unRegisterHomeReceiver() {
        unregisterReceiver(mHomePressReceiver);
    }

    /**
     * Home按键监听服务
     */
    private BroadcastReceiver mHomePressReceiver = new BroadcastReceiver() {
        private final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (TextUtils.isEmpty(reason)) {
                    return;
                }
                // 当点击了home键时需要停止预览，防止后台一直持有相机
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    CameraPreviewFragment fragment = (CameraPreviewFragment) getSupportFragmentManager()
                            .findFragmentByTag(FRAGMENT_CAMERA);
                    if (fragment != null) {
                        fragment.cancelRecordIfNeeded();
                    }
                }
            }
        }
    };
    public static void start(Context context) {
        Intent starter = new Intent(context, MatAndBeautyActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

}
