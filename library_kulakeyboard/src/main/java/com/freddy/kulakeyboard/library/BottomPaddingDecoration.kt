package com.freddy.kulakeyboard.library

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 20:30
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class BottomPaddingDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position =
            (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val layoutManager = parent.layoutManager
        if(layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            val itemCount = layoutManager.itemCount
            val startPos: Int
            val endPos: Int
            if(itemCount % spanCount == 0) {
                startPos = itemCount - spanCount
                endPos = itemCount
            }else {
                startPos = itemCount - itemCount % spanCount
                endPos = itemCount
            }
            if(position in startPos until endPos) {
                outRect.set(0, 0, 0, padding)
            }
        }else if(layoutManager is LinearLayoutManager) {
            if (position == parent.adapter!!.itemCount - 1) {
                outRect.set(0, 0, 0, padding)
            }
        }
    }
}