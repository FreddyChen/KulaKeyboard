package com.freddy.kulakeyboard.sample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.freddy.kulakeyboard.library.IPanel
import com.freddy.kulakeyboard.library.util.DensityUtil

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/24 17:19
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class CMorePanel : FrameLayout, IPanel {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_more_panel, this, true)
        init()
    }

    override fun onVisibilityChanged(
        changedView: View,
        visibility: Int
    ) {
        super.onVisibilityChanged(changedView, visibility)
        val layoutParams = layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = getPanelHeight()
        setLayoutParams(layoutParams)
    }

    private fun init() {
    }

    private val mMorePanelInvisibleRunnable =
        Runnable { visibility = View.GONE }

    override fun reset() {
        postDelayed(mMorePanelInvisibleRunnable, 0)
    }

    override fun getPanelHeight(): Int {
        return App.instance.keyboardHeight - DensityUtil.dp2px(context, 56.0f)
    }
}