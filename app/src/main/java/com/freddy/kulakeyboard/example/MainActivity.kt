package com.freddy.kulakeyboard.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.freddy.kulakeyboard.lib.KeyboardHelper
import com.freddy.kulakeyboard.lib.KeyboardStatePopupWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recyclerViewHeight = 0
    private var editTextHeight = 0
    private var translationY = 0.0f
    private var keyboardHeight = 0
    private lateinit var adapter: CAdapter
    private val data = arrayListOf<String>()
    private lateinit var keyboardHelper: KeyboardHelper

    companion object {
        private const val TAG = "MainActivity"
    }

    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keyboardHelper = KeyboardHelper()
        keyboardHelper.init(this, layout_main, wrap_kula_recyclerView)
        tv_add.setOnClickListener {
            data.add("Test${++index}")
            adapter.notifyItemInserted(data.size - 1)
            scrollToBottom()
        }
        for (i in 0 until 7) {
            data.add("i = $i")
        }
        wrap_kula_recyclerView.setOnReadyCallback {
            adapter = CAdapter(this, data)
            wrap_kula_recyclerView.recyclerView.adapter = adapter
            scrollToBottom()
        }
    }

    private fun scrollToBottom() {
        wrap_kula_recyclerView.recyclerView.adapter?.itemCount?.minus(1)
            ?.let { wrap_kula_recyclerView.recyclerView.smoothScrollToPosition(it) }
    }
}