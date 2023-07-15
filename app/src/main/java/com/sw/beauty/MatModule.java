package com.sw.beauty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tencent.ncnnbodyseg.NcnnBodyseg;

public class MatModule implements SurfaceHolder.Callback {
    public static final int REQUEST_CAMERA = 100;
    private PlayActivity playActivity;
    private SurfaceView cameraView;
    private NcnnBodyseg ncnnbodyseg = new NcnnBodyseg();
    private int facing = 0;

    public MatModule(PlayActivity playActivity) {
        this.playActivity = playActivity;
        // not work
//        cameraView = (SurfaceView) playActivity.findViewById(R.id.cameraview);
        cameraView.setZOrderOnTop(false);
        cameraView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        cameraView.getHolder().addCallback(this);
        reload();
    }

    private void reload() {
        boolean ret_init = ncnnbodyseg.loadModel(playActivity.getAssets(), 0, 0, 0);
        if (!ret_init) {
            Log.e("MainActivity", "ncnnbodyseg loadModel failed");
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        ncnnbodyseg.setOutputWindow(holder.getSurface());
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public void onResume() {
        if (ContextCompat.checkSelfPermission(playActivity.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(playActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        ncnnbodyseg.openCamera(facing);
    }
}
