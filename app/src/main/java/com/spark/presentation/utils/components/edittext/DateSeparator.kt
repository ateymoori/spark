package com.spark.presentation.utils.components.edittext

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.spark.presentation.utils.ext.add

import java.util.Calendar
import java.util.Locale



class DateSeparator(private val editText: AppCompatEditText) : TextWatcher {
    private var current = ""
    private val calendar = Calendar.getInstance()

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        try {
            if (s.toString() != current) {
                editText.removeTextChangedListener(this)
                var clean = s.toString().replace("[^\\d.]|\\.".toRegex(), "")
                val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")

                val cl = clean.length
                var sel = cl
                var i = 2
                while (i <= cl && i < 6) {
                    sel++
                    i += 2
                }
                //Fix for pressing delete next to a forward slash
                if (clean == cleanC) sel--

                if (clean.length < 8) {
                    val ddmmyyyy = "DDMMYYYY"
                    String.add(clean, ddmmyyyy.substring(clean.length))
                } else {
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    var day = Integer.parseInt(clean.substring(0, 2))
                    var mon = Integer.parseInt(clean.substring(2, 4))
                    var year = Integer.parseInt(clean.substring(4, 8))

                    mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                    calendar.set(Calendar.MONTH, mon - 1)
                    year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                    calendar.set(Calendar.YEAR, year)
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day =
                        if (day > calendar.getActualMaximum(Calendar.DATE)) calendar.getActualMaximum(Calendar.DATE) else day
                    clean = String.format(Locale.getDefault(), "%02d%02d%02d", day, mon, year)
                }

                clean = String.format(
                    "%s-%s-%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8)
                )

                sel = if (sel < 0) 0 else sel
                current = clean
                editText.setText(current)
                editText.setSelection(if (sel < current.length) sel else current.length)
                editText.addTextChangedListener(this)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            editText.addTextChangedListener(this)
        }

    }

    override fun afterTextChanged(s: Editable) {}

}
