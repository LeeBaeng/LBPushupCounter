package com.leebaeng.lbpushupcounter

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.leebaeng.lbpushupcounter.presenter.FaceAnalyzeListener

class FaceAnalyzer(val listener:FaceAnalyzeListener): ImageAnalysis.Analyzer {
    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
        .setMinFaceSize(0.20f)
        .enableTracking()
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            detector.process(inputImage)
                .addOnSuccessListener { faces ->
                    var mainFace : Face? = null

                    // 여러개 얼굴이 인식 되었을때 가장 큰 얼굴을 운동자로 인식하기 위한 처리
                    faces.forEach { face ->
                        if(mainFace == null) mainFace = face
                        else if((mainFace!!.boundingBox.width() * mainFace!!.boundingBox.height()) < (face.boundingBox.width() * face.boundingBox.height())) mainFace = face
                    }

                    listener.onDetect(mainFace)
                    imageProxy.close()
                }
                .addOnFailureListener {
                    imageProxy.close()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    fun stop(){
        detector.close()
    }
}




















