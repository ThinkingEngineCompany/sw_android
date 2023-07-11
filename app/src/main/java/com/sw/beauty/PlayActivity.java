package com.sw.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.MultiWindowSupport;
import com.unity3d.player.UnityPlayer;

public class PlayActivity extends AppCompatActivity implements
        IUnityPlayerLifecycleEvents {
    private long exitTime = 0;
    protected UnityPlayer mUnityPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);
        initUnity();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }

    private void initUnity() {
        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);

        mUnityPlayer = new UnityPlayer(this);
        FrameLayout mView = findViewById(R.id.mView);
        if (mUnityPlayer.getChildAt(0) instanceof SurfaceView) {
            ((SurfaceView) mUnityPlayer.getChildAt(0)).setZOrderOnTop(false);
        }
        mView.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();
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
    protected void onStop() {
        super.onStop();
        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;
        mUnityPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MultiWindowSupport.getAllowResizableWindow(this) && !MultiWindowSupport.isMultiWindowModeChangedToTrue(this))
            return;

        mUnityPlayer.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!MultiWindowSupport.getAllowResizableWindow(this))
            return;
        mUnityPlayer.resume();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}