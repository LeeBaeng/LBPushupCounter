package com.leebaeng.lbpushupcounter.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * [Long]Type Unix time(ex.[System.currentTimeMillis])을 자정으로 설정 후 Unix time([Long]) 으로 반환 한다
 */
fun Long.toMidnightDateTimeMs(dayAmount: Int = 0): Long{
    return this.toMidnightDateTime(dayAmount).time
}

/**
 * [Long]Type Unix time(ex.[System.currentTimeMillis])을 자정으로 설정한 [Date] 객체를 반환 한다.
 */
fun Long.toMidnightDateTime(dayAmount: Int = 0): Date{
    val cal: Calendar = GregorianCalendar()
    cal.time = Date(this)
    cal.add(Calendar.DATE, dayAmount)
    cal[Calendar.HOUR_OF_DAY] = 0
    cal[Calendar.MINUTE] = 0
    cal[Calendar.SECOND] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.time
}

/**
 * [Date]객체를 [format]에 맞춰 [String]으로 변환 후 반환 한다.
 * (format 참조 : https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
 *
 * @param format date format string
 */
fun Date.format(format: String? = "yyyy-MM-dd HH:mm:ss:SSS"): String? = SimpleDateFormat(format, Locale.getDefault()).format(this)

/**
 * [Long]Type Unix time(ex.[System.currentTimeMillis])을 Formatting된 String으로 변환 한다.
 */
fun Long.toFormattedDateTimeString(format: String? = "yyyy-MM-dd HH:mm:ss:SSS") = Date(this).format(format)