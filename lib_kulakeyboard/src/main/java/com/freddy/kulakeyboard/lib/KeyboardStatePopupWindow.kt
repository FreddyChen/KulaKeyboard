package com.freddy.kulakeyboard.lib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import com.freddy.kulakeyboard.lib.utils.DensityUtil

/**
 * FileName : KeyboardStatePopupWindow
 * Project  : KulaKeyboard
 * Package  : com.freddy.kulakeyboard.lib
 * Author   : FreddyChen
 * Date     : 2021/02/08 16:40
 * Email    : chenshichao@outlook.com
 * GitHub   : https://github.com/FreddyChen
 * Describe : TODO
 */
@SuppressLint("LongLogTag")
class KeyboardStatePopupWindow(
    private val context: Context,
    private var anchorView: View
) : PopupWindow(), ViewTreeObserver.OnGlobalLayoutListener {

    private var maxHeight = 0
    private var isSoftKeyboardOpened = false

    companion object {
        private const val TAG = "KeyboardStatePopupWindow"
    }

    fun init() {
        contentView = View(context)
        width = 0// 宽度为0，不可见，但高度为MATCH_PARENT，仍可监听布局变化，实现键盘展开/收起状态的监听
        height = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        inputMethodMode = INPUT_METHOD_NEEDED
        contentView.viewTreeObserver.addOnGlobalLayoutListener(this)
        anchorView.post {
            showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, 0)
        }
    }

    fun release() {
        contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        dismiss()
    }

    override fun onGlobalLayout() {
        val rect = Rect()
        contentView.getWindowVisibleDisplayFrame(rect)
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom
        }
        val screenHeight: Int = DensityUtil.getScreenHeight(context)
        //键盘的高度
        val keyboardHeight = maxHeight - rect.bottom
        val visible = keyboardHeight > screenHeight / 4
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true
            onKeyboardStateListener?.onOpened(keyboardHeight)
            Log.d(TAG, "键盘已展开，keyboardHeight = $keyboardHeight")
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false
            onKeyboardStateListener?.onClosed()
            Log.d(TAG, "键盘已收起，keyboardHeight = $keyboardHeight")
        }
    }

    private var onKeyboardStateListener: OnKeyboardStateListener? = null
    fun setOnKeyboardStateListener(listener: OnKeyboardStateListener?) {
        this.onKeyboardStateListener = listener
    }

    interface OnKeyboardStateListener {
        fun onOpened(keyboardHeight: Int)
        fun onClosed()
    }
}