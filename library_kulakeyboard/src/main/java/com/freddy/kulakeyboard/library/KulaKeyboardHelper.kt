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
    }

    fun init(context: Context): KulaKeyboardHelper {
        this.context = context
        setKeyboardHeight(DensityUtil.getScreenHeight(context) / 5 * 2)
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
        return this
    }

    fun bindRootLayout(rootLayout: ViewGroup): KulaKeyboardHelper {
        this.rootLayout = rootLayout
        keyboardStatePopupWindow = KeyboardStatePopupWindow(context, rootLayout)
        keyboardStatePopupWindow?.setOnKeyboardStateListener(object :
            KeyboardStatePopupWindow.OnKeyboardStateListener {
            override fun onOpened(keyboardHeight: Int) {
                inputPanel?.onSoftKeyboardOpened()
                onKeyboardStateListener?.onOpened(keyboardHeight)
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

    fun bindVoicePanel(panel: IPanel): KulaKeyboardHelper {
        return this
    }

    fun bindInputPanel(panel: IInputPanel): KulaKeyboardHelper {
        this.inputPanel = panel
        panel.setOnInputStateChangedListener(object : OnInputPanelStateChangedListener {
            override fun onShowInputMethodPanel() {
                if (expressionPanel !is ViewGroup) return
                GlobalScope.launch(Dispatchers.Main) {
                    delay(250)
                    (expressionPanel as ViewGroup).visibility = View.INVISIBLE
                }
            }

            override fun onShowExpressionPanel() {
                if (expressionPanel !is ViewGroup) return
                (expressionPanel as ViewGroup).visibility = View.VISIBLE
            }
        })
        panel.setOnLayoutAnimatorHandleListener { fromValue, toValue ->
            handlePanelMoveAnimator(fromValue, toValue)
        }
        return this
    }

    fun bindExpressionPanel(panel: IPanel): KulaKeyboardHelper {
        this.expressionPanel = panel
        return this
    }

    fun bindMorePanel(panel: IPanel): KulaKeyboardHelper {
        return this
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun handlePanelMoveAnimator(fromValue: Float, toValue: Float) {
        if (bodyLayout == null && expressionPanel == null) {
            return
        }
        if (expressionPanel !is ViewGroup) {
            return
        }
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