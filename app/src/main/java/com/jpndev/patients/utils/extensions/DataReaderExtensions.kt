package com.jpndev.patients.utils.extensions

import android.content.res.AssetManager
import android.text.format.DateFormat
import java.io.InputStream
import java.util.*

/**
 * Reads contents of Asset File
 * @param fileName Name of asset file to read
 * @return Contents (in String) of asset file
 */
fun AssetManager.readAssetsFile(fileName: String) = open(fileName).readText()

/**
 * Reads text from input stream
 * @return Contents of InputStream
 */
private fun InputStream.readText() = bufferedReader().use { it.readText() }

/**
 * @param time
 * @return yy-MM-dd HH:mm格式时间
 */
fun Long.conversionTime( format: String = "MMM dd, yyyy h:mm a"): String {
    return DateFormat.format(format, this).toString()
}

/**
 * 根据当前日期获得是星期几
 * time=yyyy-MM-dd
 *
 * @return
 */
fun Long.getWeek(): String {
    val c = Calendar.getInstance()
    c.timeInMillis = this
    return when (c[Calendar.DAY_OF_WEEK]) {
        1 -> "Sun"
        2 -> "Mon"
        3 -> "Tue"
        4 -> "Wed"
        5 -> "Thu"
        6 -> "Fri"
        7 -> "Sat"
        else -> ""
    }
}