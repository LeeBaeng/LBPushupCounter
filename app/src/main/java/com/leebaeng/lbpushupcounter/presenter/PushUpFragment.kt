package com.leebaeng.lbpushupcounter.presenter

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.annotation.StringRes
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.leebaeng.lbpushupcounter.FaceAnalyzer
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.lbpushupcounter.databinding.FragmentPushupBinding
import com.leebaeng.lbpushupcounter.util.windowHeight
import com.leebaeng.lbpushupcounter.util.windowWidth
import java.util.*
import kotlin.concurrent.timerTask


class PushUpFragment : BaseFragment(R.layout.fragment_pushup), PushUpAnalyzeListener {
    private lateinit var binding: FragmentPushupBinding

    lateinit var pushUpByFaceAnalyzer: PushUpAnalyzerByFaceDetect
    private lateinit var faceAnalyzer: FaceAnalyzer

    private var timerForManually : Timer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pushUpByFaceAnalyzer = PushUpAnalyzerByFaceDetect(this, windowWidth, windowHeight)
        binding = FragmentPushupBinding.bind(view)
        binding.fragment = this

        checkPermission(view.context)

        val cameraProvider = ProcessCameraProvider.getInstance(requireContext())
        val previewView = view.findViewById<PreviewView>(R.id.previewView)
        val executor = ContextCompat.getMainExecutor(requireContext())

        cameraProvider.addListener({
            faceAnalyzer = FaceAnalyzer(pushUpByFaceAnalyzer)
            val provider = cameraProvider.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(previewView.width, previewView.height))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setImageQueueDepth(10)
                .build()
                .apply {
                    setAnalyzer(executor, faceAnalyzer)
                }
            provider.unbindAll()
            provider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis
            )
        }, executor)
    }

    private val PERMISSIONSREQUEST = 0x0000001
    fun checkPermission(context: Context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                showToast("앱 실행을 위해서는 권한을 설정해야 합니다")
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSIONSREQUEST)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSIONSREQUEST)
            }
        }
    }

    fun onClick(view: View){
        when(view){
            binding.txtInfoText -> {
                // TODO : 운동 종료(기록 화면으로 이동)
            }
            binding.layoutAddCountManually -> {
                if(timerForManually == null){
                    timerForManually = Timer(false)
                    pushUpByFaceAnalyzer.pushUpCount++
                    timerForManually!!.schedule(timerTask {
                        this.cancel()
                        timerForManually = null
                    }, 1000)
                }
            }
        }
    }



    override fun showInfoMessage(@StringRes msgResId: Int) {
        if (!binding.layoutInfoText.isVisible) binding.layoutInfoText.isVisible = true
        if (binding.txtInfoText.text != resources.getString(msgResId)) binding.txtInfoText.text = resources.getString(msgResId)
    }

    override fun hideInfoMessage() {
        if (binding.layoutInfoText.isVisible) binding.layoutInfoText.isVisible = false
    }

    override fun drawDetectInfo(str: String) {
        binding.txtFaceDetectInfo.text = str
    }

    override fun onReadyToPushUp() {
        showInfoMessage(R.string.info_text_readyComplete)
        pushUpByFaceAnalyzer.pushUpCount = 0
    }

    override fun onPushUpCountChanged(count: Int) {
        if(count >= 10000) showInfoMessage(R.string.info_text_invalidCount)
        else if(count != 0){
            if(!binding.layoutPushCount.isVisible) binding.layoutPushCount.isVisible = true
            if(binding.layoutInfoText.isVisible) binding.layoutInfoText.isVisible = false
        }
        binding.txtPushUpCount.text = count.toString()
    }

    override fun onStop() {
        super.onStop()
        if (::faceAnalyzer.isInitialized)
            faceAnalyzer.stop()
    }
}

