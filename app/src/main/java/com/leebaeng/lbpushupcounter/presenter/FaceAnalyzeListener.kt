package com.leebaeng.lbpushupcounter.presenter

import com.google.mlkit.vision.face.Face

interface FaceAnalyzeListener {
    fun onDetect(face:Face?)
}