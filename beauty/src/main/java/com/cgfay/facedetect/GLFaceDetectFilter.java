package com.cgfay.facedetect;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLU;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;

import com.cgfay.camera.render.CameraRenderer;
import com.cgfay.filter.glfilter.base.GLImageFilter;
import com.cgfay.filter.glfilter.utils.OpenGLUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLFaceDetectFilter extends GLImageFilter {

    public GLFaceDetectFilter(Context context) {
        super(context);
        initFrameBuffer(1080, 2400);
    }

    public void onDrawFrameBegin1(int textureId) {
        // load data from textureId
        int width = 1080;
        int height = 2400;
        int[] framebuffer = new int[1];
        GLES30.glGenFramebuffers(1, framebuffer, 0);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, framebuffer[0]);

        int[] renderbuffer = new int[1];
        GLES30.glGenRenderbuffers(1, renderbuffer, 0);
        GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER, renderbuffer[0]);
        GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER, GLES30.GL_DEPTH_COMPONENT16, width, height);
        GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_RENDERBUFFER, renderbuffer[0]);

        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, textureId, 0);

        ByteBuffer buffer = ByteBuffer.allocate(width * height * 4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer);

//        Log.e("xie", "bitmap position:" + buffer.position()); // 0
//        Log.e("xie", "bitmap remaining:" + buffer.remaining()); // 1228800
        if (CameraRenderer.mFaceDetectMan != null) {
            CameraRenderer.mFaceDetectMan.detect(buffer);
        }
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
        GLES30.glDeleteRenderbuffers(1, renderbuffer, 0);
        GLES30.glDeleteFramebuffers(1, framebuffer, 0);
    }

}
