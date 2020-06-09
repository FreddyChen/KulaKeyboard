package com.freddy.kulakeyboard.sample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.freddy.kulakeyboard.sample.NormalExpressionPagerFragment
import com.freddy.kulakeyboard.sample.bean.ExpressionType

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 18:08
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class ExpressionPagerListAdapter(activity: FragmentActivity, private var expressionTypeList: ArrayList<ExpressionType>) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return if (expressionTypeList.isNullOrEmpty()) 0 else expressionTypeList.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return NormalExpressionPagerFragment.newInstance(
                expressionTypeList[position].expressionList
            )
        }

        return Fragment()
    }
}