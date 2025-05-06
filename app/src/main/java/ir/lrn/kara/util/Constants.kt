package ir.lrn.kara.util

import androidx.compose.ui.graphics.Color
import ir.lrn.kara.R
import ir.lrn.kara.data.model.FirstRouteData.Companion.fullFirstData
import java.util.Calendar

object Constants {

    const val DATASTORE_NAME = "DATASTORE_NAME"
    var GRIDLIST = false
    var SORT_NOTE = 0
    var SORT_TASK = 0
    var ROUTINE_SORT = 0
    var isThemDark = false

    const val ABOUT_BASE_URL = "https://daneshjooyar.com/wp-json/wp/v2/"
    const val NOTIFE_BASE_URL = "https://notificator.ir/api/v1/"
    const val CHANNEL_ID = "Task"
    const val CHANNEL_NAME = "channel_main"
    const val TOKEN = "H5tzuvIGHfes3LZxAGscCfX6qfAuG6VWqSxntJLc"
    const val ACTION_TASK_RECEIVER = "info.alirezaahmadi.ACTION_TASK_RECEIVER"
    const val ACTION_ROUTINE_RECEIVER = "info.alirezaahmadi.ACTION_ALARM_RECEIVER"


    val deyWeek = listOf(
        "شنبه",
        "یک شنبه",
        "دو شنبه",
        "سه شنبه",
        "چهارشنبه",
        "پنجشنبه",
        "جمعه",
    )
    val daysMap = mapOf(
        "شنبه" to Calendar.SATURDAY,
        "یک شنبه" to Calendar.SUNDAY,
        "دو شنبه" to Calendar.MONDAY,
        "سه شنبه" to Calendar.TUESDAY,
        "چهارشنبه" to Calendar.WEDNESDAY,
        "پنجشنبه" to Calendar.THURSDAY,
        "جمعه" to Calendar.FRIDAY
    )
    val persianDayOfWeek = mapOf(
        Calendar.SATURDAY to 0, // شنبه
        Calendar.SUNDAY to 1,   // یکشنبه
        Calendar.MONDAY to 2,   // دوشنبه
        Calendar.TUESDAY to 3,  // سه‌شنبه
        Calendar.WEDNESDAY to 4, // چهارشنبه
        Calendar.THURSDAY to 5, // پنج‌شنبه
        Calendar.FRIDAY to 6    // جمعه
    )
    val SkinsImage = listOf(
        R.drawable.massage_face,
        R.drawable.toilet_liquid,
        R.drawable.shiny_face,
        R.drawable.makeup2,
        R.drawable.wash_face,
    )
    val skinColors = listOf(
        Color(0xffCAF0F8),
        Color(0xffFFBDC3),
        Color(0xffD6B7FF),
        Color(0xffF9DBBD),
        Color(0xffC0FFB2),
    )
    var firstDataSet : Set<Int> = fullFirstData.map { it.id }.toSet()

    var showPager =true
}