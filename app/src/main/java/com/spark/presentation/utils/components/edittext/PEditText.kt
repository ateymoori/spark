package com.spark.presentation.utils.components.edittext

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.spark.presentation.utils.components.bottomSheetList.PBottomSheetList
import com.spark.presentation.utils.components.edittext.listener.IPEditTextListener
import com.spark.presentation.utils.ext.capitalizeWords
import com.spark.presentation.utils.components.bottomSheetList.base.BaseAdapter
import com.spark.R
import com.spark.presentation.utils.components.bottomSheetList.base.TimeHelper
import kotlinx.android.synthetic.main.p_edit_text_label.view.*
import java.util.*

class PEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener, TextWatcher {

    override fun onClick(view: View) {
        if (editText.inputType == 129)
            if (isPassShow) {
                imageEnd = R.drawable.ic_eye_off
                imageViewEnd.setImageResource(imageEnd)
                isPassShow = false
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                editText.setSelection(getLength())
            } else {
                imageEnd = R.drawable.ic_eye
                imageViewEnd.setImageResource(imageEnd)
                isPassShow = true
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                editText.setSelection(getLength())
            }
        if (isIconsEnable)
            if (view.id == R.id.imageViewStart) {
                iconListener?.startIconListener()

            } else if (view.id == R.id.imageViewEnd) {
                iconListener?.endIconListener()
            }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        clearError()
    }

    // <editor-fold defaultState="collapsed" desc="Variables">
    val KIND_DEFAULT = -1
    val KIND_DATE = 0
    val KIND_SPINNER = 1
    val KIND_AMOUNT = 2
    val KIND_SEARCH = 3
    val KIND_NIC = 4
    val KIND_PHONE = 5
    val KIND_CARD = 6
    val KIND_EXPIRY = 7

    internal enum class Type {
        DEFAULT,
        ERROR,
        DISABLED
    }

    private var bottomSheetList: PBottomSheetList? = null
    private var iconListener: IPEditTextListener? = null

    private var bg: Int = 0
    private var text: String? = null
    private var hint: String? = null
    private var tag: String? = null
    private var textColor: Int = ContextCompat.getColor(getContext(), R.color.textPrimaryDark)
    private var textStyle: Int = Typeface.NORMAL
    private var labelBackgroundColor: Int =
        ContextCompat.getColor(getContext(), R.color.transparent)
    private var textColorHint: Int = -1
    private var inputType: Int = InputType.TYPE_CLASS_TEXT
    private var maxLength: Int = 100
    private var gravity: Int = Gravity.CENTER_VERTICAL or Gravity.START
    private var lines: Int = 1
    private var maxLines: Int = 1
    private var imeOptions: Int = EditorInfo.IME_ACTION_NEXT
    private var imageStart: Int = 0
    private var imageEnd: Int = 0
    private var textAllCaps = false
    private var isEnable = true
    private var isIconsEnable = true
    private var hasError = true
    private var isPassShow: Boolean = false
    private var textSize = getContext().resources.getDimension(R.dimen.bodyFontSize)
    private var kind = KIND_DEFAULT
    private var hasHint = true
    // </editor-fold>

    // <editor-fold defaultState="collapsed" desc="init">

    init {
        LayoutInflater.from(context).inflate(R.layout.p_edit_text_label, this, true)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PEditText, 0, 0)
            bg = typedArray.getResourceId(R.styleable.PEditText_android_background, bg)
            text = typedArray.getString(R.styleable.PEditText_android_text)
            hint = typedArray.getString(R.styleable.PEditText_android_hint)
            tag = typedArray.getString(R.styleable.PEditText_android_tag)

            textStyle = typedArray.getInt(R.styleable.PEditText_android_textStyle, textStyle)
            textColor =
                typedArray.getInt(R.styleable.PEditText_android_textColor, R.color.textPrimaryDark)
            textColorHint = R.color.black
//                typedArray.getInt(R.styleable.PEditText_android_textColorHint, textColorHint)
            labelBackgroundColor =
                typedArray.getInt(R.styleable.PEditText_backgroundColor, labelBackgroundColor)
            textSize =
                typedArray.getDimensionPixelSize(
                    R.styleable.PEditText_android_textSize,
                    textSize.toInt()
                ).toFloat()

            imageStart =
                typedArray.getResourceId(R.styleable.PEditText_android_drawableStart, imageStart)
            imageEnd = typedArray.getResourceId(R.styleable.PEditText_android_drawableEnd, imageEnd)

            inputType = typedArray.getInt(R.styleable.PEditText_android_inputType, inputType)
            imeOptions = typedArray.getInt(R.styleable.PEditText_android_imeOptions, imeOptions)
            gravity = typedArray.getInt(R.styleable.PEditText_android_gravity, gravity)
            lines = typedArray.getInt(R.styleable.PEditText_android_lines, lines)
            maxLines = typedArray.getInt(R.styleable.PEditText_android_lines, maxLines)
            maxLength = typedArray.getInt(R.styleable.PEditText_android_maxLength, maxLength)

            textAllCaps =
                typedArray.getBoolean(R.styleable.PEditText_android_textAllCaps, textAllCaps)
            isEnable = typedArray.getBoolean(R.styleable.PEditText_android_enabled, isEnable)
            hasError = typedArray.getBoolean(R.styleable.PEditText_errorEnabled, hasError)
            hasHint = typedArray.getBoolean(R.styleable.PEditText_hasHint, hasHint)

            kind = typedArray.getInt(R.styleable.PEditText_kind, kind)

            typedArray.recycle()
            initView()
        }

    }

    private fun initView() {

        initEditText()
        initImageViewStart()
        initImageViewEnd()
        initKind()

    }

    private fun initEditText() {

        if (bg != 0) {
            editTextContainer.setBackgroundResource(bg)
        }

        editText.setText(text?.capitalizeWords())
        placeHolder.text = hint?.capitalizeWords()
        editText.hint = hint?.capitalizeWords()
        editText.tag = tag?.capitalizeWords()
        editText.setTextColor(textColor)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        placeHolder.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        editText.setTypeface(editText.typeface, textStyle)
        if (textColorHint != -1)
            editText.setHintTextColor(textColorHint)
        editText.inputType = inputType
        editText.imeOptions = imeOptions
        editText.gravity = gravity
        editText.setLines(lines)
        editText.isAllCaps = textAllCaps
        editText.maxLines = maxLines
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

        editText.addTextChangedListener(this)

        if (!hasError)
            errorTv.visibility = View.GONE

        if (editText.inputType == 129) {
            imageEnd = R.drawable.ic_eye
            isPassShow = true
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelection(getLength())
        }

        if (!hasHint)
            placeHolder.visibility = View.GONE

        isEnabled = isEnable
    }

    private fun initImageViewStart() {
        if (imageStart != 0) {
            imageViewStart.setImageResource(imageStart)
            imageViewStart.setOnClickListener(this)
            imageViewStart.requestLayout()
        } else {
            imageViewStart.setImageResource(0)
        }
    }

    private fun initImageViewEnd() {
        if (imageEnd != 0) {
            imageViewEnd.setImageResource(imageEnd)
            imageViewEnd.setOnClickListener(this)
            imageViewEnd.requestLayout()
        } else {
            imageViewEnd.setImageResource(0)
        }
    }

    private fun initKind() {
        when (kind) {
            KIND_DATE // Date
            -> {
                val calendar = Calendar.getInstance()
                val year =
                    if (isEmpty()) calendar.get(Calendar.YEAR) else TimeHelper.getYear(getText())
                val month =
                    if (isEmpty()) calendar.get(Calendar.MONTH) else TimeHelper.getMonth(getText())
                val day = if (isEmpty()) calendar.get(Calendar.DAY_OF_MONTH) else TimeHelper.getDay(
                    getText()
                )
                val datePickerDialog = DatePickerDialog(
                    context,
                    R.style.DimePickerTheme, { _, selectedYear, selectedMonth, dayOfMonth ->
                        var clean = String.format(
                            Locale.getDefault(), "%02d%02d%02d", dayOfMonth,
                            selectedMonth + 1, selectedYear
                        )
                        clean = String.format(
                            "%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8)
                        )
                        editText.setText(clean)
                    }, year, month, day
                )

                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.addTextChangedListener(DateSeparator(editText))
                imageViewEnd.setImageResource(R.drawable.ic_date)
                imageViewEnd.setOnClickListener { datePickerDialog.show() }

                editText.setOnClickListener { datePickerDialog.show() }
                editText.isFocusable = false
                editText.isClickable = true
             //   editText.isEnabled = false

            }
            KIND_SPINNER // Spinner
            -> {
                editText.isFocusableInTouchMode = false
                editText.inputType = InputType.TYPE_NULL
                imageViewEnd.setImageResource(R.drawable.ic_arrow_down)
            }
            KIND_SEARCH // Search
            -> imageViewEnd.setImageResource(R.drawable.ic_search)
            KIND_PHONE//PHONE
            -> {
                setMaxLength(14)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.addTextChangedListener(PrefixWatcher(editText))
            }
            else -> initEditText()
        }
    }

    // </editor-fold>

    // <editor-fold defaultState="collapsed" desc="Getters">
    fun getEditText(): AppCompatEditText {
        return editText
    }

    fun getEndIcon(): ImageView {
        return imageViewEnd
    }

    fun getStartIcon(): ImageView {
        return imageViewStart
    }

    fun getText(): String {
        return when (kind) {
            KIND_CARD, KIND_EXPIRY -> editText.text.toString()
                .replace(" ", "")
            KIND_PHONE -> {
                if (tag.isNullOrEmpty())
                    editText.text.toString().replace("+6", "").replace(" ", "")
                else
                    editText.text.toString().replace("$tag", "").replace(" ", "")
            }
            KIND_AMOUNT -> editText.text.toString().replace(",", "").replace(" ", "")
            else -> editText.text.toString()
        }
    }

    fun getLength(): Int {
        return editText.text.toString().trim { it <= ' ' }.length
    }

    fun isEmpty(): Boolean {
        return editText.text!!.isEmpty()
    }

    fun View.isVisible(): Boolean {
        return this.visibility == View.VISIBLE
    }


    override fun clearFocus() {
        super.clearFocus()
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }

    fun setText(text: String?) {
        this.text = text?.capitalizeWords()
        editText.setText(text)
        editText.setTextColor(ContextCompat.getColor(context, R.color.textPrimaryDark))
        if (kind == KIND_DATE)
            initKind()
    }

    fun setHint(hint: String) {
        this.hint = hint
        placeHolder.text = hint
        clearError()
    }

    fun setError(error: String) {

        if (!hasError)
            errorTv.visibility = View.VISIBLE

        if (textColor != -1)
            editText.setTextColor(textColor)

        editText.setHintTextColor(ContextCompat.getColor(context, R.color.error))
        placeHolder.setTextColor(ContextCompat.getColor(context, R.color.error))
        setBackgroundEdt(Type.ERROR)

        errorTv.text = error

    }

    fun setTextColor(color: Int) {
        editText.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setHintTextColor(color: Int) {
        editText.setHintTextColor(ContextCompat.getColor(context, color))
    }

    fun setIconListener(iconListener: IPEditTextListener?) {
        this.iconListener = iconListener
    }

    fun setMaxLength(maxLength: Int) {
        this.maxLength = maxLength
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    fun setTag(tag: String) {
        this.tag = tag
        editText.tag = tag
        setText("")
    }

    fun setKind(kind: Int) {
        clearInput()
        this.kind = kind
        initKind()
    }
    private fun setBackgroundEdt(type: Type) {

        if (bg != 0 && type != Type.ERROR) {
            editTextContainer.setBackgroundResource(bg)
            return
        }

        when (type) {
            Type.ERROR -> editTextContainer.setBackgroundResource(R.drawable.shape_rectangle_transparent_red_24)
            Type.DISABLED -> editTextContainer.setBackgroundResource(R.drawable.shape_rectangle_transparent_disable_24)
            else -> editTextContainer.setBackgroundResource(R.drawable.selector_rectangle_transparent_divider_to_transparent_hint_24dp)
        }
    }

    fun setImageStart(imageStart: Int) {
        this.imageStart = imageStart
        initImageViewStart()
    }

    fun setImageEnd(imageEnd: Int) {
        this.imageEnd = imageEnd
        initImageViewEnd()
    }

    fun clearInput() {
        editText.setText("")
        clearError()
    }

    override fun setBackgroundResource(resid: Int) {
        // nothing
    }

    override fun setBackground(background: Drawable) {
        // nothing
    }

    override fun setEnabled(enabled: Boolean) {
        if (!isEnable)
            setBackgroundEdt(Type.DISABLED)
        isEnable = enabled
        editText.isEnabled = isEnable
    }

    fun setIconEnabled(enabled: Boolean) {
        isIconsEnable = enabled
    }

    private fun clearError() {
        if (hasError) {
            if (textColor != -1)
                editText.setTextColor(textColor)
            placeHolder.setTextColor(textColor)
            editText.setHintTextColor(ContextCompat.getColor(context, R.color.hintColor))
            setBackgroundEdt(Type.DEFAULT)

            errorTv.text = ""
        }
    }

    fun setSpinnerAdapter(adapter: BaseAdapter<*>) {
        editText.setOnClickListener {
            bottomSheetList = PBottomSheetList.newInstance(getText())
            bottomSheetList.let {
                it?.show((context as AppCompatActivity).supportFragmentManager, "cardListBTM")
            }
            bottomSheetList.let {
                it?.setAdapter(adapter)
            }
        }

        imageViewEnd.setOnClickListener {
            bottomSheetList = PBottomSheetList.newInstance(getText())
            bottomSheetList.let {
                it?.show((context as AppCompatActivity).supportFragmentManager, "cardListBTM")
            }
            bottomSheetList.let {
                it?.setAdapter(adapter)
            }
        }


    }



    fun dismiss() {
        bottomSheetList.let {
            it?.dismiss()
        }
    }


}