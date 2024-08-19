package com.example.imagesearch.utils

import android.content.Context
import android.util.Log
import com.example.imagesearch.Constants.PREFS_NAME
import com.example.imagesearch.Constants.PREF_KEY
import java.text.ParseException
import java.text.SimpleDateFormat

object Utils {

    fun getDateFromTimestampWithFormat(
        timestamp: String?,
        fromFormatformat: String?,
        toFormatformat: String?
    ): String {
        var date: java.util.Date? = null
        var res = ""
        try {
            val format = SimpleDateFormat(fromFormatformat)
            // java.sql.Date와 java.util.Date가 다름
            // 처음엔 sql로 했다가 타입 미스매치가 뜨고 as Date?로 변환시켰는데 util로 바꾸니 그럴 필요 없어짐
            date = format.parse(timestamp)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Log.d("date", "getDateFromTimestampWithFormat date >> $date")

        val df = SimpleDateFormat(toFormatformat)
        res = df.format(date)
        return res
    }

    fun saveHistory(context: Context, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_KEY, query).apply()
    }

    fun getHistory(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_KEY, null)
    }
}