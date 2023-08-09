package com.sw.beauty;

import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerForActivityOrService;

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

        mUnityPlayer = new UnityPlayerForActivityOrService(playActivity, playActivity);
        FrameLayout mView = playActivity.findViewById(R.id.mView);
        // mUnityPlayer.getFrameLayout()
        //  com.unity3d.player.K - FrameLayout
        //      com.unity3d.player.a - SurfaceView
        //  com.unity3d.player.E
        if (mUnityPlayer.getFrameLayout().getChildAt(0) instanceof FrameLayout) {
            FrameLayout fl = (FrameLayout) mUnityPlayer.getFrameLayout().getChildAt(0);
            if (fl.getChildAt(0) instanceof SurfaceView) {
                SurfaceView surfaceView = (SurfaceView) fl.getChildAt(0);
                Log.e("xie", "setZorderFalse:");
                surfaceView.setZOrderOnTop(false);
            }
        }

        mView.addView(mUnityPlayer.getFrameLayout());
        mUnityPlayer.getFrameLayout().requestFocus();
    }

    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }


}
