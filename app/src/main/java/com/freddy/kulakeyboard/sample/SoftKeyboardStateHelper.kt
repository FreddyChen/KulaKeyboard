package com.freddy.kulakeyboard.sample

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import java.util.*

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:42
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class SoftKeyboardStateHelper : ViewTreeObserver.OnGlobalLayoutListener {

    interface SoftKeyboardStateListener {
        fun onSoftKeyboardOpened(keyboardHeight: Int)
        fun onSoftKeyboardClosed()
    }

    private val listeners: MutableList<SoftKeyboardStateListener> =
        LinkedList()
    private var activityRootView: View? = null
    private var lastSoftKeyboardHeightInPx = 0
    private var isSoftKeyboardOpened = false

    constructor(activityRootView: View?) : this(activityRootView, false)

    constructor(activityRootView: View?, isSoftKeyboardOpened: Boolean) {
        this.activityRootView = activityRootView
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
        activityRootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val r = Rect()
        activityRootView!!.getWindowVisibleDisplayFrame(r)
        val screenHeight: Int = DensityUtil.getScreenHeight()
        val heightDifference = screenHeight - r.bottom
        val visible = heightDifference > screenHeight / 4
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true
            notifyOnSoftKeyboardOpened(heightDifference)
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false
            notifyOnSoftKeyboardClosed()
        }
    }


    fun setIsSoftKeyboardOpened(isSoftKeyboardOpened: Boolean) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
    }

    fun isSoftKeyboardOpened(): Boolean {
        return isSoftKeyboardOpened
    }

    fun getLastSoftKeyboardHeightInPx(): Int {
        return lastSoftKeyboardHeightInPx
    }

    fun addSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx
        for (listener in listeners) {
            listener?.onSoftKeyboardOpened(keyboardHeightInPx)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener?.onSoftKeyboardClosed()
        }
    }

    fun release() {
        activityRootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }
}