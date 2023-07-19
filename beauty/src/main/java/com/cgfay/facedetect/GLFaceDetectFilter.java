package com.cgfay.facedetect;

import android.content.Context;
import android.opengl.GLES30;

import com.cgfay.filter.glfilter.base.GLImageFilter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GLFaceDetectFilter extends GLImageFilter {

    private final FaceDetectMan mFaceDetectMan;

    public GLFaceDetectFilter(Context context) {
        super(context);
        initFrameBuffer(1080, 2400);
        mFaceDetectMan = new FaceDetectMan(context);
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
        mFaceDetectMan.detect(buffer);

        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
        GLES30.glDeleteRenderbuffers(1, renderbuffer, 0);
        GLES30.glDeleteFramebuffers(1, framebuffer, 0);
    }

}
