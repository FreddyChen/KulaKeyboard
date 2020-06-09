package com.freddy.kulakeyboard.sample

import kotlin.experimental.xor

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/09 17:50
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
fun main(args: Array<String>) {

    val oldData = "313233343536505810"
    val a = hexStringToByte(oldData)
    val b = getXor(a)
    println("result = ${Integer.toHexString(b.toInt())}")
}

fun hexStringToByte(hex: String): ByteArray {
    val len = hex.length / 2
    val result = ByteArray(len)
    val achar = hex.toCharArray()
    for (i in 0 until len) {
        val pos = i * 2
        result[i] = (toByte(achar[pos]).toInt().shl(4).or(toByte(achar[pos + 1]).toInt())).toByte()
    }
    return result
}

private const val hexString = "0123456789ABCDEF"
fun toByte(c: Char): Byte {
    return hexString.indexOf(c).toByte()
}

/**
 * 获取异或校验码
 * @param data
 * @return
 */
private fun getXor(data: ByteArray): Byte {
    var xor = data[0]
    for (i in 1 until data.size) {
        xor = xor xor data[i]
    }
    return xor
}