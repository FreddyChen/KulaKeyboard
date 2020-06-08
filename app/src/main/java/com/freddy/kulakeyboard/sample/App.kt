package com.freddy.kulakeyboard.sample

import android.app.Application

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:46
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class App : Application() {

    var keyboardHeight = 0

    companion object {
        @JvmStatic lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}