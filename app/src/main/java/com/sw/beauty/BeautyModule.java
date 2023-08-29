package com.sw.beauty;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cgfay.camera.widget.CameraTextureView;
import com.sw.beauty.ui.CameraPreviewPresenterX2;

public class BeautyModule {
    private static final String TAG = "BeautyModule";
    private final Handler mMainHandler;
    private final PlayActivity mActivity;
    private CameraPreviewPresenterX2 mPreviewPresenter;
    private CameraTextureView mCameraTextureView;
    private CameraTextureView mRawCameraTextureView;
    private RecordModule recordModule;

    public BeautyModule(PlayActivity playActivity) {
        mActivity = playActivity;
        mMainHandler = new Handler(Looper.getMainLooper());
        mPreviewPresenter = new CameraPreviewPresenterX2(this);
        recordModule = new RecordModule(playActivity, mPreviewPresenter);

        onCreate();
    }

    public void onCreate() {
        ConstraintLayout constraintLayout = mActivity.findViewById(R.id.parent);
        mCameraTextureView = new CameraTextureView(mActivity);
        mRawCameraTextureView = new CameraTextureView(mActivity);

//        mCameraTextureView.addOnTouchScroller(mTouchScroller);
//        mCameraTextureView.addMultiClickListener(mMultiClickListener);

        mCameraTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        mRawCameraTextureView.setSurfaceTextureListener(mRawSurfaceTextureListener);
        mCameraTextureView.setOpaque(false);
        constraintLayout.addView(mCameraTextureView, 1,
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        constraintLayout.addView(mRawCameraTextureView, 1,
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPreviewPresenter.onAttach(mActivity);
        mPreviewPresenter.onCreate();

        recordModule.onCreate();
    }


    public void onStart() {
        mPreviewPresenter.onStart();
    }


    public void onResume() {
//        enhancementBrightness();
        mPreviewPresenter.onResume();
        Log.d(TAG, "onResume: ");
    }

    public void onPause() {
        mPreviewPresenter.onPause();
        Log.d(TAG, "onPause: ");
    }

    public void onStop() {
        mPreviewPresenter.onStop();
        Log.d(TAG, "onStop: ");
    }

    public void onDestroy() {
        mPreviewPresenter.onDetach();
        mPreviewPresenter.onDestroy();
        mMainHandler.removeCallbacksAndMessages(null);
        Log.d(TAG, "onDestroy: ");
    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mPreviewPresenter.onSurfaceCreated(surface);
            mPreviewPresenter.onSurfaceChanged(width, height);
            Log.d(TAG, "onSurfaceTextureAvailable: ");
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            mPreviewPresenter.onSurfaceChanged(width, height);
            Log.d(TAG, "onSurfaceTextureSizeChanged: ");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mPreviewPresenter.onSurfaceDestroyed();
            Log.d(TAG, "onSurfaceTextureDestroyed: ");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    private TextureView.SurfaceTextureListener mRawSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mPreviewPresenter.onRawSurfaceCreated(surface);
            mPreviewPresenter.onRawSurfaceChanged(width, height);
            Log.d(TAG, "onSurfaceTextureAvailable: ");
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            mPreviewPresenter.onRawSurfaceChanged(width, height);
            Log.d(TAG, "onSurfaceTextureSizeChanged: ");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mPreviewPresenter.onRawSurfaceDestroyed();
            Log.d(TAG, "onSurfaceTextureDestroyed: ");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    public void deleteProgressSegment() {

    }

    public void hideOnRecording() {

    }

    public void updateRecordProgress(float progress) {

    }

    public void addProgressSegment(float mCurrentProgress) {

    }

    public void resetAllLayout() {
        // 录制完成走这里
        mMainHandler.post(()-> {
            recordModule.resetAllLayout();
        });
    }

    public void showConcatProgressDialog() {

    }

    public void hideConcatProgressDialog() {

    }

    public void showToast(String msg) {

    }

    public void showFps(float fps) {

    }
}
