package com.cgfay.facedetect;

import com.cgfay.landmark.LandmarkEngine;
import com.cgfay.landmark.OneFace;
import com.duowan.vnnlib.VNN;

public class FaceResultIns {
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
                for (int j = 0; j < faceDetectionFrameData.facesArr[0].faceLandmarksNum; j++) {
                    faceLandmarks[j * 2] = (1 - (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2])) * 2 - 1;
                    faceLandmarks[j * 2 + 1] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2 + 1] * 2 - 1);
                }
                // 高度百分比
                // 从 texture获取数据
//                for (int j = 0; j < faceDetectionFrameData.facesArr[0].faceLandmarksNum; j++) {
//                    faceLandmarks[j * 2] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2]) * 2 - 1;
//                    faceLandmarks[j * 2 + 1] = (faceDetectionFrameData.facesArr[0].faceLandmarks[j * 2 + 1] * 2 - 1) * 1.2f;
//                }
                // 索引转换
                OneFace oneFace = new OneFace();
                oneFace.vertexPoints = faceLandmarks;
                convertFacePoint(oneFace);
                // 暂时只用一个人脸
                LandmarkEngine.getInstance().putOneFace(0, oneFace);
            }
//            }
        }
    }

    private void convertFacePoint(OneFace oneFace) {
        float[] point = oneFace.vertexPoints;
        float[] after = new float[114 * 2];
        for (int i = 0; i < 38; i++) {
            after[i * 2] = point[i * 2];
            after[i * 2 + 1] = point[i * 2 + 1];
        }

        convert(after, point, 38, 42);
        convert(after, point, 39, 43);
        convert(after, point, 40, 44);
        convert(after, point, 41, 45);
        convert(after, point, 42, 46);

        convert(after, point, 43, 51);
        convert(after, point, 44, 52);
        convert(after, point, 45, 53);
        convert(after, point, 46, 54);

        convert(after, point, 47, 58);
        convert(after, point, 48, 59);
        convert(after, point, 49, 60);
        convert(after, point, 50, 61);
        convert(after, point, 51, 62);

        convert(after, point, 52, 66);
        convert(after, point, 53, 67);
        convert(after, point, 54, 69);
        convert(after, point, 55, 70);
        convert(after, point, 56, 71);
        convert(after, point, 57, 73);

        convert(after, point, 58, 74);
        convert(after, point, 59, 75);
        convert(after, point, 60, 77);
        convert(after, point, 61, 78);
        convert(after, point, 62, 79);
        convert(after, point, 63, 81);

        convert(after, point, 64, 41);
        convert(after, point, 65, 40);
        convert(after, point, 66, 39);
        convert(after, point, 67, 38);
        convert(after, point, 68, 50);
        convert(after, point, 69, 49);
        convert(after, point, 70, 48);
        convert(after, point, 71, 47);

        convert(after, point, 72, 68);
        convert(after, point, 73, 72);
        convert(after, point, 74, 102);
        convert(after, point, 75, 76);
        convert(after, point, 76, 80);
        convert(after, point, 77, 103);

        convert(after, point, 78, 55);
        convert(after, point, 79, 65);

        convert(after, point, 80, 56);
        convert(after, point, 81, 64);
        convert(after, point, 82, 57);
        convert(after, point, 83, 63);

        convert(after, point, 84, 82);
        convert(after, point, 85, 83);
        convert(after, point, 86, 84);
        convert(after, point, 87, 85);
        convert(after, point, 88, 86);
        convert(after, point, 89, 87);
        convert(after, point, 90, 88);
        convert(after, point, 91, 89);
        convert(after, point, 92, 90);
        convert(after, point, 93, 91);
        convert(after, point, 94, 92);
        convert(after, point, 95, 93);

        convert(after, point, 96, 94);
        convert(after, point, 97, 95);
        convert(after, point, 98, 96);
        convert(after, point, 99, 97);
        convert(after, point, 100, 98);
        convert(after, point, 101, 99);
        convert(after, point, 102, 100);
        convert(after, point, 103, 101);

        convert(after, point, 104, 102);
        convert(after, point, 105, 103);

        oneFace.vertexPoints = after;
    }

    private void convert(float[] after, float[] point, int i, int i1) {
        after[i * 2] = point[i1 * 2];
        after[i * 2 + 1] = point[i1 * 2 + 1];
    }
}
