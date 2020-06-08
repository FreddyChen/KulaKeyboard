package com.freddy.kulakeyboard.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freddy.kulakeyboard.library.bean.Expression
import java.lang.Exception

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 18:01
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class ExpressionListAdapter(val context: Context, var expressionList: ArrayList<Expression>) : RecyclerView.Adapter<ExpressionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expression, parent, false))
    }

    override fun getItemCount(): Int {
        return if (expressionList.isNullOrEmpty()) 0 else expressionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.expressionImageView.setImageResource(expressionList[position].resId)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expressionImageView: ImageView = itemView.findViewById(R.id.iv_expression)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}