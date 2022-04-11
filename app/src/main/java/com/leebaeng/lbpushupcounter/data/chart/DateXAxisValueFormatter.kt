package com.leebaeng.lbpushupcounter.data.chart

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.leebaeng.util.log.logW
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateXAxisValueFormatter : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String? {

        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        val formatter = SimpleDateFormat("MM/dd", Locale.getDefault())
        return formatter.format(emissionsMilliSince1970Time)
    }
}