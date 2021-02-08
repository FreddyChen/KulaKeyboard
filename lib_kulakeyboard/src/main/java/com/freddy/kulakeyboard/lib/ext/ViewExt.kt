package com.freddy.kulakeyboard.lib.ext

import android.view.View
import android.view.ViewTreeObserver

/**
 * FileName : ViewExt
 * Project  : KulaKeyboard
 * Package  : com.freddy.kulakeyboard.lib.ext
 * Author   : FreddyChen
 * Date     : 2021/02/08 16:41
 * Email    : chenshichao@outlook.com
 * GitHub   : https://github.com/FreddyChen
 * Describe : TODO
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}