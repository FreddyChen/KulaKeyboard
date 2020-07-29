package com.freddy.kulakeyboard.sample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freddy.kulakeyboard.library.KeyboardHelper
import com.freddy.kulakeyboard.library.OnInputPanelStateChangedListener
import com.freddy.kulakeyboard.sample.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private lateinit var keyboardHelper: KeyboardHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
        Toast.makeText(this, "键盘高度：${App.instance.keyboardHeight}", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        msgList = arrayListOf()
        for (i in 0 until 20) {
            msgList.add("Msg${i + 1}")
        }
        keyboardHelper = KeyboardHelper()
        keyboardHelper.init(this)
            .bindRootLayout(layout_main)
            .bindRecyclerView(recycler_view)
            .bindInputPanel(chat_input_panel)
            .bindExpressionPanel(expression_panel)
            .bindMorePanel(more_panel)
            .setScrollBodyLayout(msgList.size > 15)
            .setKeyboardHeight(
                if (App.instance.keyboardHeight == 0) DensityUtil.getScreenHeight(applicationContext) / 5 * 2 else App.instance.keyboardHeight
            )
            .setOnKeyboardStateListener(object : KeyboardHelper.OnKeyboardStateListener {
                override fun onOpened(keyboardHeight: Int) {
                    App.instance.keyboardHeight = keyboardHeight
                }

                override fun onClosed() {
                }
            })

        recycler_view.setHasFixedSize(true)
        val adapter = MsgListAdapter(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        scrollToBottom()

        recycler_view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                keyboardHelper.reset()
            }
            false
        }
    }

    private fun scrollToBottom() {
        recycler_view.adapter?.itemCount?.minus(1)?.let { recycler_view.scrollToPosition(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardHelper.release()
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