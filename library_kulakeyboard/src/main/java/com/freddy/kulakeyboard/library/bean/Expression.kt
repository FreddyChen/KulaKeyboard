package com.freddy.kulakeyboard.library.bean

import java.io.Serializable

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 17:58
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
data class Expression(var resId: Int, var unique: String) : Serializable {

    override fun toString(): String {
        return "Expression(resId=$resId, unique='$unique')"
    }
}