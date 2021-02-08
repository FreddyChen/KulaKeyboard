package com.freddy.kulakeyboard.example

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * FileName : CAdapter
 * Project  : KulaKeyboard
 * Package  : com.freddy.kulakeyboard.example
 * Author   : FreddyChen
 * Date     : 2021/02/08 17:06
 * Email    : chenshichao@outlook.com
 * GitHub   : https://github.com/FreddyChen
 * Describe : TODO
 */
class CAdapter(private val context: Context, private val data: MutableList<String>) :
    RecyclerView.Adapter<CAdapter.CViewHolder>() {

    class CViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return CViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int) {
        holder.tv.text = data[position]
    }

    override fun getItemCount() = data.size
}