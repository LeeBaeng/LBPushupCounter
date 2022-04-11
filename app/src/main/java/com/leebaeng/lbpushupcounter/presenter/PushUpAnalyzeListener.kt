package com.leebaeng.lbpushupcounter.presenter

import androidx.annotation.StringRes

interface PushUpAnalyzeListener {
    fun showInfoMessage(@StringRes msgResId:Int)
    fun hideInfoMessage()
    fun drawDetectInfo(str: String)
    fun onReadyToPushUp()
    fun onPushUpCountChanged(count: Int)
}