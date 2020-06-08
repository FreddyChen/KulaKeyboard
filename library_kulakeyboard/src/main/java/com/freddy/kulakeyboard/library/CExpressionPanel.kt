package com.freddy.kulakeyboard.library

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.freddy.kulakeyboard.library.bean.ExpressionType
import com.freddy.kulakeyboard.library.util.DensityUtil
import kotlinx.android.synthetic.main.layout_expression_panel.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:26
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
@Suppress("UNREACHABLE_CODE")
class CExpressionPanel : LinearLayout, IPanel {

    private var expressionTypeList = arrayListOf<ExpressionType>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_expression_panel, this, true)
        init()
    }

    override fun onVisibilityChanged(
        changedView: View,
        visibility: Int
    ) {
        super.onVisibilityChanged(changedView, visibility)
        val layoutParams = layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = KulaKeyboardHelper.keyboardHeight + DensityUtil.dp2px(context, 36.0f)
        setLayoutParams(layoutParams)
    }

    private fun init() {
        orientation = VERTICAL
        initData()
        initRecyclerView()
        initViewPager()
    }

    private fun initData() {
        expressionTypeList.add(ExpressionType(R.drawable.ic_expression_panel_tab_normal, ExpressionManager.instance.getNormalExpressionList()))
    }

    private fun initRecyclerView() {
        val expressionTypeListAdapter = ExpressionTypeListAdapter(context, expressionTypeList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = expressionTypeListAdapter
//        expressionTypeListAdapter.setOnItemClickListener{ _, _, position ->
//            view_pager.currentItem = position
//        }
    }

    private fun initViewPager() {
        val expressionPagerListAdapter = ExpressionPagerListAdapter(context as AppCompatActivity, expressionTypeList)
        view_pager.adapter = expressionPagerListAdapter
        view_pager.isUserInputEnabled = true
    }

    private val mExpressionPanelInvisibleRunnable =
        Runnable { visibility = View.INVISIBLE }

    override fun reset() {
        postDelayed(mExpressionPanelInvisibleRunnable, 250)
    }

    override fun release() {
    }

    override fun getPanelHeight(): Int {
        return KulaKeyboardHelper.keyboardHeight + DensityUtil.dp2px(
            context,
            36.0f
        )
    }
}