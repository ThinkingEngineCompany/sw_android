package com.sw.beauty;

import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

public class UnityModule {
    PlayActivity playActivity;
    protected UnityPlayer mUnityPlayer;
    public UnityModule(PlayActivity playActivity) {
        this.playActivity = playActivity;
        initUnity();
    }
    private void initUnity() {
        String cmdLine = updateUnityCommandLineArguments(playActivity.getIntent().getStringExtra("unity"));
        playActivity.getIntent().putExtra("unity", cmdLine);

        mUnityPlayer = new UnityPlayer(playActivity);
        FrameLayout mView = playActivity.findViewById(R.id.mView);
        if (mUnityPlayer.getChildAt(0) instanceof SurfaceView) {
            SurfaceView surfaceView = (SurfaceView) mUnityPlayer.getChildAt(0);
            surfaceView.setZOrderOnTop(false);
        }
        mView.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }


}
