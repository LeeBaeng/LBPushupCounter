package com.leebaeng.lbpushupcounter.util

import android.content.Context
import com.leebaeng.lbpushupcounter.BuildConfig

object ConfigUtil {
    fun getPackageName(context: Context): String {
        return context.packageName
    }

    fun getLabel(context: Context): String {
        return context.applicationInfo.loadLabel(context.packageManager).toString()
    }

    fun getAppId(context: Context): String {
        return context.applicationInfo.loadDescription(context.packageManager).toString()
    }

    fun getAppVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    fun getAppVersionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    fun getBuildType(): String {
        return BuildConfig.BUILD_TYPE
    }

    fun isProVersion(): Boolean{
        return getBuildType().lowercase().contentEquals("pro")
    }
}