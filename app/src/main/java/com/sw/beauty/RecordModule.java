package com.sw.beauty;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;

import com.cgfay.camera.fragment.CameraPreviewFragment;
import com.cgfay.camera.model.GalleryType;
import com.cgfay.camera.widget.RecordButton;
import com.cgfay.uitls.dialog.DialogBuilder;
import com.cgfay.uitls.utils.PermissionUtils;
import com.sw.beauty.ui.CameraPreviewPresenterX2;

public class RecordModule implements View.OnClickListener {
    private final PlayActivity mActivity;
    private RecordButton mBtnRecord;
    // 刪除布局
    private LinearLayout mLayoutDelete;
    // 视频删除按钮
    private Button mBtnDelete;
    // 视频预览按钮
    private Button mBtnNext;
    private Dialog mDialog;
    private CameraPreviewPresenterX2 pre;
    public RecordModule(PlayActivity playActivity, CameraPreviewPresenterX2 pre) {
        mActivity = playActivity;
        this.pre = pre;
    }

    public void onCreate() {
        mBtnRecord = mActivity.findViewById(R.id.btn_record);
        mBtnRecord.setOnClickListener(this);
        mBtnRecord.addRecordStateListener(mRecordStateListener);

        mLayoutDelete = mActivity.findViewById(R.id.layout_delete);
        mBtnDelete = mActivity.findViewById(R.id.btn_record_delete);
        mBtnDelete.setOnClickListener(this);
        mBtnNext = mActivity.findViewById(R.id.btn_goto_edit);
        mBtnNext.setOnClickListener(this);

        if (mBtnRecord != null) {
            Log.e("xie", "audio per:" +
                    (mActivity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED));
            mBtnRecord.setPerEnable(
                    mActivity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
            mBtnRecord.setRecordEnable(true);
        }
        pre.setRecordSeconds(15);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_record) {
            // do nothing
        } else if (i == R.id.btn_record_delete) {
            deleteRecordedVideo();
        } else if (i == R.id.btn_goto_edit) {
            stopRecordOrPreviewVideo();
        }
    }
    private void deleteRecordedVideo() {
        dismissDialog();
        mDialog = DialogBuilder.from(mActivity, R.layout.dialog_two_button)
                .setText(R.id.tv_dialog_title, R.string.delete_last_video_tips)
                .setText(R.id.btn_dialog_cancel, R.string.btn_dialog_cancel)
                .setDismissOnClick(R.id.btn_dialog_cancel, true)
                .setText(R.id.btn_dialog_ok, R.string.btn_delete)
                .setDismissOnClick(R.id.btn_dialog_ok, true)
                .setOnClickListener(R.id.btn_dialog_ok, v -> {
                    pre.deleteLastVideo();
                })
                .show();
    }
    private void stopRecordOrPreviewVideo() {
        pre.mergeAndEdit();
        resetDeleteButton();
    }
    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
    private void resetDeleteButton() {
        boolean hasRecordVideo = (pre.getRecordedVideoSize() > 0);
        if (mBtnNext != null) {
            mBtnNext.setVisibility(hasRecordVideo ? View.VISIBLE : View.GONE);
        }
        if (mBtnDelete != null) {
            mBtnDelete.setVisibility(hasRecordVideo ? View.VISIBLE : View.GONE);
        }
    }
    private RecordButton.RecordStateListener mRecordStateListener = new RecordButton.RecordStateListener() {
        @Override
        public void onRecordStart() {
           pre.startRecord();
        }

        @Override
        public void reqPer() {
            mActivity.checkAndRequestRecordPermission(new PlayActivity.OnPermissionSuccess() {
                @Override
                public void onOK() {
                    mBtnRecord.setPerEnable(
                            mActivity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
                }
            });
        }

        @Override
        public void onRecordStop() {
            pre.stopRecord();
        }

        @Override
        public void onZoom(float percent) {

        }
    };

    public void resetAllLayout() {
        if (mLayoutDelete != null) {
            mLayoutDelete.setVisibility(View.VISIBLE);
        }
        resetDeleteButton();
        if (mBtnRecord != null) {
            mBtnRecord.reset();
        }
    }
}
