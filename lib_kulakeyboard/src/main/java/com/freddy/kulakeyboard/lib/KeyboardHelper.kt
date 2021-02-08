package com.freddy.kulakeyboard.lib

import android.content.Context
import android.view.ViewGroup

/**
 * FileName : KeyboardHelper
 * Project  : KulaKeyboard
 * Package  : com.freddy.kulakeyboard.lib
 * Author   : FreddyChen
 * Date     : 2021/02/08 16:40
 * Email    : chenshichao@outlook.com
 * GitHub   : https://github.com/FreddyChen
 * Describe : TODO
 */
class KeyboardHelper : KeyboardStatePopupWindow.OnKeyboardStateListener {

    private lateinit var context: Context
    private var popupWindow: KeyboardStatePopupWindow? = null
    private lateinit var wrapLayout: KulaWrapLayout

    fun init(context: Context, rootLayout: ViewGroup, wrapLayout: KulaWrapLayout) {
        this.context = context
        this.wrapLayout = wrapLayout
        popupWindow = KeyboardStatePopupWindow(context, rootLayout).apply {
            init()
            setOnKeyboardStateListener(this@KeyboardHelper)
        }
    }

    fun reset() {

    }

    fun release() {

    }

    override fun onOpened(keyboardHeight: Int) {
        wrapLayout.onKeyboardStateChanged(isOpen = true, keyboardHeight)
    }

    override fun onClosed() {
        wrapLayout.onKeyboardStateChanged(isOpen = false)
    }
}