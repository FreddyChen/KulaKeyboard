package com.freddy.kulakeyboard.library

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.freddy.kulakeyboard.library.util.DensityUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 11:49
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class KulaKeyboardHelper {

    private lateinit var context: Context
    private var rootLayout: ViewGroup? = null
    private var bodyLayout: ViewGroup? = null
    private var inputPanel: IInputPanel? = null
    private var expressionPanel: IPanel? = null
    private var keyboardStatePopupWindow: KeyboardStatePopupWindow? = null

    companion object {
        var keyboardHeight = 0
        var inputPanelHeight = 0
        var expressionPanelHeight = 0
    }

    fun init(context: Context): KulaKeyboardHelper {
        this.context = context
        return this
    }

    fun reset() {
        inputPanel?.reset()
        expressionPanel?.reset()
    }

    fun release() {
        inputPanel?.release()
        inputPanel = null
        expressionPanel?.release()
        expressionPanel = null
        keyboardStatePopupWindow?.dismiss()
        keyboardStatePopupWindow = null
    }

    fun setKeyboardHeight(keyboardHeight: Int): KulaKeyboardHelper {
        KulaKeyboardHelper.keyboardHeight = keyboardHeight
        if(inputPanelHeight == 0) {
            inputPanelHeight = keyboardHeight
        }
        return this
    }

    fun bindRootLayout(rootLayout: ViewGroup): KulaKeyboardHelper {
        this.rootLayout = rootLayout
        keyboardStatePopupWindow = KeyboardStatePopupWindow(context, rootLayout)
        keyboardStatePopupWindow?.setOnKeyboardStateListener(object :
            KeyboardStatePopupWindow.OnKeyboardStateListener {
            override fun onOpened(keyboardHeight: Int) {
                KulaKeyboardHelper.keyboardHeight = keyboardHeight
                inputPanel?.onSoftKeyboardOpened()
                onKeyboardStateListener?.onOpened(keyboardHeight)
                inputPanel?.apply {
                    inputPanelHeight = getPanelHeight()
                }
                expressionPanel?.apply {
                    expressionPanelHeight = getPanelHeight()
                }
            }

            override fun onClosed() {
                inputPanel?.onSoftKeyboardClosed()
                onKeyboardStateListener?.onClosed()
            }
        })
        return this
    }

    fun bindBodyLayout(bodyLayout: ViewGroup): KulaKeyboardHelper {
        this.bodyLayout = bodyLayout
        return this
    }

    fun<P : IPanel> bindVoicePanel(panel: P): KulaKeyboardHelper {
        return this
    }

    fun<P : IInputPanel> bindInputPanel(panel: P): KulaKeyboardHelper {
        this.inputPanel = panel
        inputPanelHeight = panel.getPanelHeight()
        panel.setOnInputStateChangedListener(object : OnInputPanelStateChangedListener {
            override fun onShowInputMethodPanel() {
                if (expressionPanel !is ViewGroup) return
                GlobalScope.launch(Dispatchers.Main) {
                    delay(250)
                    expressionPanel?.let {
                        it as ViewGroup
                        it.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onShowExpressionPanel() {
                if (expressionPanel !is ViewGroup) return
                expressionPanel?.let {
                    it as ViewGroup
                    it.visibility = View.VISIBLE
                }
            }
        })
        panel.setOnLayoutAnimatorHandleListener { fromValue, toValue ->
            handlePanelMoveAnimator(fromValue, toValue)
        }
        return this
    }

    fun<P : IPanel> bindExpressionPanel(panel: P): KulaKeyboardHelper {
        this.expressionPanel = panel
        expressionPanelHeight = panel.getPanelHeight()
        return this
    }

    fun<P : IPanel> bindMorePanel(panel: P): KulaKeyboardHelper {
        return this
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun handlePanelMoveAnimator(fromValue: Float, toValue: Float) {
        if (bodyLayout == null || expressionPanel == null) {
            return
        }
        if (expressionPanel !is ViewGroup) return
        val bodyLayoutTranslationYAnimator: ObjectAnimator =
            ObjectAnimator.ofFloat(bodyLayout, "translationY", fromValue, toValue)
        val expressionPanelTranslationYAnimator: ObjectAnimator =
            ObjectAnimator.ofFloat(expressionPanel, "translationY", fromValue, toValue)
        val animatorSet = AnimatorSet()
        animatorSet.duration = 250
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.play(bodyLayoutTranslationYAnimator).with(expressionPanelTranslationYAnimator)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                bodyLayout?.requestLayout()
                expressionPanel?.let {
                    it as ViewGroup
                    it.requestLayout()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    private var onKeyboardStateListener: OnKeyboardStateListener? = null
    fun setOnKeyboardStateListener(listener: OnKeyboardStateListener?): KulaKeyboardHelper {
        this.onKeyboardStateListener = listener
        return this
    }
    interface OnKeyboardStateListener {
        fun onOpened(keyboardHeight: Int)
        fun onClosed()
    }
}