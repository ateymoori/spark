package com.spark.presentation.utils.components.edittext

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.spark.presentation.utils.ext.add

class PrefixWatcher(private val editText: AppCompatEditText) : TextWatcher {

    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        val mask = if (editText.tag == null) "+6 " else editText.tag.toString()

        if (isRunning || isDeleting) {
            return
        }

        isRunning = true

        if (!editable.toString().startsWith(mask.trim { it <= ' ' })) {
            editText.setText(String.add(mask, editable.toString()))
            Selection.setSelection(editText.text, editText.text!!.length)
        }

        isRunning = false
    }

}
