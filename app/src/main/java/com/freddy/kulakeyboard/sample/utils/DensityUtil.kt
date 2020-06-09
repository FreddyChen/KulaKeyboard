package com.freddy.kulakeyboard.sample.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import com.freddy.kulakeyboard.sample.App
import kotlin.math.roundToInt

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:45
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
object DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    fun dp2px(dpValue: Float): Int {
        return (dpValue * getDisplayMetrics().density).roundToInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    fun px2dp(pxValue: Float): Int {
        return (pxValue / getDisplayMetrics().density).roundToInt()
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, App.instance.resources.displayMetrics
        ).roundToInt()
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    fun px2sp(pxVal: Float): Float {
        return pxVal / getDisplayMetrics().scaledDensity
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val wm = App.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    fun getScreenWidth(): Int {
        return getDisplayMetrics().widthPixels
    }

    /**
     * 获取屏幕高度
     * @return
     */
    fun getScreenHeight(): Int {
        return getDisplayMetrics().heightPixels
    }

    /**
     * 获取像素密度
     * @return
     */
    fun getDensity(): Float {
        return getDisplayMetrics().density
    }
}