package com.leebaeng.lbpushupcounter.presenter.view

import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.leebaeng.lbpushupcounter.R
import java.text.SimpleDateFormat
import java.util.*


class GraphMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    // draw override를 사용해 marker의 위치 조정 (bar의 상단 중앙)
    override fun draw(canvas: Canvas?) {
        canvas!!.translate(-(width / 2).toFloat(), -height.toFloat())
        super.draw(canvas)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val formatter = SimpleDateFormat("MM/dd", Locale.getDefault())
        if( e != null ){
            val date = formatter.format(e.x * 1000)
            txtMarker.text = "$date : ${e.y.toInt()}${context.resources.getString(R.string.history_view_graph_select_count_text)}"
        }else{
            txtMarker.text = context.resources.getString(R.string.history_view_graph_select_no_record)
        }
        super.refreshContent(e, highlight)
    }

    private var txtMarker: TextView = findViewById(R.id.test_marker_view)
}