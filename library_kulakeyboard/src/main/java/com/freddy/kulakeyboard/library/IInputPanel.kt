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

    /**
     * 软键盘打开
     */
    fun onSoftKeyboardOpened()

    /**
     * 软件盘关闭
     */
    fun onSoftKeyboardClosed()

    /**
     * 设置布局动画处理监听器
     */
    fun setOnLayoutAnimatorHandleListener(listener: ((panelType: PanelType, lastPanelType: PanelType, fromValue: Float, toValue: Float) -> Unit)?)

    /**
     * 设置输入面板（包括软键盘、表情、更多等）状态改变监听器
     */
    fun setOnInputStateChangedListener(listener: OnInputPanelStateChangedListener?)
}