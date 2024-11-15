package info.alirezaahmadi.taskmanager.util

import java.text.DecimalFormat
import java.util.Calendar

object TaskHelper {

    fun taskByLocate(englishStr: String): String {
        var result = ""
        var fa = '۰'
        for (ch in englishStr) {
            fa = ch
            when (ch) {
                '0' -> fa = '۰'
                '1' -> fa = '۱'
                '2' -> fa = '۲'
                '3' -> fa = '۳'
                '4' -> fa = '۴'
                '5' -> fa = '۵'
                '6' -> fa = '۶'
                '7' -> fa = '۷'
                '8' -> fa = '۸'
                '9' -> fa = '۹'
            }
            result = "${result}$fa"
        }
        return result
    }

    fun taskByLocateAndSeparator(price: String): String {
        val priceWithoutCommas = price.replace(",", "")
        // اول عدد را به صورت اعداد انگلیسی جداسازی کن
        val formattedPrice = taskBySeparator(priceWithoutCommas)
        // سپس اعداد جدا شده را به فارسی تبدیل کن
        return taskByLocate(formattedPrice)
    }

    fun taskBySeparator(price: String): String {
        val priceFormat = DecimalFormat("###,###")
        return priceFormat.format(Integer.valueOf(price))
    }

    fun getTimeInMillis(
        calendar: Calendar,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int
    ): Long {
        // ایجاد یک شیء Calendar

        // تنظیم سال، ماه، روز
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1) // ماه‌ها از 0 شروع می‌شوند
        calendar.set(Calendar.DAY_OF_MONTH, day)

        // تنظیم ساعت، دقیقه، ثانیه
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // تنظیم میلی‌ثانیه صفر برای دقت بیشتر
        calendar.set(Calendar.MILLISECOND, 0)

        // بازگرداندن زمان به صورت میلی‌ثانیه
        return calendar.timeInMillis
    }

    fun splitWholeDate(dateString: String): List<Int> {

        if (dateString == "") {
            return listOf(0, 0, 0)
        }

        val parts = dateString.split("-")// 2024-01-8
        val year = parts[0].toInt()
        val month = parts[1].toInt()
        val day = parts[2].toInt()

        return listOf(year, month, day)
    }

    fun jalaliToGregorian(jy: Int, jm: Int, jd: Int): String {

        if (jy == 0 || jm == 0 || jd == 0) {
            return ""
        }

        val jy1: Int = jy + 1595
        var days: Int =
            -355668 + (365 * jy1) + ((jy1 / 33) * 8) + (((jy1 % 33) + 3) / 4) + jd + (if (jm < 7) ((jm - 1) * 31) else (((jm - 7) * 30) + 186))
        var gy: Int = 400 * (days / 146097)
        days %= 146097
        if (days > 36524) {
            gy += 100 * (--days / 36524)
            days %= 36524
            if (days >= 365) days++
        }
        gy += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            gy += ((days - 1) / 365)
            days = (days - 1) % 365
        }
        var gd: Int = days + 1
        val sal_a: IntArray = intArrayOf(
            0,
            31,
            if ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0)) 29 else 28,
            31,
            30,
            31,
            30,
            31,
            31,
            30,
            31,
            30,
            31
        )
        var gm: Int = 0
        while (gm < 13 && gd > sal_a[gm]) gd -= sal_a[gm++]
        return "$gy-$gm-$gd"
    }

    fun gregorianToJalali(gy: Int, gm: Int, gd: Int): String {

        if (gy == 0 || gm == 0 || gd == 0) {
            return "انتخاب نشده"
        }

        val gDaysInMonth: IntArray =
            intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2: Int = if (gm > 2) (gy + 1) else gy
        var gTotalDays: Int =
            355666 + (365 * gy) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + gd + gDaysInMonth[gm - 1]
        var jy: Int = -1595 + (33 * (gTotalDays / 12053))
        gTotalDays %= 12053
        jy += 4 * (gTotalDays / 1461)
        gTotalDays %= 1461
        if (gTotalDays > 365) {
            jy += ((gTotalDays - 1) / 365)
            gTotalDays = (gTotalDays - 1) % 365
        }
        val jm: Int
        val jd: Int
        if (gTotalDays < 186) {
            jm = 1 + (gTotalDays / 31)
            jd = 1 + (gTotalDays % 31)
        } else {
            jm = 7 + ((gTotalDays - 186) / 30)
            jd = 1 + ((gTotalDays - 186) % 30)
        }
        return "$jy-$jm-$jd"
    }

    // تابعی برای تبدیل میلی‌ثانیه به تاریخ میلادی به صورت لیست [سال، ماه، روز]
    fun convertMillisToDateList(millis: Long): List<Int> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // افزودن 1 چون ماه‌ها از 0 شروع می‌شوند
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return listOf(year, month, day)
    }

    // تابعی برای تبدیل میلی‌ثانیه به زمان به صورت لیست [ساعت، دقیقه، ثانیه]
    fun convertMillisToTimeList(millis: Long): List<Int> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
        }

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return listOf(hour, minute)
    }

    //ساعت و دقیقه به تریگر
    fun getTriggerTimeInMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // اگر زمان تنظیم‌شده از زمان فعلی گذشته باشد، تنظیم آلارم برای روز بعد
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }



}

