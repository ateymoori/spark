package com.spark.presentation.utils.components.bottomSheetList.base


import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects

object TimeHelper {

    private const val DATE_FORMAT_CLIENT = "dd-MM-yyy"
    private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"

    fun getYear(strDate: String): Int {
        return try {
            Integer.parseInt(strDate.replace("-", "").substring(4, 8))
        } catch (e: Exception) {
            0
        }
    }

    fun getMonth(strDate: String): Int {
        return try {
            Integer.parseInt(strDate.replace("-", "").substring(2, 4)) - 1
        } catch (e: Exception) {
            0
        }
    }

    fun getDay(strDate: String): Int {
        return try {
            Integer.parseInt(strDate.replace("-", "").substring(0, 2))
        } catch (e: Exception) {
            0
        }
    }

    private fun isNotValidDate(inDate: String): Boolean {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_CLIENT, Locale.getDefault())
        dateFormat.isLenient = false
        try {
            dateFormat.parse(inDate.trim())
        } catch (pe: ParseException) {
            return true
        }

        return false
    }


    fun ageValidation(strDate: String, min_old: Int = 12, max_old: Int = 120): Boolean {

        if (isNotValidDate(strDate))
            return false

        val inputYear = getYear(strDate)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val distance = currentYear - inputYear

        if (distance in min_old..max_old) {
            return true
        }

        return false
    }

    fun convertDateToServer(inDate: String): String {
        try {
            val inSDF = SimpleDateFormat(DATE_FORMAT_CLIENT, Locale.getDefault())
            val outSDF = SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault())
            val date = inSDF.parse(inDate)
            return outSDF.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return inDate
    }


}