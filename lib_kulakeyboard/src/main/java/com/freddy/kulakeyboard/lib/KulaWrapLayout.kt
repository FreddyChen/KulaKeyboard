package com.freddy.kulakeyboard.lib

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.animation.addListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freddy.kulakeyboard.lib.ext.afterMeasured
import com.freddy.kulakeyboard.lib.utils.DensityUtil

class KulaWrapLayout : LinearLayout {

    companion object {
        private const val TAG = "KulaRecyclerViewWrap"
    }

    private var type: Type = Type.One

    enum class Type {
        One,
        Two,
        Three
    }

    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText

    private var recyclerViewHeight: Int = 0
    private var keyboardHeight: Int = 0
    private var editTextHeight: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        orientation = VERTICAL
        editTextHeight = DensityUtil.dp2px(context, 50.0f)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        afterMeasured {
            Log.d(TAG, "onFinishInflate afterMeasured height = $height")
            createRecyclerView()
            createEditText()
            onReadyCallback?.invoke()
        }
    }

    private fun createRecyclerView() {
        recyclerViewHeight = height - editTextHeight
        recyclerView = RecyclerView(context)
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, recyclerViewHeight)
        recyclerView.layoutParams = lp
        recyclerView.setBackgroundColor(Color.GREEN)
        addView(recyclerView)
    }

    private fun createEditText() {
        editText = EditText(context)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, editTextHeight)
        editText.layoutParams = lp
        editText.setBackgroundColor(Color.RED)
        editText.setText("Test")
        editText.gravity = Gravity.CENTER
        addView(editText)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        var resultWidth = 0
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        var resultHeight = 0
        when (widthSpecMode) {
            MeasureSpec.AT_MOST -> {
                resultWidth = measureWidth + paddingStart + paddingEnd
            }
            MeasureSpec.EXACTLY -> {
                resultWidth = measureWidth
            }
            MeasureSpec.UNSPECIFIED -> {
                resultWidth = resultWidth.coerceAtLeast(measureWidth)
            }
        }
        when (heightSpecMode) {
            MeasureSpec.AT_MOST -> {
                resultHeight = measureHeight + paddingTop + paddingBottom
            }
            MeasureSpec.EXACTLY -> {
                resultHeight = measureHeight
            }
            MeasureSpec.UNSPECIFIED -> {
                resultHeight = resultHeight.coerceAtLeast(measureHeight)
            }
        }
        setMeasuredDimension(resultWidth, resultHeight)
    }

    private fun getLastItemBottom(): Int {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastItem = layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition())
            ?: return 0
        return lastItem.bottom
    }

    fun onKeyboardStateChanged(isOpen: Boolean, keyboardHeight: Int = 0) {
        when (isOpen) {
            true -> {
                this.keyboardHeight = keyboardHeight
                val bottom = getLastItemBottom()
                type = when {
                    bottom <= recyclerViewHeight - this.keyboardHeight -> Type.One
                    bottom > recyclerViewHeight - this.keyboardHeight && bottom < recyclerViewHeight -> Type.Two
                    else -> Type.Three
                }

                val animator = ValueAnimator.ofInt(this.keyboardHeight)
                animator.duration = 275
                animator.interpolator = DecelerateInterpolator()
                animator.addUpdateListener {
                    val value = it.animatedValue as Int
                    if(type != Type.One) recyclerView.smoothScrollBy(0, value)
                    val lp = recyclerView.layoutParams as LayoutParams
                    lp.height = recyclerViewHeight - value
                    recyclerView.layoutParams = lp
                }
                animator.addListener(
                    onEnd = {
                        recyclerViewHeight = recyclerView.height
                    }
                )
                animator.start()
            }
            else -> {
                val animator = ValueAnimator.ofInt(this.keyboardHeight)
                animator.duration = 275
                animator.interpolator = DecelerateInterpolator()
                animator.addUpdateListener {
                    val value = it.animatedValue as Int
                    if(type != Type.One) recyclerView.smoothScrollBy(0, -value)
                    val lp = recyclerView.layoutParams as LayoutParams
                    lp.height = recyclerViewHeight + value
                    recyclerView.layoutParams = lp
                }
                animator.addListener(
                    onEnd = {
                        recyclerViewHeight = recyclerView.height
                    }
                )
                animator.start()
            }
        }
    }

    private var onReadyCallback: (() -> Unit?)? = null
    fun setOnReadyCallback(callback: () -> Unit?) {
        this.onReadyCallback = callback
    }
}