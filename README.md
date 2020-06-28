# KulaKeyboard
Android仿微信键盘输入法/表情/更多面板切换

# 效果展示

+ 微信  
![微信效果展示](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592060295634.gif)
+ KulaKeyboard  
![KulaKeyboard效果展示](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1593331059700.gif)


# 使用方式

1. 添加依赖

2. 在`AndroidManifest.xml`设置对应的`activity`节点`android:windowSoftInputMode="adjustNothing"`或在`Activity setContentView()之前调用window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)`

3. 自定义输入法面板（参考`CInputPanel`）、表情面板（参考`CExpressionPanel`）、更多面板（参考`CMorePanel`）。

4. 初始化`KeyboardHelper`并绑定对应自定义Panel及设置获取到的键盘高度等
```
private lateinit var keyboardHelper: KeyboardHelper
keyboardHelper = KeyboardHelper()
        keyboardHelper.init(this)
            .bindRootLayout(layout_main)
            .bindBodyLayout(layout_body)
            .bindInputPanel(chat_input_panel)
            .bindExpressionPanel(expression_panel)
            .bindMorePanel(more_panel)
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
```
**注：如果应用有登录页面，可在用户登录弹出软键盘时获取键盘高度并保存到本地，方便下次使用。如果没有登录页面，则可以选择首次设置键盘高度为某个值，比如屏高度的2/5，这样在第一次弹出软键盘或面板时，或存在高度显示的瑕疵，在弹出键盘后，即获取到键盘高度，此时`KulaKeyboard库`会自动更新对应的值，后续会显示正确。目前Android系统并不提供直接获取键盘高度的方式，只能这样处理。考虑到一半社交App都需要登录，或在其它页面弹出软键盘时及时更新本地保存的键盘高度的值即可，影响不大。**

以上步骤即可。

另外，贴上Activity布局文件，仅供参考
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/layout_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:clipChildren="false"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/top_bar">

        <LinearLayout
            android:id="@+id/layout_body"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight="1"
                android:background="#333333" />

            <com.freddy.kulakeyboard.sample.CInputPanel
                android:id="@+id/chat_input_panel"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
        </LinearLayout>

        <com.freddy.kulakeyboard.sample.CExpressionPanel
            android:id="@+id/expression_panel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="invisible" />

        <com.freddy.kulakeyboard.sample.CMorePanel
            android:id="@+id/more_panel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="invisible" />
    </LinearLayout>

    <TextView
        android:id="@+id/top_bar"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:background="#00bfcf"
        android:gravity="center"
        android:textStyle="bold"
        android:text="TopBar"
        android:textColor="#000000"
        android:textSize="18sp"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

使用过程中，如果有任何疑问，请联系我。

开源不易。如果该项目对你有帮助，麻烦点个star鼓励一下作者。另外，欢迎fork和pr，让我们共同完善。

QQ交流群：1015178804，目前是Android IM技术交流群，后续写的文章，也会用此群进行交流。

# License


    Copyright 2020, chenshichao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
