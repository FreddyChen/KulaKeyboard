package com.freddy.kulakeyboard.sample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.freddy.kulakeyboard.sample.R
import com.freddy.kulakeyboard.sample.bean.ExpressionType

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 18:06
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class ExpressionTypeListAdapter(private val context: Context, private var expressionTypeList: ArrayList<ExpressionType>) : RecyclerView.Adapter<ExpressionTypeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expression_type, parent, false))
    }

    override fun getItemCount(): Int {
        return if (expressionTypeList.isNullOrEmpty()) 0 else expressionTypeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.expressionIconImageView.setImageResource(expressionTypeList[position].resId)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expressionIconImageView: ImageView = itemView.findViewById(R.id.iv_expression_icon)
    }
}