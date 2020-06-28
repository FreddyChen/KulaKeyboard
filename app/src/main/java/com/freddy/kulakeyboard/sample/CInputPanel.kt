package com.freddy.kulakeyboard.sample

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.freddy.kulakeyboard.library.IInputPanel
import com.freddy.kulakeyboard.library.KeyboardHelper
import com.freddy.kulakeyboard.library.OnInputPanelStateChangedListener
import com.freddy.kulakeyboard.library.PanelType
import com.freddy.kulakeyboard.library.util.DensityUtil
import com.freddy.kulakeyboard.library.util.UIUtil
import kotlinx.android.synthetic.main.layout_input_panel.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 13:40
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class CInputPanel : LinearLayout, IInputPanel {

    private var panelType = PanelType.NONE
    private var lastPanelType = panelType
    private var isKeyboardOpened = false

    companion object {
        const val TAG = "CInputPanel"
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this, true)
        init()
        setListeners()
    }

    private var isActive = false

    private fun init() {
        orientation = HORIZONTAL
        setPadding(
            DensityUtil.dp2px(context, 10.0f),
            DensityUtil.dp2px(context, 6.0f),
            DensityUtil.dp2px(context, 10.0f),
            DensityUtil.dp2px(context, 6.0f)
        )
        gravity = Gravity.BOTTOM
        setBackgroundColor(ContextCompat.getColor(context, R.color.c_77cbcbcb))
        et_content.inputType = InputType.TYPE_NULL
        et_content.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (!isKeyboardOpened) {
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(100)
                        UIUtil.requestFocus(et_content)
                        UIUtil.showSoftInput(context, et_content)
                    }
                    et_content.resetInputType()
                    btn_expression.setNormalImageResId(R.drawable.ic_chat_expression_normal)
                    btn_expression.setPressedImageResId(R.drawable.ic_chat_expression_pressed)
                    handleAnimator(PanelType.INPUT_MOTHOD)
                    onInputPanelStateChangedListener?.onShowInputMethodPanel()
                }
                return@setOnTouchListener true
            }
            false
        }
    }

    private fun setListeners() {
        btn_voice.setOnClickListener {
            btn_expression.setNormalImageResId(R.drawable.ic_chat_expression_normal)
            btn_expression.setPressedImageResId(R.drawable.ic_chat_expression_pressed)
            if (lastPanelType == PanelType.VOICE) {
                btn_voice_pressed.visibility = View.GONE
                et_content.visibility = View.VISIBLE
                UIUtil.requestFocus(et_content)
                UIUtil.showSoftInput(context, et_content)
                handleAnimator(PanelType.INPUT_MOTHOD)
                et_content.resetInputType()
            } else {
                btn_voice_pressed.visibility = View.VISIBLE
                et_content.visibility = View.GONE
                UIUtil.loseFocus(et_content)
                UIUtil.hideSoftInput(context, et_content)
                handleAnimator(PanelType.VOICE)
                onInputPanelStateChangedListener?.onShowVoicePanel()
            }
        }
        btn_expression.setOnClickListener {
            btn_voice_pressed.visibility = View.GONE
            et_content.visibility = View.VISIBLE
            if (lastPanelType == PanelType.EXPRESSION) {
                btn_expression.setNormalImageResId(R.drawable.ic_chat_expression_normal)
                btn_expression.setPressedImageResId(R.drawable.ic_chat_expression_pressed)
                UIUtil.requestFocus(et_content)
                UIUtil.showSoftInput(context, et_content)
                handleAnimator(PanelType.INPUT_MOTHOD)
                et_content.resetInputType()
            } else {
                btn_expression.setNormalImageResId(R.drawable.ic_chat_keyboard_normal)
                btn_expression.setPressedImageResId(R.drawable.ic_chat_keyboard_pressed)
                UIUtil.loseFocus(et_content)
                UIUtil.hideSoftInput(context, et_content)
                handleAnimator(PanelType.EXPRESSION)
                onInputPanelStateChangedListener?.onShowExpressionPanel()
            }
        }
        btn_more.setOnClickListener {
            btn_expression.setNormalImageResId(R.drawable.ic_chat_expression_normal)
            btn_expression.setPressedImageResId(R.drawable.ic_chat_expression_pressed)
            btn_voice_pressed.visibility = View.GONE
            et_content.visibility = View.VISIBLE
            if (lastPanelType == PanelType.MORE) {
                UIUtil.requestFocus(et_content)
                UIUtil.showSoftInput(context, et_content)
                handleAnimator(PanelType.INPUT_MOTHOD)
                et_content.resetInputType()
            } else {
                UIUtil.loseFocus(et_content)
                UIUtil.hideSoftInput(context, et_content)
                handleAnimator(PanelType.MORE)
                onInputPanelStateChangedListener?.onShowMorePanel()
            }
        }
        et_content.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Toast.makeText(context, "发送", Toast.LENGTH_SHORT).show()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun handleAnimator(panelType: PanelType) {
        Log.d(TAG, "lastPanelType = $lastPanelType\tpanelType = $panelType")
        if (lastPanelType == panelType) {
            return
        }
        isActive = true
        Log.d(TAG, "isActive = $isActive")
        this.panelType = panelType
        var fromValue = 0.0f
        var toValue = 0.0f
        when (panelType) {
            PanelType.VOICE -> {
                when (lastPanelType) {
                    PanelType.INPUT_MOTHOD -> {
                        fromValue = -KeyboardHelper.inputPanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    PanelType.EXPRESSION -> {
                        fromValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    PanelType.MORE -> {
                        fromValue = -KeyboardHelper.morePanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    PanelType.NONE -> {
                        fromValue = 0.0f
                        toValue = 0.0f
                    }
                    else -> {
                    }
                }
            }
            PanelType.INPUT_MOTHOD ->
                when (lastPanelType) {
                    PanelType.VOICE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.inputPanelHeight.toFloat()
                    }
                    PanelType.EXPRESSION -> {
                        fromValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                        toValue = -KeyboardHelper.inputPanelHeight.toFloat()
                    }
                    PanelType.MORE -> {
                        fromValue = -KeyboardHelper.morePanelHeight.toFloat()
                        toValue = -KeyboardHelper.inputPanelHeight.toFloat()
                    }
                    PanelType.NONE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.inputPanelHeight.toFloat()
                    }
                    else -> {
                    }
                }
            PanelType.EXPRESSION ->
                when (lastPanelType) {
                    PanelType.INPUT_MOTHOD -> {
                        fromValue = -KeyboardHelper.inputPanelHeight.toFloat()
                        toValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                    }
                    PanelType.VOICE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                    }
                    PanelType.MORE -> {
                        fromValue = -KeyboardHelper.morePanelHeight.toFloat()
                        toValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                    }
                    PanelType.NONE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                    }
                    else -> {
                    }
                }
            PanelType.MORE ->
                when (lastPanelType) {
                    PanelType.INPUT_MOTHOD -> {
                        fromValue = -KeyboardHelper.inputPanelHeight.toFloat()
                        toValue = -KeyboardHelper.morePanelHeight.toFloat()
                    }
                    PanelType.VOICE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.morePanelHeight.toFloat()
                    }
                    PanelType.EXPRESSION -> {
                        fromValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                        toValue = -KeyboardHelper.morePanelHeight.toFloat()
                    }
                    PanelType.NONE -> {
                        fromValue = 0.0f
                        toValue = -KeyboardHelper.morePanelHeight.toFloat()
                    }
                    else -> {
                    }
                }
            PanelType.NONE ->
                when (lastPanelType) {
                    PanelType.VOICE -> {
                        // from 0.0f to 0.0f
                    }
                    PanelType.INPUT_MOTHOD -> {
                        fromValue = -KeyboardHelper.inputPanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    PanelType.EXPRESSION -> {
                        fromValue = -KeyboardHelper.expressionPanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    PanelType.MORE -> {
                        fromValue = -KeyboardHelper.morePanelHeight.toFloat()
                        toValue = 0.0f
                    }
                    else -> {
                    }
                }
        }
        onLayoutAnimatorHandleListener?.invoke(panelType, lastPanelType, fromValue, toValue)
        lastPanelType = panelType
    }

    private var onLayoutAnimatorHandleListener: ((panelType: PanelType, lastPanelType: PanelType, fromValue: Float, toValue: Float) -> Unit)? =
        null
    private var onInputPanelStateChangedListener: OnInputPanelStateChangedListener? = null
    override fun onSoftKeyboardOpened() {
        isKeyboardOpened = true
        et_content.resetInputType()
    }

    override fun onSoftKeyboardClosed() {
        isKeyboardOpened = false
        et_content.inputType = InputType.TYPE_NULL
        if (lastPanelType == PanelType.INPUT_MOTHOD) {
            UIUtil.loseFocus(et_content)
            UIUtil.hideSoftInput(context, et_content)
            handleAnimator(PanelType.NONE)
        }
    }

    override fun setOnLayoutAnimatorHandleListener(listener: ((panelType: PanelType, lastPanelType: PanelType, fromValue: Float, toValue: Float) -> Unit)?) {
        this.onLayoutAnimatorHandleListener = listener
    }

    override fun setOnInputStateChangedListener(listener: OnInputPanelStateChangedListener?) {
        this.onInputPanelStateChangedListener = listener
    }

    override fun reset() {
        if (!isActive) {
            return
        }
        Log.d(TAG, "reset()")
        UIUtil.loseFocus(et_content)
        UIUtil.hideSoftInput(context, et_content)
        btn_expression.setNormalImageResId(R.drawable.ic_chat_expression_normal)
        btn_expression.setPressedImageResId(R.drawable.ic_chat_expression_pressed)
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            handleAnimator(PanelType.NONE)
        }
        isActive = false
    }

    override fun getPanelHeight(): Int {
        return KeyboardHelper.keyboardHeight
    }
}