package com.leebaeng.lbpushupcounter

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.leebaeng.lbpushupcounter.data.db.DataBase
import com.leebaeng.lbpushupcounter.data.db.getDatabase
import com.leebaeng.util.log.LLog

class MainActivity : AppCompatActivity() {
    lateinit var db : DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = getDatabase(this)
        LLog.init(this)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        MobileAds.initialize(this) { }
    }
}