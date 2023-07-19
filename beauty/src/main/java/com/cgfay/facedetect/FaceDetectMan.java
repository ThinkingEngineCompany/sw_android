package com.cgfay.facedetect;

import static android.opengl.GLES20.GL_COLOR_ATTACHMENT0;
import static android.opengl.GLES20.GL_FRAMEBUFFER;
import static android.opengl.GLES20.GL_RGBA;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES30.GL_RGBA8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLU;
import android.util.Log;

import androidx.annotation.ColorInt;

import com.duowan.vnnlib.VNN;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL11;

public class FaceDetectMan {
    public int mVnnID = VNNHelper.VNN_EFFECT_MODE.VNN_FACE_KEYPOINTS;
    public VNN.VNN_FaceFrameDataArr faceDetectionFrameData = new VNN.VNN_FaceFrameDataArr();
    public static Context scontext;
    // 重复创建界面会卡住
    public FaceDetectMan(Context context) {
        scontext = context;
        String exPath = context.getExternalFilesDir(null).getAbsolutePath();
        copyAssetsToFiles(context, "vnn_models", exPath + "/vnn_models");
        String sdcard = context.getExternalFilesDir(null).getAbsolutePath() + "/vnn_models";
        String[] modelPath = {
                sdcard + "/vnn_face278_data/face_mobile[1.0.0].vnnmodel"
        };
        Log.e("xie2", "modelPath:" + modelPath[0]);
        //VNN.javaTest();
        mVnnID = VNN.createFace(modelPath); // 1
//        Log.e("xie1", "mVnnID:" + mVnnID);
    }

//    public void detect(ByteBuffer pixels) {
    public void detect(Bitmap mBitmap) {
//        Log.e("xie", "detect..:" + mInputTexture);
//        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mInputTexture);
//        int error;
//        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
//            Log.e("xie", "GL OpenGL error 1: " + GLU.gluErrorString(error));
//        }
//        Log.e("xie", "GL error:" + error);
//
//        int[] currentTexture = new int[1];
//        GLES30.glGetIntegerv(GLES30.GL_TEXTURE_BINDING_2D, currentTexture, 0);
//
//        Log.e("xie", "GL mInputTexture:" + mInputTexture);
//        Log.e("xie", "GL currentTexture[0]:" + currentTexture[0]);
//        if (currentTexture[0] != mInputTexture) {
//            Log.e("xie", "Texture is not bound:");
//        }
//
//        int[] framebufferIds = new int[1];
//        GLES30.glGenFramebuffers(1, framebufferIds, 0);
//        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, framebufferIds[0]);
//        // 绑定纹理到帧缓冲区
//        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0,
//                GLES30.GL_TEXTURE_2D, mInputTexture, 0);
//        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
//            Log.e("xie", "GL Framebuffer error 1: " + GLU.gluErrorString(error));
//        }
//
//        ByteBuffer pixels = ByteBuffer.allocateDirect(480 * 640 * 4);
//        pixels.order(ByteOrder.nativeOrder());
//        pixels.position(0);
//        GLES30.glReadPixels(0, 0, 480, 640, GLES30.GL_RGBA, GL_UNSIGNED_BYTE, pixels);
//        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
//            Log.e("xie", "OpenGL error 2: " + GLU.gluErrorString(error));
//        }

//        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0,
//                GLES30.GL_TEXTURE_2D, 0, 0);
//        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);

//        int status = GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER);
//        if (status != GLES30.GL_FRAMEBUFFER_COMPLETE) {
//            Log.e("xie", "Framebuffer is not complete: " + status); // 36055
//        } else {
//            Log.e("xie", "Framebuffer is complete: " + status);
//        }
//        Log.e("xie", "buffer.position:" + pixels.position()); // 0
//        Log.e("xie", "buffer.remaining 1:" + pixels.remaining()); // 1228800
//
//        byte[] bytes = new byte[pixels.remaining()]; // 创建一个与 buffer 中数据大小相同的 byte 数组
//        pixels.rewind(); // 将 buffer 的位置重置为 0
//        pixels.get(bytes); // 将 buffer 中的数据传输到 byte 数组中
//        Log.e("xie", "bytes.length:" + bytes.length);
//        Log.e("xie", "bytes:" + bytes[0] + " - " + bytes[1] + " - " + bytes[2] + " - " + bytes[3]);
//        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
//            Log.e("xie","OpenGL error3: " + GLU.gluErrorString(error));
//        }

//        Log.e("xie", "bytes:" + bytes.length);
//        int index = 0;
//        while (bytes[index] == 0 && index < bytes.length - 4) {
//            index++;
//        }
//        Log.e("xie", "content:" + index + "-" + bytes[index + 0] + "-" + bytes[index + 1]
//                + "-" + bytes[index + 2] + "-" + bytes[index + 3]);

//        VNN.VNN_Image inputImage = new VNN.VNN_Image();
//        inputImage.width = 480;
//        inputImage.height = 640;
//        inputImage.data = bytes;
//        inputImage.ori_fmt = VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_FLIP_V
//                | VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_ROTATE_90R; // ---
////        inputImage.pix_fmt = VNN.VNN_PixelFormat.VNN_PIX_FMT_YUV420P_888_SKIP1;
//        inputImage.pix_fmt = VNN.VNN_PixelFormat.VNN_PIX_FMT_RGBA8888;
//        inputImage.mode_fmt = VNN.VNN_MODE_FMT.VNN_MODE_FMT_VIDEO;


        int mImageWidth = mBitmap.getWidth();
        int mImageHeight = mBitmap.getHeight();
        byte[] mImageData = getRGBAFromBitmap(mBitmap);
        VNN.VNN_Image inputImage = new VNN.VNN_Image();
        inputImage.width = mImageWidth;
        inputImage.height = mImageHeight;
        inputImage.data = mImageData;
        inputImage.ori_fmt = VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_ROTATE_180; // ---
//        inputImage.ori_fmt = VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_DEFAULT; // ---
        inputImage.pix_fmt = VNN.VNN_PixelFormat.VNN_PIX_FMT_RGBA8888;
        inputImage.mode_fmt = VNN.VNN_MODE_FMT.VNN_MODE_FMT_VIDEO;

        detectInner(0, 0, inputImage);
    }

    public void detect(byte[] data, int width, int height) {

        VNN.VNN_Image inputImage = new VNN.VNN_Image();
        inputImage.width = width; // not use
        inputImage.height = height;// not use
        inputImage.data = data;
        inputImage.ori_fmt = VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_FLIP_V
                | VNN.VNN_OrientationFormat.VNN_ORIENT_FMT_ROTATE_90R; // ---
//        inputImage.pix_fmt = VNN.VNN_PixelFormat.VNN_PIX_FMT_YUV420P_888_SKIP1;
        inputImage.pix_fmt = VNN.VNN_PixelFormat.VNN_PIX_FMT_NV21;
        inputImage.mode_fmt = VNN.VNN_MODE_FMT.VNN_MODE_FMT_VIDEO;

        detectInner(width, height, inputImage);
    }

    private void detectInner(int width, int height, VNN.VNN_Image inputImage) {
        faceDetectionFrameData.facesNum = 0;
        VNN.setFacePoints(mVnnID, 278);
        int ret = VNN.applyFaceCpu(mVnnID, inputImage, faceDetectionFrameData);
        if (inputImage.mode_fmt == VNN.VNN_MODE_FMT.VNN_MODE_FMT_VIDEO) {
            VNN.processFaceResultRotate(mVnnID, faceDetectionFrameData, 180);
        }
        Log.e("xie", "detect ret:" + ret + " - num:" + faceDetectionFrameData.facesNum);
        // drawFaceKeyPoints(canvas);
//        for (int i = 0; i < faceDetectionFrameData.facesNum; i++) {
//            Rect faceRect = new Rect();
//            faceRect.left = (int) (faceDetectionFrameData.facesArr[i].faceRect[0] * width);
//            faceRect.top = (int) (faceDetectionFrameData.facesArr[i].faceRect[1] * height);
//            faceRect.right = (int) (faceDetectionFrameData.facesArr[i].faceRect[2] * width);
//            faceRect.bottom = (int) (faceDetectionFrameData.facesArr[i].faceRect[3] * height);
//            Log.e("xie", "faceDetectionFrameData.facesArr[i].faceLandmarksNum:" +
//                    faceDetectionFrameData.facesArr[i].faceLandmarksNum); // faceLandmarksNum:278
//            Log.e("xie", "faceDetectionFrameData.facesArr[i].faceLandmarks.length:" +
//                    faceDetectionFrameData.facesArr[i].faceLandmarks.length); // faceLandmarksNum:556
//            for (int j = 0; j < faceDetectionFrameData.facesArr[i].faceLandmarksNum; j++) {
//                float pointx = (faceDetectionFrameData.facesArr[i].faceLandmarks[j * 2] * width);
//                float pointy = (faceDetectionFrameData.facesArr[i].faceLandmarks[j * 2 + 1] * height);
//                // canvas.drawCircle(pointx, pointy, strokeWidth, mPaint);
//            }
//            Log.e("xie", "faceLandmarksNum:" + faceDetectionFrameData.facesArr[i].faceLandmarksNum);
//        }
//        FaceResultIns.getInstance().faceDetectionFrameData = faceDetectionFrameData;
        FaceResultIns.getInstance().setData(faceDetectionFrameData);
    }

    public void destroyVNN(int effectMode) {
        VNN.destroyFace(mVnnID);
    }

    public void copyAssetsToFiles(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                if (!file.exists()) {
                    file.mkdirs();//如果文件夹不存在，则递归
                }
                for (String fileName : fileNames) {
                    String srcPath = oldPath + "/" + fileName;
                    String dstPath = newPath + "/" + fileName;
                    File f = new File(dstPath);
                    if (f.exists()) continue;
                    copyAssetsToFiles(context, srcPath, dstPath);
                }

            } else {//如果是文件
                File file = new File(newPath);
                if (!file.exists()) {
                    InputStream is = context.getAssets().open(oldPath);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int byteCount = 0;
                    while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                        fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                    }
                    fos.flush();//刷新缓冲区
                    is.close();
                    fos.close();
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public static byte[] getRGBAFromBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int componentsPerPixel = 4;
        int totalPixels = width * height;
        int totalBytes = totalPixels * componentsPerPixel;

        byte[] rgbValues = new byte[totalBytes];
        @ColorInt int[] argbPixels = new int[totalPixels];
        bitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < totalPixels; i++) {
            @ColorInt int argbPixel = argbPixels[i];
            int red = Color.red(argbPixel);
            int green = Color.green(argbPixel);
            int blue = Color.blue(argbPixel);
            int alpha = Color.alpha(argbPixel);
            rgbValues[i * componentsPerPixel + 0] = (byte) red;
            rgbValues[i * componentsPerPixel + 1] = (byte) green;
            rgbValues[i * componentsPerPixel + 2] = (byte) blue;
            rgbValues[i * componentsPerPixel + 3] = (byte) alpha;
        }

        return rgbValues;
    }
}
