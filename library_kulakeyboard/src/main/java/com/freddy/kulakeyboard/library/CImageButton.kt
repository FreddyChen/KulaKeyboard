package com.freddy.kulakeyboard.library

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:19
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class CImageButton : AppCompatImageButton {

    private var normalImageResId = 0
    private var pressedImageResId = 0
    private var disabledImageResId = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.CImageButton, defStyleAttr, 0)
        array.apply {
            normalImageResId = getResourceId(R.styleable.CImageButton_kula_kb_cib_normal_image_res_id, 0)
            pressedImageResId = getResourceId(R.styleable.CImageButton_kula_kb_cib_pressed_image_res_id, 0)
            disabledImageResId = getResourceId(R.styleable.CImageButton_kula_kb_cib_disabled_image_res_id, 0)
            recycle()
        }

        init()
    }

    private fun init() {
        isClickable = true
        setImageResource(normalImageResId)
        setOnTouchListener(mOnTouchListener)
        scaleType = ScaleType.CENTER_INSIDE
    }

    fun setNormalImageResId(@DrawableRes resId: Int) {
        this.normalImageResId = resId
        setImageResource(resId)
    }

    fun setPressedImageResId(@DrawableRes resId: Int) {
        this.pressedImageResId = resId
    }

    fun setDisabledImageResId(@DrawableRes resId: Int) {
        this.disabledImageResId = resId
        setImageResource(resId)
    }

    private val mOnTouchListener = OnTouchListener { v, event ->
        if (!isEnabled) {
            return@OnTouchListener false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                setImageResource(pressedImageResId)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                setImageResource(normalImageResId)
            }
        }
        false
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled) {
            setImageResource(normalImageResId)
        } else {
            setImageResource(disabledImageResId)
        }
        super.setEnabled(enabled)
    }
}