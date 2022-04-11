package com.leebaeng.lbpushupcounter.presenter

import android.util.Log
import com.google.mlkit.vision.face.Face
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.util.log.logW

class PushUpAnalyzerByFaceDetect(private val listener: PushUpAnalyzeListener, private val screenWidth: Int, private val screenHeight: Int) : FaceAnalyzeListener {
    companion object{
        // 얼굴 사이즈 계산을 위한 필요한 횟수
        private const val CHECK_FACE_SIZE_COUNT = 40
    }
    private var faceSize = -1f
    private var checkCount = 0
    private var totalLatest: ArrayList<Float> = ArrayList()  // 최근 10개만 계산. 최근 10개의 계산수치의 각 값의 갭차이가 10% 미만일 경우에만 얼굴 인식 완료 판정
    private var averageLatest: Float = 0f // 최근 10개만 계산.
    private var isDowned = false

    var pushUpCount = 0
    set(count){
        field = count
        pushUpCountStr = pushUpCount.toString()
        listener.onPushUpCountChanged(pushUpCount)
        listener.drawDetectInfo("PushUp!! $pushUpCountStr")
    }
    var pushUpCountStr = "0"

    override fun onDetect(face: Face?) {
        if (face != null) {
            Log.d("", "analyze: ${face.boundingBox.width()}, ${face.boundingBox.height()}")
            val distance: Float = face.boundingBox.width().toFloat() * face.boundingBox.height()

            // 얼굴 사이즈 아직 미인식된 상태
            if (faceSize == -1f) {
                // 얼굴 중앙여부 판단
                if(isFaceOnCenter(face)){
                    listener.hideInfoMessage()
                    checkCount++
                    if(detectedFaceSize(distance)){
                        pushUpCount = 0
                        listener.onReadyToPushUp()
                    }
                } else {
                    listener.showInfoMessage(R.string.info_text_goCenter)
                    initFaceInfo()
                }
            } else {
                // 대기상태보다 2배 이상 가까이 오면 down으로판단
                if(distance > faceSize * 2 && !isDowned)
                    isDowned = true

                // 다운 상태에서 다시 대기 동작의 10% 이내 범위로 멀어지면 count Up
                else if(distance < faceSize * 1.1 && isDowned){
                    isDowned = false
                    pushUpCount++
                }
            }
        } else {
            if (checkCount > 0 && faceSize == -1f)
                initFaceInfo()
        }
    }

    private fun isFaceOnCenter(face: Face): Boolean {
        val isCenterW = (screenWidth / 2.4 < face.boundingBox.centerX() && face.boundingBox.centerX() < screenWidth / 1.6)
        val isCenterH = (screenHeight / 2.1 < face.boundingBox.centerY() && face.boundingBox.centerY() < screenHeight / 1.6)
        var str = "CurrentPos : ${face.boundingBox.centerX()}, ${face.boundingBox.centerY()} // $screenWidth, $screenHeight"

        str = "W:$isCenterW, H:$isCenterH // $str"
        str.logW()

        return isCenterH && isCenterW
    }

    private fun detectedFaceSize(distance: Float) : Boolean{
        /** 최근 [CHECK_FACE_SIZE_COUNT]개 데이터만 계산 */
        var tot = 0f
        if (totalLatest.size > CHECK_FACE_SIZE_COUNT) totalLatest.removeAt(0)
        totalLatest.add(distance)
        totalLatest.forEach { tot += it }

        // 각 값의 오차가 10% 내외인지 판단
        totalLatest.forEachIndexed { index, l ->
            totalLatest.forEachIndexed { index2, l2 ->
                if(index == index2) return@forEachIndexed
                if(l2 < l * 0.95 || l * 1.05 < l2){
                    listener.drawDetectInfo("avg error : ${totalLatest.size}, $totalLatest")
                    return@detectedFaceSize false
                }
            }
        }

        averageLatest = tot / totalLatest.size
        listener.drawDetectInfo("avg : $averageLatest(${totalLatest.size}, $totalLatest)")
        if (checkCount > CHECK_FACE_SIZE_COUNT) {
            faceSize = averageLatest
            return true
        }

        /** 아직 수집 데이터가 [CHECK_FACE_SIZE_COUNT]개 미만 이므로 return false */
        return false
    }

    private fun initFaceInfo() {
        faceSize = -1f
        checkCount = 0
    }
}