package com.cgfay.facedetect;

import android.util.Log;

import com.duowan.vnnlib.VNN;

public class FaceResultIns {
    public VNN.VNN_FaceFrameDataArr faceDetectionFrameData;
    static FaceResultIns faceResultIns;
    public float[] faceLandmarks = new float[556];

    public static FaceResultIns getInstance() {

        if (faceResultIns != null) {
            return faceResultIns;
        } else {
            synchronized (FaceResultIns.class) {
                faceResultIns = new FaceResultIns();
            }
        }
        return faceResultIns;
    }

    public void setData(VNN.VNN_FaceFrameDataArr data) {
        VNN.VNN_FaceFrameDataArr faceDetectionFrameData = data;
        if (faceDetectionFrameData != null && faceDetectionFrameData.facesNum > 0) {
//                SparseArray<OneFace> faceArrays = LandmarkEngine.getInstance().getFaceArrays();
//            for (int i = 0; i < faceDetectionFrameData.facesNum; i++) {
            if (faceDetectionFrameData.facesArr[0].faceLandmarksNum > 0) {
//                System.arraycopy(faceDetectionFrameData.facesArr[0].faceLandmarks, 0,
//                        faceLandmarks, 0, faceLandmarks.length);
                // 坐标变换 0.1 -> 1- 0.1 | 0.9 -> 0.9 * 2 - 1
                // Canvas - OpenGl
                // 从相机获取数据
//                for (int j = 0; j < faceDetectionFrameData.facesArr[0].faceLandmarksNum; j++) {
//                    faceLandmarks[j * 2] = (1 - (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2])) * 2 - 1;
//                    faceLandmarks[j * 2 + 1] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2 + 1] * 2 - 1);
//                }
                // 从 texture获取数据
                for (int j = 0; j < faceDetectionFrameData.facesArr[0].faceLandmarksNum; j++) {
                    faceLandmarks[j * 2] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2]) * 2 - 1;
                    faceLandmarks[j * 2 + 1] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2 + 1] * 2 - 1);
                }
            }

//            }
        }
    }
}
