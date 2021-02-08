package com.spark.presentation.utils.ext

import android.text.TextUtils
import android.util.Patterns
import android.webkit.URLUtil
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.Companion.add(vararg strings: String?): String {
    var result = ""
    for (string in strings) {
        result += string
    }
    return result
}

fun String.add(vararg strings: String?): String {
    var result = this
    for (string in strings) {
        result += string
    }
    return result
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isValidDateTime(): Boolean {
    if (this.isNullOrEmpty()) return false
 if (!this.matches("""\d{2}\-\d{2}\-\d{4}""".toRegex())) return false
    val df = SimpleDateFormat("dd-MM-yyyy")
    //df.isLenient = false
    return try {
        df.parse(this)
        true
    } catch (ex: ParseException) {
        false
    }
}

fun String.isPasswordValid(): Boolean {
    return this.containsCapitalLetter && this.containsSpecialChar && this.containsDigit && this.containsLatinLetter && this.length > 8
}

fun String.addCurrency(): String {
    return this.add(" MYR")
}

fun String.bold(): String {
    return "<b>$this</b>"
}

fun String.nextLine(): String {
    return "<br>$this</br>"
}

fun BigDecimal.addCurrency(): String =
    NumberFormat.getCurrencyInstance(Locale("en", "MY")).format(this)

fun BigDecimal.addCurrency(country: String): String =
    NumberFormat.getCurrencyInstance(Locale("en", country)).format(this)

fun String.isUrl(): Boolean {
    return URLUtil.isValidUrl(this)
}

fun String.isPhoneNumber(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.ellipsize(at: Int): String {
    if (this.length > at) {
        return this.substring(0, at) + "..."
    }
    return this
}

fun String.capitalizeWords() = split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }

fun CharSequence.capitalizeWords() = split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }

fun String.toCamelCase(): String {
    if (TextUtils.isEmpty(this))
        return ""
    return Character.toUpperCase(this[0]) +
            this.substring(1).toLowerCase(Locale.ROOT)
}

fun String.Companion.addSeparatorAfter4Character(str: String): String {
    var number = str.replace(" ".toRegex(), "")
    number = number.replace("(\\w{4})".toRegex(), "$1" + " ")
    return number
}

fun String.Companion.addSeparatorAfter3Character(str: String): String {
    var number = str.replace(",".toRegex(), "")
    number = number.replace("(\\w{3})".toRegex(), "$1" + ",")
    return number
}

fun String.Companion.cardValidation(cardNumber: String): Boolean {
    val listOfPattern = ArrayList<String>()
    val ptVisa = "^4[0-9]{6,}$"
    listOfPattern.add(ptVisa)
    val ptMasterCard = "^5[1-5][0-9]{5,}$"
    listOfPattern.add(ptMasterCard)
    for (p in listOfPattern) {
        if (cardNumber.matches(p.toRegex()))
            return true
    }
    return false
}

val String.containsLatinLetter: Boolean
    get() = matches(Regex(".*[A-Za-z].*"))

val String.containsDigit: Boolean
    get() = matches(Regex(".*[0-9].*"))

val String.containsCapitalLetter: Boolean
    get() = matches(Regex(".*[A-Z].*"))

val String.containsSpecialChar: Boolean
    get() = !matches(Regex("[a-zA-Z0-9]*"))

val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null