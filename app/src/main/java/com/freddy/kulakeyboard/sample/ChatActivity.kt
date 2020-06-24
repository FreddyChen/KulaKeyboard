package com.freddy.kulakeyboard.sample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freddy.kulakeyboard.library.KulaKeyboardHelper
import com.freddy.kulakeyboard.sample.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_chat.*

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:50
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class ChatActivity : AppCompatActivity() {

    private lateinit var msgList: ArrayList<String>
    private lateinit var kulaKeyboardHelper: KulaKeyboardHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
        Toast.makeText(this, "键盘高度：${App.instance.keyboardHeight}", Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        kulaKeyboardHelper = KulaKeyboardHelper()
        kulaKeyboardHelper.init(this)
            .bindRootLayout(layout_main)
            .bindBodyLayout(layout_body)
            .bindInputPanel(chat_input_panel)
            .bindExpressionPanel(expression_panel)
            .bindMorePanel(more_panel)
            .setKeyboardHeight(
                if (App.instance.keyboardHeight == 0) DensityUtil.getScreenHeight(applicationContext) / 5 * 2 else App.instance.keyboardHeight
            )
            .setOnKeyboardStateListener(object : KulaKeyboardHelper.OnKeyboardStateListener {
                override fun onOpened(keyboardHeight: Int) {
                    App.instance.keyboardHeight = keyboardHeight
                }

                override fun onClosed() {
                }
            })

        msgList = arrayListOf()
        for (i in 0 until 100) {
            msgList.add("Msg${i + 1}")
        }
        recycler_view.setHasFixedSize(true)
        val adapter = MsgListAdapter(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        scrollToBottom()

        recycler_view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                kulaKeyboardHelper.reset()
            }
            false
        }
    }

    private fun scrollToBottom() {
        recycler_view.adapter?.itemCount?.minus(1)?.let { recycler_view.scrollToPosition(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        kulaKeyboardHelper.release()
    }

    private inner class MsgListAdapter(val context: Context) :
        RecyclerView.Adapter<MsgListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return if (msgList.isNullOrEmpty()) 0 else msgList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.msgTextView.text = msgList[position]
        }

        private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val msgTextView: TextView = itemView.findViewById(R.id.tv_msg)
        }
    }
}