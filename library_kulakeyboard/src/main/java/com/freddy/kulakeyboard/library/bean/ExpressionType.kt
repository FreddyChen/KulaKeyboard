package com.freddy.kulakeyboard.library.bean

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 17:59
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
data class ExpressionType(var resId: Int, var expressionList: ArrayList<Expression>) {

    override fun toString(): String {
        return "ExpressionType(resId=$resId, expressionList=$expressionList)"
    }
}