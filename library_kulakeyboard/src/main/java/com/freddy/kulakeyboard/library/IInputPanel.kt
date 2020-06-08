package com.freddy.kulakeyboard.library

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 16:16
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
interface IInputPanel : IPanel {

    fun onSoftKeyboardOpened()

    fun onSoftKeyboardClosed()

    fun setOnLayoutAnimatorHandleListener(listener: ((fromValue: Float, toValue: Float) -> Unit)?)

    fun setOnInputStateChangedListener(listener: OnInputPanelStateChangedListener?)
}