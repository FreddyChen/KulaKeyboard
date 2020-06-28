package com.freddy.kulakeyboard.library

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 16:18
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
interface OnInputPanelStateChangedListener {

    /**
     * 显示语音面板
     */
    fun onShowVoicePanel()

    /**
     * 显示软键盘面板
     */
    fun onShowInputMethodPanel()

    /**
     * 显示表情面板
     */
    fun onShowExpressionPanel()

    /**
     * 显示更多面板
     */
    fun onShowMorePanel()
}