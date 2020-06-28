# KulaKeyboard
Android仿微信键盘输入法/表情/更多面板切换

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


# **这或许就是你想要的聊天键盘处理方案**
## <font color=#0072ff>写在前面</font>
<font size=6>老规矩，不想看文章的同学可以直接移步到[Github](https://github.com/FreddyChen/KulaKeyboard)</font>

首先跟大家说声抱歉，距离上一篇文章[CEventCenter](https://github.com/FreddyChen/CEventCenter)将近一年了，最近才稍微有点空闲的时间可以写写博客，工作实在太忙，抱歉哈。

近期在开源一款即时通讯App，由于之前发布的[NettyChat](https://github.com/FreddyChen/NettyChat)属于封装的一个Module，很多想基于Netty+TCP+Protobuf开发IM类App的同学不知道要怎么上手，而且群里以及掘金上也有很多同学想要聊天类的UI以及消息持久化、离线消息之类的处理逻辑代码等，所以决定从零开始，带领大家开发一款优秀的IM App，会包含[ims_kula](https:github.com/FreddyChen/ims_kula)(基础通信模块)、[KulaChat](https://github.com/FreddyChen/KulaChat)(基于ims开发的App)以及[kulachat-server](https://github.com/FreddyChen/kulachat-server)(Java服务端)，将会是一个完整项目，敬请期待～

相信不少同学都踩过Android系统键盘处理的坑，尤其是自己开发过IM App的同学，￼在处理聊天会话页的键盘弹起、表情切换、输入法切换、更多模块切换等，往往会遇到键盘挤压布局、切换闪动及切换效果比较生硬之类的问题，我也有幸遇到过，从网上找了很多种方法，但效果不尽如人意，于是决定自己自己动手撸一个。接下来，我将~~带领大家~~，算了，废话不多说，我们直接开始。

## <font color=#0072ff>效果对比</font>

+ **微信**  
![微信键盘切换效果](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592060295634.gif)

+ **KulaKeyboard**  
![KulaKeyboard键盘切换效果](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1593331059700.gif)


gif质量比较差，大家将就着看一下。从以上效果对比，我们可以注意几个点：
1. 键盘首次弹出时，是有动画效果的，可以看到RecyclerView也跟着向上平移；
2. 从输入法到表情切换时，由于表情面板比输入法稍高，可以看到键盘消失的同时，表情面板显示了，同时RecyclerView再整体向上平移了一段距离；
3. 同上，从表情面板切换到输入法时，也有动画效果；
4. 输入法或表情面板收起时，RecyclerView向下平移；
5. 表情面板右下角，由于需要显示发送及删除按钮，所以最后两行的最后两个表情总是隐藏的，除非滑动到底部才全部显示。

接下来，我们来分析一下细节。

## <font color=#0072ff>细节分析</font>
### 键盘高度获取
### windowSoftInputMode
在AndroidManifest.xml的<activity>节点，可以设置windowSoftInputMode属性，取值分别是以下10种：

+ stateUnspecified
+ stateUnchanged
+ stateHidden
+ stateAlwaysHidden
+ stateVisible
+ stateAlwaysVisible
+ adjustUnspecified
+ adjustResize
+ adjustPan
+ adjustNothing

其中，以**state**开头的都是设置软键盘的显示与隐藏的模式，我们无须关心，我们需要关心的是后面4个以**adjust**开头的属性，这4个属性是设置软键盘与显示内容之间的关系，下面我们来分析一下这4个属性，以及分别设置一下看看效果：

+ <font color=#666666>**adjustUnspecified**</font>
    - 说明  
      **默认值**。不指定是否Activity的主窗口是否调整大小来为软键盘腾出空间或是否平移窗口内容来显示内容焦点（`EditText`）。如果窗口内容存在可滚动的控件（比如`RecyclerView`）,那么系统将会选择`adjustResize`模式将窗口调整大小（重绘`RecyclerView`）。如果不存在可滚动的控件，那么系统将会将窗口整体向上平移以显示软键盘。也就是说，**如果`windowSoftInputMode`设置为`adjustUnspecified`或者不指定任何属性时，系统将会在`adjustResize`和`adjustPan`中选择合适的一种**。
    - 效果展示
      - 不包含可滚动的控件  
        ![adjustUnspecified不包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063347303.gif)
      - 包含可滚动的控件  
		![adjustUnspecified包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063412612.gif)
+ <font color=#666666>**adjustResize**</font>
    - 说明  
    Activity的主窗口总是调整大小来为软键盘腾出空间。如果主窗口存在可滑动的控件，那么系统将会调整该控件大小。如果不存在可滑动的控件，那么系统将会使主窗口布局进行压缩。
    - 效果展示
      - 不包含可滚动的控件  
        ![adjustUnspecified不包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063382793.gif)
      - 包含可滚动的控件  
		![adjustUnspecified包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063438831.gif)
+ <font color=#666666>**adjustPan**</font>
    - 说明  
    主窗口的内容焦点（`EditText`）如果处在软键盘的高度覆盖的区域时，主窗口自动向上平移直至软键盘不遮挡内容焦点为止，使用户总能看到输入内容的部分。
    - 效果展示
      - 不包含可滚动的控件  
        ![adjustUnspecified不包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063366203.gif)
      - 包含可滚动的控件  
		![adjustUnspecified包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063426141.gif)
+ <font color=#666666>**adjustNothing**</font>
    - 说明  
    不作任何反应，不关心软键盘是否遮挡内容焦点（`EditText`）。
    - 效果展示
      - 不包含可滚动的控件  
        ![adjustUnspecified不包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063396620.gif)
      - 包含可滚动的控件  
		![adjustUnspecified包含可滚动的控件](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592063450124.gif)

以上属性说明，大部分参照网上的介绍加入自己的理解，希望能通俗易懂。

### 切换动画
从上述属性说明及效果展示可以看到，虽然设置`adjustResize`可以实现软键盘弹出及输入面板切换到表情面板时Activity主窗口的`RecyclerView`通过重绘去调整大小以适应，但同时也可以看到软键盘弹出时几乎没有任何动画过渡效果，界面切换非常生硬。当然网上也有不少人的实现方式是使表情面板高度和软键盘一致，这样在输入法面板和表情面板来回切换时避免界面切换效果比较生硬的问题，但软键盘弹出的效果还是非常生硬。

那么，有没有一种方法可以使切换效果更自然、体验更好呢？答案是肯定的。卖个关子，先听我把最后一个点说完，我们再来分析一下怎么实现。
### 利用不可见的PopupWindow获取键盘打开状态及键盘高度

Android系统没有提供API可以让我们获取键盘高度，所以只能另想办法。目前比较主流的方案是通过`OnGlobalLayoutListener`的方式获取，如果`windowSoftInputMode`设置为`adjustUnspecified` | `adjustPan` | `adjustResize`其中一种，那么键盘弹出时布局将会重绘或平移，相关的`onGlobalLayout()`也将会回调，然后再做相关的计算即可得到键盘实际高度。

**注：如果`windowSoftInputMode`设置为`adjustNothing`，在键盘弹出时，`onGlobalLayout`不会回调。**

<font color=#0072ff size=4>为什么需要利用不可见的`PopupWindow`获取键盘打开状态及键盘高度呢？</font>

首先我们来分析一下`windowSoftInputMode`的属性，设置为`adjustUnspecified` 时，系统会根据控件类型选择`adjustPan` 或`adjustResize`其中一种。`adjustPan`的效果是键盘弹出时，整个布局向上平移一段距离，这不是我们想要的（因为可能会有TopBar的存在），而`adjustResize`貌似可以实现我们想要的效果，可是切换太生硬，几乎没有任何动画过渡。

综上所述，我们可以利用`adjustNothing`属性，让系统不要干预，我们自己来实现弹出键盘时布局指定控件向上平移的效果。向上平移多少像素呢？这个时候就需要获取到键盘高度，使键盘弹出时，布局向上平移键盘的高度即可。

说到这里，大家应该注意到了上面的备注：**如果`windowSoftInputMode`设置为`adjustNothing`，在键盘弹出时，`onGlobalLayout()`不会回调**。

那么有没有办法可以把activity的`windowSoftInputMode`属性设置为`adjustNothing`并且可以获取键盘高度呢？答案是肯定的，这里需要用到一些小技巧：在Activity打开时，同时创建一个**高度为match_parent，宽度为0**的`PopupWindow`，宽度为0时，`PopupWindow`是不可见的，但该`PopupWindow`确实存在，由于高度为match_parent，所以在`PopupWindow`里设置`OnGlobalLayoutListener`，再通过计算，即可获取键盘高度。至于键盘打开状态，那就非常简单了，可以在`onGlobalLayout()`中判断键盘高度大于一定高度（比如整个屏幕高度的1/3）的时候即认为键盘为打开状态，反之即认为键盘收起。

好了，说完了原理，我们来看看具体实现方式。

## <font color=#0072ff>具体实现</font>

我们先看看实现思路：假设有一个`ChatActivity`，顶部为`TopBar`，主体部分为`RecyclerView`，顶部为输入框，布局代码如下：

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

仔细观察，可以看到第一个LinearLayout设置了一个属性：`android:clipChildren="false"`，有什么作用呢？官方文档的解释是：**Defines whether a child is limited to draw inside of its bounds or not. **渣渣翻译过来的意思是：**用来定义他的子控件是否要在他应有的边界内进行绘制**，简单地说，也就是**是否允许子View超出父布局的边界**。

画个图比较直观：

![KulaKeyboard实现细节分析](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/KulaKeyboard实现细节分析.png)

如图，暗蓝色区域为手机屏幕，包含`TopBar`、`RecyclerView`、`InputPanel`，紫色区域为表情面板、更多面板等，显示在屏幕区域外。由于设置了`android:clipChildren="false"`属性，所以紫色区域不受父布局边界限制，得以显示在屏幕区域外。键盘打开时，`TopBar`保持不动，`RecyclerView`、`InputPanel`及紫色区域面板向上平移键盘高度，同理，键盘收起时这些控件向下平移键盘高度，显示到屏幕区域外，即可实现我们想要的动画过渡效果。

分析完实现方式，我们来看看具体代码实现。

由于贴全部代码篇幅过长，所以只贴关键部分代码，具体实现大家可以到[Github](https://github.com/FreddyChen/KulaKeyboard)查看。
1. 定义`IPanel`接口
```
 interface IPanel {
    	/**
        * 重置状态
        */
       fun reset()

       /**
        * 获取面板高度
        */
       fun getPanelHeight(): Int
   }
```
2. 对于`InputPanel`，需要定义特定的接口
```
interface IInputPanel : IPanel {

    /**
     * 软键盘打开
     */
    fun onSoftKeyboardOpened()

    /**
     * 软件盘关闭
     */
    fun onSoftKeyboardClosed()

    /**
     * 设置布局动画处理监听器
     */
    fun setOnLayoutAnimatorHandleListener(listener: ((panelType: PanelType, lastPanelType: PanelType, fromValue: Float, toValue: Float) -> Unit)?)

    /**
     * 设置输入面板（包括软键盘、语音、表情、更多等）状态改变监听器
     */
    fun setOnInputStateChangedListener(listener: OnInputPanelStateChangedListener?)
}
```
3. `OnInputPanelStateChangedListener`
```
interface OnInputPanelStateChangedListener {

    /**
     * 显示语音面板
     */
    fun onShowVoicePanel()

    /**
     * 显示软键盘面板
     */
    fun onShowInputMethodPanel()

    /**
     * 显示表情面板
     */
    fun onShowExpressionPanel()

    /**
     * 显示更多面板
     */
    fun onShowMorePanel()
}
```
4. `PanelTyoe`
```
enum class PanelType {

    /**
     * 面板类型：软键盘
     */
    INPUT_MOTHOD,

    /**
     * 面板类型：语音
     */
    VOICE,

    /**
     * 面板类型：表情
     */
    EXPRESSION,

    /**
     * 面板类型：更多
     */
    MORE,

    /**
     * 面板类型：无
     */
    NONE
}
```
5. `KeyboardStatePopupWindow`
```
class KeyboardStatePopupWindow(var context: Context, anchorView: View) : PopupWindow(),
    ViewTreeObserver.OnGlobalLayoutListener {

    init {
        val contentView = View(context)
        setContentView(contentView)
        width = 0
        height = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        inputMethodMode = INPUT_METHOD_NEEDED
        contentView.viewTreeObserver.addOnGlobalLayoutListener(this)

        anchorView.post {
            showAtLocation(
                anchorView,
                Gravity.NO_GRAVITY,
                0,
                0
            )
        }
    }

    private var maxHeight = 0
    private var isSoftKeyboardOpened = false

    override fun onGlobalLayout() {
        val rect = Rect()
        contentView.getWindowVisibleDisplayFrame(rect)
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom
        }
        val screenHeight: Int = DensityUtil.getScreenHeight(context)
        //键盘的高度
        val keyboardHeight = maxHeight - rect.bottom
        val visible = keyboardHeight > screenHeight / 4
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true
            onKeyboardStateListener?.onOpened(keyboardHeight)
            KulaKeyboardHelper.keyboardHeight = keyboardHeight
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false
            onKeyboardStateListener?.onClosed()
        }
    }

    fun release() {
        contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    private var onKeyboardStateListener: OnKeyboardStateListener? = null

    fun setOnKeyboardStateListener(listener: OnKeyboardStateListener?) {
        this.onKeyboardStateListener = listener
    }

    interface OnKeyboardStateListener {
        fun onOpened(keyboardHeight: Int)
        fun onClosed()
    }
}
```
接下来，就是关键的`KeyboardHelper`啦，代码比较简单，注释就懒得写了
```
class KeyboardHelper {

    private lateinit var context: Context
    private var rootLayout: ViewGroup? = null
    private var bodyLayout: ViewGroup? = null
    private var inputPanel: IInputPanel? = null
    private var expressionPanel: IPanel? = null
    private var morePanel: IPanel? = null
    private var keyboardStatePopupWindow: KeyboardStatePopupWindow? = null

    companion object {
        var keyboardHeight = 0
        var inputPanelHeight = 0
        var expressionPanelHeight = 0
        var morePanelHeight = 0
    }

    fun init(context: Context): KeyboardHelper {
        this.context = context
        return this
    }

    fun reset() {
        inputPanel?.reset()
        expressionPanel?.reset()
    }

    fun release() {
        inputPanel?.reset()
        inputPanel = null
        expressionPanel?.reset()
        expressionPanel = null
        keyboardStatePopupWindow?.dismiss()
        keyboardStatePopupWindow = null
    }

    fun setKeyboardHeight(keyboardHeight: Int): KeyboardHelper {
        KeyboardHelper.keyboardHeight = keyboardHeight
        if (inputPanelHeight == 0) {
            inputPanelHeight = keyboardHeight
        }
        return this
    }

    fun bindRootLayout(rootLayout: ViewGroup): KeyboardHelper {
        this.rootLayout = rootLayout
        keyboardStatePopupWindow = KeyboardStatePopupWindow(context, rootLayout)
        keyboardStatePopupWindow?.setOnKeyboardStateListener(object :
            KeyboardStatePopupWindow.OnKeyboardStateListener {
            override fun onOpened(keyboardHeight: Int) {
                KeyboardHelper.keyboardHeight = keyboardHeight
                inputPanel?.onSoftKeyboardOpened()
                onKeyboardStateListener?.onOpened(keyboardHeight)
                inputPanel?.apply {
                    inputPanelHeight = getPanelHeight()
                }
                expressionPanel?.apply {
                    expressionPanelHeight = getPanelHeight()
                }
                morePanel?.apply {
                    morePanelHeight = getPanelHeight()
                }
            }

            override fun onClosed() {
                inputPanel?.onSoftKeyboardClosed()
                onKeyboardStateListener?.onClosed()
            }
        })
        return this
    }

    fun bindBodyLayout(bodyLayout: ViewGroup): KeyboardHelper {
        this.bodyLayout = bodyLayout
        return this
    }

    fun <P : IPanel> bindVoicePanel(panel: P): KeyboardHelper {
        return this
    }

    fun <P : IInputPanel> bindInputPanel(panel: P): KeyboardHelper {
        this.inputPanel = panel
        inputPanelHeight = panel.getPanelHeight()
        panel.setOnInputStateChangedListener(object : OnInputPanelStateChangedListener {
            override fun onShowVoicePanel() {
                if (expressionPanel !is ViewGroup || morePanel !is ViewGroup) return
                expressionPanel?.let {
                    it as ViewGroup
                    it.visibility = View.GONE
                }
                morePanel?.let {
                    it as ViewGroup
                    it.visibility = View.GONE
                }
            }

            override fun onShowInputMethodPanel() {
                if (expressionPanel !is ViewGroup || morePanel !is ViewGroup) return
                expressionPanel?.let {
                    it as ViewGroup
                    it.visibility = View.GONE
                }
                morePanel?.let {
                    it as ViewGroup
                    it.visibility = View.GONE
                }
            }

            override fun onShowExpressionPanel() {
                if (expressionPanel !is ViewGroup) return
                expressionPanel?.let {
                    it as ViewGroup
                    it.visibility = View.VISIBLE
                }
            }

            override fun onShowMorePanel() {
                if (morePanel !is ViewGroup) return
                morePanel?.let {
                    it as ViewGroup
                    it.visibility = View.VISIBLE
                }
            }
        })
        panel.setOnLayoutAnimatorHandleListener { panelType, lastPanelType, fromValue, toValue ->
            handlePanelMoveAnimator(panelType, lastPanelType, fromValue, toValue)
        }
        return this
    }

    fun <P : IPanel> bindExpressionPanel(panel: P): KeyboardHelper {
        this.expressionPanel = panel
        expressionPanelHeight = panel.getPanelHeight()
        return this
    }

    fun <P : IPanel> bindMorePanel(panel: P): KeyboardHelper {
        this.morePanel = panel
        morePanelHeight = panel.getPanelHeight()
        return this
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun handlePanelMoveAnimator(panelType: PanelType, lastPanelType: PanelType, fromValue: Float, toValue: Float) {
        Log.d("KulaKeyboardHelper", "panelType = $panelType, lastPanelType = $lastPanelType")
        val bodyLayoutTranslationYAnimator: ObjectAnimator =
            ObjectAnimator.ofFloat(bodyLayout, "translationY", fromValue, toValue)
        var panelTranslationYAnimator: ObjectAnimator? = null
        when(panelType) {
            PanelType.INPUT_MOTHOD -> {
                expressionPanel?.reset()
                morePanel?.reset()
            }
            PanelType.VOICE -> {
                expressionPanel?.reset()
                morePanel?.reset()
            }
            PanelType.EXPRESSION -> {
                morePanel?.reset()
                panelTranslationYAnimator = ObjectAnimator.ofFloat(expressionPanel, "translationY", fromValue, toValue)
            }
            PanelType.MORE -> {
                expressionPanel?.reset()
                panelTranslationYAnimator = ObjectAnimator.ofFloat(morePanel, "translationY", fromValue, toValue)
            }
            else -> {}
        }
        val animatorSet = AnimatorSet()
        animatorSet.duration = 250
        animatorSet.interpolator = DecelerateInterpolator()
        if(panelTranslationYAnimator == null) {
            animatorSet.play(bodyLayoutTranslationYAnimator)
        }else {
            animatorSet.play(bodyLayoutTranslationYAnimator).with(panelTranslationYAnimator)
        }
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                bodyLayout?.requestLayout()
                expressionPanel?.let {
                    it as ViewGroup
                    it.requestLayout()
                }
                morePanel?.let {
                    it as ViewGroup
                    it.requestLayout()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    private var onKeyboardStateListener: OnKeyboardStateListener? = null
    fun setOnKeyboardStateListener(listener: OnKeyboardStateListener?): KeyboardHelper {
        this.onKeyboardStateListener = listener
        return this
    }

    interface OnKeyboardStateListener {
        fun onOpened(keyboardHeight: Int)
        fun onClosed()
    }
}
```
最后，贴上两个工具类的代码
`DensityUtil`
```
object DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        return (dpValue * getDisplayMetrics(context).density).roundToInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        return (pxValue / getDisplayMetrics(context).density).roundToInt()
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, context.resources.displayMetrics
        ).roundToInt()
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / getDisplayMetrics(context).scaledDensity
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    /**
     * 获取屏幕高度
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    /**
     * 获取像素密度
     * @return
     */
    fun getDensity(context: Context): Float {
        return getDisplayMetrics(context).density
    }
}
```
`UIUtil`
```
object UIUtil {

    /**
     * 使控件获取焦点
     *
     * @param view
     */
    fun requestFocus(view: View?) {
        if (view != null) {
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
        }
    }

    /**
     * 使控件失去焦点
     *
     * @param view
     */
    fun loseFocus(view: View?) {
        if (view != null) {
            val parent = view.parent as ViewGroup
            parent.isFocusable = true
            parent.isFocusableInTouchMode = true
            parent.requestFocus()
        }
    }

    /**
     * 是否应该隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param v       输入框
     */
    fun hideSoftInput(context: Context, v: View) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    fun showSoftInput(context: Context, v: View?) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, 0)
    }
}
```
至此，实现的代码已经全部贴出，对，就是这么简单~

## 调用方式
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

**由于篇幅过长，至于更详细的调用方式和自定义的`CInputPanel`、`CExpressionPanel`、`CMorePanel`，在此就不贴了，大家可以跳转至[Github](https://github.com/FreddyChen/KulaKeyboard)参考，README.md将详细讲解调用方式及自定义需要的Panel。**

## <font color=#0072ff>写在最后</font>
[Github地址](https://github.com/FreddyChen/KulaKeyboard)  
终于写完啦，本来这一块的代码在[KulaChat](https://github.com/FreddyChen/KulaChat) App里面，考虑到有很多同学自己开发IM App，需要实现键盘切换效果，所以就单独把键盘切换封装成一个Module，**项目中有一个Emoji表情的面板实现，支持自定义各种表情面板以及更多面板等**，[Github]()上面有详细的使用方式，如果项目对您有帮助，麻烦点个star，同时欢迎fork和pull request，期待大家与我一起共同完善，为开源社区贡献一点力量。