package com.spark.presentation.utils.components.bottomSheetList.base


import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects

object TimeHelper {

    private const val DATE_FORMAT_CLIENT = "dd-MM-yyy"
    private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    private const val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd hh:mm:ss a"
    private const val ONE_DAY_DAYS_SPAN = 86400
    private const val ONE_MONTH_DAYS_SPAN = 2629746

    val today: String
        get() {
            val outSDF = SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault())
            return outSDF.format(Date())
        }

    fun getYear(strDate: String): Int {
        return Integer.parseInt(strDate.replace("-", "").substring(4, 8))
    }

    fun getMonth(strDate: String): Int {
        return Integer.parseInt(strDate.replace("-", "").substring(2, 4)) - 1
    }

    fun getDay(strDate: String): Int {
        return Integer.parseInt(strDate.replace("-", "").substring(0, 2))
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

    fun isMoreThanOneMonth(strDate: String): Boolean {
        if (isNotValidDate(strDate))
            return false
        var date = Date()
        val dateFormat = SimpleDateFormat(DATE_FORMAT_CLIENT, Locale.getDefault())
        dateFormat.isLenient = false
        dateFormat.parse(strDate.trim()).let {
            if (it != null) {
                date = it
            }
        }

        val selectedMilli = Objects.requireNonNull(date).time
        val today = Date()
        val currentMilli = today.time
        val diff = selectedMilli - currentMilli
        return diff / 1000 >= ONE_MONTH_DAYS_SPAN
    }

    fun isMoreThanOneDay(strDate: String): Boolean {
        var date = Date()
        val dateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, Locale.getDefault())
        dateFormat.isLenient = false
        dateFormat.parse(strDate.trim()).let {
            if (it != null) {
                date = it
            }
        }

        val selectedMilli = Objects.requireNonNull(date).time
        val today = Date()
        val currentMilli = today.time
        val diff = selectedMilli - currentMilli
        return diff / 1000 >= ONE_DAY_DAYS_SPAN
    }

    fun NicDateValidation(idNumber: String, birthDay: String): Boolean {
        val birthDate = convertDateToServer(birthDay).replace("-", "").substring(2, 6)
        val number = idNumber.substring(0, 4)
        return birthDate != number
    }

    fun ageValidation(strDate: String): Boolean {
        if (isNotValidDate(strDate))
            return true

        var result = false
        var date = Date()
        val dateFormat = SimpleDateFormat(DATE_FORMAT_CLIENT, Locale.getDefault())
        dateFormat.isLenient = false
        dateFormat.parse(strDate.trim()).let {
            if (it != null) {
                date=it
            }
        }

        val selectedMilli = Objects.requireNonNull(date).time
        val dateOfBirth = Date(selectedMilli)
        val dob = Calendar.getInstance()
        dob.time = dateOfBirth
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.MONTH) > dob.get(Calendar.MONTH)) {
            --age
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) > dob
                .get(Calendar.DAY_OF_MONTH)
        ) {
            if (today.get(Calendar.DAY_OF_MONTH) > dob.get(Calendar.DAY_OF_MONTH))
                --age
        }
        if (age in 18..100)
            result = true
        return !result
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

    fun convertDateToClient(inDate: String): String {
        try {
            val inSDF = SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault())
            val outSDF = SimpleDateFormat(DATE_FORMAT_CLIENT, Locale.getDefault())
            val date = inSDF.parse(inDate)
            return outSDF.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return inDate
    }
}