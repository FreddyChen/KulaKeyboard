package com.freddy.kulakeyboard.sample.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.freddy.kulakeyboard.library.util.DensityUtil
import com.freddy.kulakeyboard.sample.R

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:23
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class CEditText : AppCompatEditText {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.editTextStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        imeOptions = EditorInfo.IME_ACTION_SEND
        resetInputType()
        gravity = Gravity.CENTER_VERTICAL
        setPadding(
            DensityUtil.dp2px(context, 14.0f),
            DensityUtil.dp2px(context, 8.0f),
            DensityUtil.dp2px(context, 14.0f),
            DensityUtil.dp2px(context, 8.0f)
        )
        setTextColor(ContextCompat.getColor(context,
            R.color.c_000000
        ))
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        setHorizontallyScrolling(false)
        maxLines = 5
    }

    fun resetInputType() {
        inputType = InputType.TYPE_CLASS_TEXT
    }
}