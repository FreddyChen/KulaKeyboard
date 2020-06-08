package com.freddy.kulakeyboard.library

import com.freddy.kulakeyboard.library.bean.Expression
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 18:22
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class ExpressionManager private constructor() {

    private val normalExpressionList = arrayListOf<Expression>()

    init {
        init()
    }

    companion object {
        val instance: ExpressionManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ExpressionManager()
        }
    }

    private fun init() {
        initExpressionData()
    }

    private fun initExpressionData() {
       normalExpressionList.add(Expression(R.drawable.img_expression_1, "\$ne#1^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_2, "\$ne#2^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_3, "\$ne#3^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_4, "\$ne#4^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_5, "\$ne#5^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_6, "\$ne#6^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_7, "\$ne#7^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_8, "\$ne#8^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_9, "\$ne#9^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_10, "\$ne#10^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_11, "\$ne#11^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_12, "\$ne#12^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_13, "\$ne#13^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_14, "\$ne#14^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_15, "\$ne#15^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_16, "\$ne#16^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_17, "\$ne#17^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_18, "\$ne#18^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_19, "\$ne#19^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_20, "\$ne#20^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_21, "\$ne#21^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_22, "\$ne#22^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_23, "\$ne#23^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_24, "\$ne#24^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_25, "\$ne#25^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_26, "\$ne#26^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_27, "\$ne#27^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_28, "\$ne#28^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_29, "\$ne#29^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_30, "\$ne#30^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_31, "\$ne#31^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_32, "\$ne#32^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_33, "\$ne#33^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_34, "\$ne#34^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_35, "\$ne#35^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_36, "\$ne#36^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_37, "\$ne#37^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_38, "\$ne#38^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_39, "\$ne#39^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_40, "\$ne#40^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_41, "\$ne#41^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_42, "\$ne#42^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_43, "\$ne#43^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_44, "\$ne#44^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_45, "\$ne#45^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_46, "\$ne#46^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_47, "\$ne#47^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_48, "\$ne#48^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_49, "\$ne#49^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_50, "\$ne#50^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_51, "\$ne#51^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_52, "\$ne#52^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_53, "\$ne#53^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_54, "\$ne#54^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_55, "\$ne#55^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_56, "\$ne#56^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_57, "\$ne#57^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_58, "\$ne#58^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_59, "\$ne#59^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_60, "\$ne#60^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_61, "\$ne#61^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_62, "\$ne#62^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_63, "\$ne#63^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_64, "\$ne#64^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_65, "\$ne#65^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_66, "\$ne#66^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_67, "\$ne#67^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_68, "\$ne#68^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_69, "\$ne#69^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_70, "\$ne#70^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_71, "\$ne#71^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_72, "\$ne#72^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_73, "\$ne#73^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_74, "\$ne#74^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_75, "\$ne#75^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_76, "\$ne#76^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_77, "\$ne#77^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_78, "\$ne#78^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_79, "\$ne#79^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_80, "\$ne#80^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_81, "\$ne#81^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_82, "\$ne#82^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_83, "\$ne#83^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_84, "\$ne#84^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_85, "\$ne#85^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_86, "\$ne#86^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_87, "\$ne#87^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_88, "\$ne#88^"))
       normalExpressionList.add(Expression(R.drawable.img_expression_89, "\$ne#89^"))
    }

    fun getNormalExpressionList(): ArrayList<Expression> {
        return normalExpressionList
    }
}