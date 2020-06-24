package com.freddy.kulakeyboard.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.freddy.kulakeyboard.sample.utils.SoftKeyboardStateHelper
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:30
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var softKeyboardStateHelper: SoftKeyboardStateHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        softKeyboardStateHelper = SoftKeyboardStateHelper(layout_main)
        softKeyboardStateHelper.addSoftKeyboardStateListener(object :
            SoftKeyboardStateHelper.SoftKeyboardStateListener {

            override fun onSoftKeyboardOpened(keyboardHeight: Int) {
                App.instance.keyboardHeight = keyboardHeight
            }

            override fun onSoftKeyboardClosed() {
            }
        })

        btn_login.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ChatActivity::class.java))
        }
    }
}