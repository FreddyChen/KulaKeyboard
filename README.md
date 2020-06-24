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
![KulaKeyboard键盘切换效果](https://freddy-markdown.oss-cn-shenzhen.aliyuncs.com/img/1592060037619.gif)


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

那么有没有办法可以把activity的`windowSoftInputMode`属性设置为`adjustNothing`并且可以获取键盘高度呢？答案是肯定的，这里需要用到一些小技巧：在Activity打开时，同时创建一个**高度为match_parent，宽度为0**的`PopupWindow`，宽度为0时，`PopupWindow`是不可见的，但该`PopupWindow`确实存在，由于高度为match_parent，所以在`PopupWindow`里设置`OnGlobalLayoutListener`，再通过计算，即可获取键盘高度。至于键盘打开状态，那就非常简单了，可以在`onGlobalLayout()`中判断键盘高度大于一定高度的时候即认为键盘为打开状态，比如整个屏幕高度的1/3，反之即认为键盘收起。

好了，说完了原理，我们来看看具体实现方式。

## <font color=#0072ff>具体实现</font>
## <font color=#0072ff>写在最后</font>
[Github地址](https://github.com/FreddyChen/KulaKeyboard)  
终于写完啦，本来这一块的代码在[KulaChat](https://github.com/FreddyChen/KulaChat) App里面，考虑到有很多同学自己开发IM App，需要实现键盘切换效果，所以就单独把键盘切换封装成一个Module，**项目中有一个Emoji表情的面板实现，支持自定义各种表情面板以及更多面板等**，[Github]()上面有详细的使用方式，如果项目对您有帮助，麻烦点个star，同时欢迎fork和pull request，期待大家与我一起共同完善，为开源社区贡献一点力量。