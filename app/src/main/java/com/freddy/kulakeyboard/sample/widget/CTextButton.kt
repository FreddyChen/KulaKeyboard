package com.freddy.kulakeyboard.sample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.freddy.kulakeyboard.sample.R

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:19
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class CTextButton : AppCompatTextView {

    private var normalTextColor = 0
    private var pressedTextColor = 0
    private var disabledTextColor = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.CTextButton, defStyleAttr, 0)
        normalTextColor = array.getColor(
            R.styleable.CTextButton_ctb_normal_text_color,
            ContextCompat.getColor(context, R.color.c_333333)
        )
        pressedTextColor = array.getColor(
            R.styleable.CTextButton_ctb_pressed_text_color,
            ContextCompat.getColor(context, R.color.c_666666)
        )
        disabledTextColor = array.getColor(
            R.styleable.CTextButton_ctb_disabled_text_color,
            ContextCompat.getColor(context, R.color.c_999999)
        )
        array.recycle()

        init()
    }

    private fun init() {
        isClickable = true
        setTextColor(normalTextColor)
        setOnTouchListener(mOnTouchListener)
    }

    private val mOnTouchListener = OnTouchListener { v, event ->
        if (!isEnabled) {
            return@OnTouchListener false
        }
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                setTextColor(pressedTextColor)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                setTextColor(normalTextColor)
            }
        }
        false
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled) {
            setTextColor(normalTextColor)
        } else {
            setTextColor(disabledTextColor)
        }
        super.setEnabled(enabled)
    }
}