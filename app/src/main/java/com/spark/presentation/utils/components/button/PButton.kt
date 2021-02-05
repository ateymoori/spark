package com.spark.presentation.utils.components.button

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.spark.presentation.utils.ext.capitalizeWords
import com.spark.presentation.utils.ext.dpToPx


class PButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private var animatedDrawable: CircularAnimatedDrawable? = null
    private val paddingProgress = 16.dpToPx()
    private val strokeWidth = 4.dpToPx().toFloat()
    private var buttonText = ""
    private var canvas: Canvas? = null
    private var isLoading = false

    // </editor-fold>

    init {
        this.buttonText = text.toString()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        text = if (isLoading) {
            drawIndeterminateProgress(canvas)
            ""
        } else buttonText
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text?.capitalizeWords(), type)
    }

    private fun drawIndeterminateProgress(canvas: Canvas?) {
        if (animatedDrawable == null) {
            val offset = (width - height) / 2
            animatedDrawable = CircularAnimatedDrawable(
                textColors.defaultColor,
                strokeWidth
            )
            val left: Int = offset + paddingProgress
            val right: Int = width - offset - paddingProgress
            val bottom: Int = height - paddingProgress
            val top: Int = paddingProgress
            animatedDrawable?.setBounds(left, top, right, bottom)
            animatedDrawable?.callback = this
            animatedDrawable?.start()
        } else
            canvas?.let { animatedDrawable?.draw(it) }
    }

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        isEnabled = !loading
        text = if (isLoading) {
            drawIndeterminateProgress(canvas)
            ""
        } else buttonText
    }

    fun startLoading() {
        setLoading(true)
    }

    fun stopLoading() {
        setLoading(false)
    }

    fun setBtnText(string: String) {
        buttonText = string
        text = string
    }

}