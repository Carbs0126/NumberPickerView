# NumberPickerView
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-NumberPickerView-green.svg?style=true)](https://android-arsenal.com/details/1/3817)
##### Another NumberPicker with more flexible attributes on Android platform 

[English] [6]

## 前言
在Android项目的开发中会用到`NumberPicker`组件，但是默认风格的`NumberPicker`具有一些不灵活的属性，定制起来也比较麻烦，并且缺少一些过渡动效，因此在应用开发时，一般采用自定义的控件来完成选择功能。

### 控件截图
====
![Example Image][4]<br>
效果图1

![Example Image][5]<br>
效果图2

![Example Image][2]<br>
静态截图以及渐变效果

![Example Image][3]<br>
NumberPickerView的实际应用，一款可以选择公历/农历日期的View，且公农历自由切换。<br>
截屏有些问题，使得看上去有点卡顿且divider颜色不一致，实际效果很流畅。具体项目地址可见：<br>
https://github.com/Carbs0126/GregorianLunarCalendar

### 说明
====
`NumberPickerView`是一款与android原生`NumberPicker`具有类似界面以及类似功能的`View`。
主要功能同样是从多个候选项中通过上下滚动的方式选择需要的选项，但是与`NumberPicker`相比较，有几个主要不同点，下面是两者的不同之处。

#### 原始控件特性-NumberPicker
1. 显示窗口只能显示3个备选选项；
2. 在fling时阻力较大，无法快速滑动；
3. 在选中与非选中状态切换比较生硬；
4. 批量改变选项中的内容时，没有动画效果；
5. 动态设置wrap模式时(`setWrapSelectorWheel()`方法)，会有“暂时显示不出部分选项”的问题；
6. 选中位置没有文字说明；
7. 代码中不能控制选项滑动滚动到某一item；

#### 自定义控件特性-NumberPickerView
1. 显示窗口可以显示多个备选选项；
2. fling时滑动速度较快，且可以设置摩擦力，如下代码使得摩擦力为默认状态的2倍<br>`mNumberPickerView.setFriction(2 * ViewConfiguration.get(mContext).getScrollFriction());`
3. 在选中与非选中的状态滑动时，具有渐变的动画效果，包括文字放大缩小以及颜色的渐变；
4. 在批量改变选项中的内容时，可以选择是否采用友好的滑动效果；
5. 可以动态的设置是否wrap，即，是否循环滚动；
6. 选中位置可以添加文字说明，可控制文字字体大小颜色等；
7. 具有在代码中动态的滑动到某一位置的功能；
8. 支持`wrap_content`，支持item的padding
9. 提供多种属性，优化UI效果
10. 在滑动过程中不响应`onValueChanged()`
11. 点击上下单元格，可以自动滑动到对应的点击对象。
12. 可通过属性设置`onValueChanged`等回调接口的执行线程。
13. 兼容NumberPicker的重要方法和接口：
```java
    兼容的方法有：
    setOnValueChangedListener()
    setOnScrollListener()
    setDisplayedValues()/getDisplayedValues()
    setWrapSelectorWheel()/getWrapSelectorWheel()
    setMinValue()/getMinValue()
    setMaxValue()/getMaxValue()
    setValue()/getValue()
    
    兼容的内部接口有：
    OnValueChangeListener
    OnScrollListener
    
    添加的接口有：
    OnValueChangeListenerInScrolling//滑动过程中响应value change
```

### 使用方法
====
1.导入至工程
```groovy
    compile 'cn.carbswang.android:NumberPickerView:1.1.1'
```
或者
```xml
    <dependency>
      <groupId>cn.carbswang.android</groupId>
      <artifactId>NumberPickerView</artifactId>
      <version>1.1.1</version>
      <type>pom</type>
    </dependency>
```
2.通过布局声明NumberPickerView
```xml
    <cn.carbswang.android.numberpickerview.library.NumberPickerView
        android:id="@+id/picker"
        android:layout_width="wrap_content"
        android:layout_height="240dp"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"
        android:background="#11333333"
        android:contentDescription="test_number_picker_view"
        app:npv_ItemPaddingHorizontal="5dp"
        app:npv_ItemPaddingVertical="5dp"
        app:npv_ShowCount="5"
        app:npv_RespondChangeOnDetached="false"
        app:npv_TextSizeNormal="16sp"
        app:npv_TextSizeSelected="20sp"
        app:npv_WrapSelectorWheel="true"/>

```
3.Java代码中使用：
  1)若设置的数据(String[] mDisplayedValues)不会再次改变，可以使用如下方式进行设置：（与NumberPicker的设置方式一致）
```java
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
```
  2)若设置的数据(String[] mDisplayedValues)会改变，可以使用如下组合方式进行设置：（与NumberPicker的更改数据方式一致）
```java
        int minValue = getMinValue();
        int oldMaxValue = getMaxValue();
        int oldSpan = oldMaxValue - minValue + 1;
        int newMaxValue = display.length - 1;
        int newSpan = newMaxValue - minValue + 1;
        if (newSpan > oldSpan) {
            setDisplayedValues(display);
            setMaxValue(newMaxValue);
        } else {
            setMaxValue(newMaxValue);
            setDisplayedValues(display);
        }
```
或者直接使用NumberPickerView提供的方法：<br>
    `refreshByNewDisplayedValues(String[] display)`<br>
使用此方法时需要注意保证数据改变前后的minValue值不变，以及设置的display不能够为null，且长度不能够为0。
  3)添加了滑动过程中响应value change的函数
  ```java
    picker.setOnValueChangeListenerInScrolling(...);
  ```


4.另外，NumberPickerView提供了平滑滚动的方法：<br>
    `public void smoothScrollToValue(int fromValue, int toValue, boolean needRespond)`<br>
    
此方法与`setValue(int)`方法相同之处是可以动态设置当前显示的item，不同之处在于此方法可以使`NumberPickerView`平滑的从滚动，即从`fromValue`值挑选最近路径滚动到`toValue`，第三个参数`needRespond`用来标识在滑动过程中是否响应`onValueChanged`回调函数。因为多个`NumberPickerView`在联动时，很可能不同的`NumberPickerView`的停止时间不同，如果在此时响应了`onValueChanged`回调，就可能再次联动，造成数据不准确，将`needRespond`置为`false`，可避免在滑动中响应回调函数。<br>

另外，在使用此方法或者间接调用此方法时，需要注意最好不要在`onCreate(Bundle savedInstanceState)`方法中调用，因为scroll动画需要一定时间，如需确要在`onCreate(Bundle savedInstanceState)`中调用，请使用如下方式：

```xml    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //代码省略
        mNumberPickerView.post(new Runnable() {
            @Override
            public void run() {
                //调用smoothScrollToValue()等方法的代码
            }
        });
    }
```    

5.各项自定义属性的说明
```xml
    <declare-styleable name="NumberPickerView">
        <attr name="npv_ShowCount" format="reference|integer" />//显示的条目个数，默认3个
        <attr name="npv_ShowDivider" format="reference|boolean" />//是否显示两条divider，默认显示
        <attr name="npv_DividerColor" format="reference|color" />//两条divider的颜色
        <attr name="npv_DividerMarginLeft" format="reference|dimension" />//divider距左侧的距离
        <attr name="npv_DividerMarginRight" format="reference|dimension" />//divider距右侧的距离
        <attr name="npv_DividerHeight" format="reference|dimension" />//divider的高度
        <attr name="npv_TextColorNormal" format="reference|color" />//未选中文字的颜色
        <attr name="npv_TextColorSelected" format="reference|color" />//选中文字的颜色
        <attr name="npv_TextColorHint" format="reference|color" />//中间偏右侧说明文字的颜色
        <attr name="npv_TextSizeNormal" format="reference|dimension" />//未选中文字的大小
        <attr name="npv_TextSizeSelected" format="reference|dimension" />//选中文字的大小
        <attr name="npv_TextSizeHint" format="reference|dimension" />//说明文字的大小
        <attr name="npv_TextArray" format="reference" />//文字内容，stringarray类型
        <attr name="npv_MinValue" format="reference|integer" />//最小值，同setMinValue()
        <attr name="npv_MaxValue" format="reference|integer" />//最大值，同setMaxValue()
        <attr name="npv_WrapSelectorWheel" format="reference|boolean" />//设置是否wrap，同setWrapSelectorWheel
        <attr name="npv_HintText" format="reference|string" />//设置说明文字
        <attr name="npv_EmptyItemHint" format="reference|string" />//空行的显示文字，默认不显示任何文字。只在WrapSelectorWheel==false是起作用
        <attr name="npv_MarginStartOfHint" format="reference|dimension" />//说明文字距离左侧的距离，"左侧"是指文字array最宽item的右侧
        <attr name="npv_MarginEndOfHint" format="reference|dimension" />//说明文字距离右侧的距离
        <attr name="npv_ItemPaddingHorizontal" format="reference|dimension" />//item的水平padding，用于wrap_content模式
        <attr name="npv_ItemPaddingVertical" format="reference|dimension" />//item的竖直padding，用于wrap_content模式
        <attr name="npv_RespondChangeOnDetached" format="reference|boolean" />//在detach时如果NumberPickerView正好滑动，设置
        //是否响应onValueChange回调，用在一个Dialog/PopupWindow被显示多次，
        //且多次显示时记录上次滑动状态的情况。建议Dialog/PopupWindow在显示时每次都指定初始值，且将此属性置为false
        <attr name="npv_RespondChangeInMainThread" format="reference|boolean" />//指定`onValueChanged`响应事件在什么线程中执行。
        //默认为`true`，即在主线程中执行。如果设置为`false`则在子线程中执行。

        //以下属性用于在wrap_content模式下，改变内容array并且又不想让控件"跳动"，那么就可以设置所有改变的内容的最大宽度
        <!--just used to measure maxWidth for wrap_content without hint,
            the string array will never be displayed.
            you can set this attr if you want to keep the wraped numberpickerview
            width unchanged when alter the content list-->
        <attr name="npv_AlternativeTextArrayWithMeasureHint" format="reference" />//可能达到的最大宽度，包括说明文字在内，最大宽度只可能比此String的宽度更大
        <attr name="npv_AlternativeTextArrayWithoutMeasureHint" format="reference" />//可能达到的最大宽度，不包括说明文字在内，最大宽度只可能比此String的宽度+说明文字+说明文字marginstart +说明文字marginend 更大
        <!--the max length of hint content-->
        <attr name="npv_AlternativeHint" format="reference|string" />//说明文字的最大宽度
    </declare-styleable>

```
    
### 版本更新
====
#### 1.1.1
1.添加更改文字typeface的方法<br>
2.添加滑动过程中响应valuechange的方法<br>
  ```java
    picker.setOnValueChangeListenerInScrolling(...);
  ```
<br>
#### 1.1.0
1.优化位置校正时的滚动时间。<br>
2.微调刷新时间。<br>
3.优化示例界面显示布局。<br>
<br>
#### 1.0.9
1.添加属性`app:npv_RespondChangeInMainThread="true"`，指定`onValueChanged`响应事件在什么线程中执行。默认为`true`，即在主线程中执行。如果设置为`false`则在子线程中执行。<br>
2.更新`TimePickerActivity`示例，以说明属性`app:npv_RespondChangeInMainThread="true"`的用法。<br>
3.修复bug: 在更新内容时，如果滑动没有停止，那么新的内容显示出来后，滚动的位置不正确的bug。<br>
<br>
#### 1.0.8
1.更改`stopScrolling`方法，在`abortAnimation()`之前添加滚动到当前坐标的代码<br>
2.更改`npv_RespondChangeOnDetached`的默认值为false<br>
<br>
#### 1.0.7
1.完善在`onDetachToWindow()`函数中添加的响应判断，主要针对多次调用`Dialog/PopupWindow`，如果此时`Dialog/PopupWindow`在隐藏时，`NumberPickerView`仍然在滑动，那么需要停止滑动+可选响应`OnValueChange`回调+更改上次选中索引。添加属性`npv_RespondChangeOnDetached`作为判断是否响应onValueChange回调，主要用在多个NumberPickerView联动的场景。同时建议每次在显示`Dialog/PopupWindow`时，重新为每个NumberPickerView设定确定的值，且将`npv_RespondChangeOnDetached`属性置为false，具体可见`GregorianLunarCalendar`项目中的dialog相关用法。此次更改方式较为笨拙，如果有好的方法，还请告知，非常感谢。<br>
<br>
#### 1.0.6
1.在`onDetachToWindow()`函数中添加响应判断，主要针对多次调用的Dialog/PopupWindow<br>
#### 1.0.5
1.在`onAttachToWindow()`函数中添加判断`mHandlerThread`有没有已经被`quit`掉的函数，避免在第二次进入dialog/popupWindow时无法刷新位置的问题<br>
#### 1.0.4
1.更改部分属性名称，更改部分注释<br>
<br>
#### 1.0.3
1.修复不能够在`ScrollView`中滑动的bug，感谢anjiao以及Elektroktay的issue<br>
<br>


### 主要原理
====
#### 1.滚动效果的产生：
`Scroller` + `VelocityTracker` + `onDraw(Canvas canvas)`

#### 2.自动校准位置。
`Handler` 刷新当前位置

#### 3.渐变的UI效果
渐变UI效果同样是通过计算当前滑动的坐标以及某个item与中间显示位置的差值比例，来确定此item中的字体大小以及颜色。


### 将NumberPicker改为NumberPickerView
====
要替代项目中使用的NumberPicker，只需要将涉及NumberPicker的代码（如回调中传入了NumberPicker、使用了NumberPicker的内部接口）改为NumberPickerView即可。<br>

### 另
UI设计借鉴了meizu的多个应用的设计。感谢google的android平台以及meizu的设计。

万水千山总是情，来个Star行不行？<br>

欢迎大家不吝指教。
email: yeah0126@yeah.net

## License

    Copyright 2016 Carbs.Wang (NumberPickerView)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/Carbs0126/Screenshot/blob/master/numberpickerview.gif
[2]: https://github.com/Carbs0126/Screenshot/blob/master/numberpickerviewall.jpg
[3]: https://github.com/Carbs0126/Screenshot/blob/master/gregorian_refine.gif
[4]: https://github.com/Carbs0126/Screenshot/blob/master/numberpickerview_refine1.gif
[5]: https://github.com/Carbs0126/Screenshot/blob/master/numberpickerview_refine2.gif
[6]: https://github.com/Carbs0126/NumberPickerView/blob/master/README_English.md
