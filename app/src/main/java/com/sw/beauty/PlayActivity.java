package com.sw.beauty;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.sw.beauty.bean.Model;
import com.sw.beauty.util.FileUtils;
import com.unity3d.player.E;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.MultiWindowSupport;
import com.unity3d.player.UnityPlayer;

public class PlayActivity extends AppCompatActivity implements
        IUnityPlayerLifecycleEvents {
    private static final int AUDIO_PERMISSION_REQUEST_CODE = 0;
    private long exitTime = 0;
    protected UnityPlayer mUnityPlayer;
    private MatModule mat;
    private BeautyModule beauty;
    public static final String EXTRA_SCENE_FILE = "extra_scene_file";
    private OnPermissionSuccess onPermissionSuccess;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);
        UnityModule unity = new UnityModule(this);
        Log.e("xie", "onCreate:");
        beauty = new BeautyModule(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
//                UnityPlayer.UnitySendMessage("Main Camera", "loadDep",
//                        FileUtils.getFilePath("terrain.sw"));
                UnityPlayer.UnitySendMessage("Main Camera", "loadAndUseScene",
                        FileUtils.getFilePath("light_scene.sw"));
            }
        });
        mUnityPlayer = unity.mUnityPlayer;
        // sendMessage...
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mUnityPlayer.newIntent(intent);
    }

    @Override
    public void onUnityPlayerUnloaded() {
        moveTaskToBack(true);
    }

    @Override
    public void onUnityPlayerQuitted() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        beauty.onResume();
        if (MultiWindowSupport.getAllowResizableWindow(this) && !MultiWindowSupport.isMultiWindowModeChangedToTrue(this))
            return;

        mUnityPlayer.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        beauty.onStart();
        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;
        mUnityPlayer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MultiWindowSupport.saveMultiWindowMode(this);

        if (MultiWindowSupport.getAllowResizableWindow(this))
            return;

        mUnityPlayer.pause();
        beauty.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beauty.onStop();
        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;
        mUnityPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        try {
            mUnityPlayer.destroy();
        } catch (Exception e) {
            Log.e("xie", "e:" + e.getMessage());
        }
        super.onDestroy();
        beauty.onDestroy();

    }

    // Low Memory Unity
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mUnityPlayer.lowMemory();
//    }
//
//    // Trim Memory Unity
//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
//            mUnityPlayer.lowMemory();
//        }
//    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
//            return mUnityPlayer.injectEvent(event);
//        return super.dispatchKeyEvent(event);
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    public static void start(Context context, Model model) {
        Intent starter = new Intent(context, PlayActivity.class);
        if (model != null) {
            starter.putExtra(EXTRA_SCENE_FILE, FileUtils.getModelFile(model));
        }
        context.startActivity(starter);
    }

    public void checkAndRequestRecordPermission(OnPermissionSuccess onPermissionSuccess) {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_PERMISSION_REQUEST_CODE);
            this.onPermissionSuccess = onPermissionSuccess;
        } else {
            onPermissionSuccess.onOK();
        }
    }

    public interface OnPermissionSuccess {
        void onOK();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AUDIO_PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (onPermissionSuccess != null) {
                onPermissionSuccess.onOK();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}