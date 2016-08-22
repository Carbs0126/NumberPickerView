# NumberPickerView
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-NumberPickerView-green.svg?style=true)](https://android-arsenal.com/details/1/3817)
#####Another NumberPicker with more flexible attributes on Android platform

##Foreword
some android projects use the android.widget.NumberPicker to provide alternative choices, but the default style of `NumberPicker` has some inflexible attibutes, and complicated to be customized.This NumberPickerView extends from View, and provide a friendly experience.

###ScreenShot
====
![Example Image][4]<br>
picking an item dynamically

![Example Image][5]<br>

![Example Image][2]<br>

![Example Image][3]<br>
a small project powered by NumberPickerView, a view which can pick or jump to a certain date with two modes, in Gregorian mode or in Chinese Lunar mode. The gif seem to be a little lag, but actually it runs smoothly. Check the project here:
https://github.com/Carbs0126/GregorianLunarCalendar

###Introduction
====
`NumberPickerView`extends from `View` and has almost all functions of `android.widget.NumberPicker` except inputting fuction by EditText, but it has some advanced features, here are these two views' differences below:

#### the android.widget.NumberPicker
1. the NumberPicker's viewport can only show three items;
2. the value of friction is big, you can not pick a new value smoothly by fling;
3. no animation if you use setValue() in java code to set to a new value;
4. no animation if you use setDisplayValues() to change the content of NumberPicker;
5. has a bug if you set wrap mode by using `setWrapSelectorWheel()`, sometimes NumberPicker will not refresh canvas after setWrapSelectorWheel() until it receive a new MotionEvent;
6. no text hint at the center position;
7. cannot control NumberPicker to smoothly scroll to a certain item (position);
8. NumberPicker class in early version of some customized framework has bugs when changing maxValue and displayedValues.

#### NumberPickerView
1. the NumberPickerView's viewport can show more than three items;
2. can set the value of friction in java code, you can pick a new value smoothly by fling, in java code, you can use the code below to make friction be twice as former<br> `mNumberPickerView.setFriction(2 * ViewConfiguration.get(mContext).getScrollFriction());`
3. items' texts has animation between selected mode and normal mode, including Gradient textColor and Gradient textSize;
4. can choose if use animation when changing displayedValues;
5. can setWrapSelectorWheel() dynamically in java code or in xml;
6. can set a hint text at the center position, default is empty; can change the hint text's color and textSize;
7. can scroll smoothly to a centain item (position);
8. support `wrap_content` mode,support item's padding
9. has some other attibutes to refine UI
10. not respond `onValueChanged()` during scrolling
11. press the certain item, NumberPickerView will scroll to this item automatically
12. you can set if the `onValueChanged` callbacks invoked in main thread or in sub thread;
13. NumberPickerView has some same compatible fuctions and interfaces with NumberPicker, this makes it easier to change NumberPicker to NumberPickerView in project:
```java
    //compatible fuctions
    setOnValueChangedListener()
    setOnScrollListener()
    setDisplayedValues()/getDisplayedValues()
    setWrapSelectorWheel()/getWrapSelectorWheel()
    setMinValue()/getMinValue()
    setMaxValue()/getMaxValue()
    setValue()/getValue()

    //compatible interfaces
    OnValueChangeListener
    OnScrollListener
```

### How to use
====
1.import to project
```groovy
    compile 'cn.carbswang.android:NumberPickerView:1.1.0'
```
or
```xml
    <dependency>
      <groupId>cn.carbswang.android</groupId>
      <artifactId>NumberPickerView</artifactId>
      <version>1.1.0</version>
      <type>pom</type>
    </dependency>
```
2.add a NumberPickerView in xml
```xml
    <cn.carbswang.android.numberpickerview.library.NumberPickerView
        android:id="@+id/picker"
        android:layout_width="wrap_content"
        android:layout_height="240dp"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"
        android:background="#11333333"
        android:contentDescription="test_number_picker_view"
        app:npv_RespondChangeOnDetached="false"
        app:npv_ItemPaddingHorizontal="5dp"
        app:npv_ItemPaddingVertical="5dp"
        app:npv_ShowCount="5"
        app:npv_TextSizeNormal="16sp"
        app:npv_TextSizeSelected="20sp"
        app:npv_WrapSelectorWheel="true"/>

```
3.control NumberPickerView in Java code
  1)if the displayedValues in NumberPickerView will NOT change, you can set data by this way: (same as using NumberPicker)
```java
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
```
  2)if the displayedValues in NumberPickerView will change, you can set data by this way: (same as using NumberPicker)
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
OR use NumberPickerView's method: <br>
    `refreshByNewDisplayedValues(String[] display)`<br>
but make sure the minValue will NOT change before and after using this method, and `display` should not be null, and its length should be greater than 0.

4.NumberPickerView also have methods to scroll smoothly <br>
    `public void smoothScrollToValue(int fromValue, int toValue, boolean needRespond)`<br>

the same point between this method and `setValue(int)`is you can set the current picked item dynamically, the difference is this method can make `NumberPickerView` scroll smoothly from `fromValue` to `toValue` by choosing short distance, the third argument `needRespond` is a boolean flag used to set if you want NumberPickerView invoke its `onValueChanged` callback when scrolling finished this time, because if several `NumberPickerView`s have interconnections, the early stopped `NumberPickerView` invoking callbacks will effect the latter stopped ones. So you can set this flag to be false to avoid invoking `onValueChanged` callback this time. <br>

and you'd better not use this method in `onCreate(Bundle savedInstanceState)`, if have to do this, you can use in this way:

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //some code ...
        mNumberPickerView.post(new Runnable() {
            @Override
            public void run() {
                //call smoothScrollToValue()
            }
        });
    }
```

5.introduction of attibutes in xml
```xml
    <declare-styleable name="NumberPickerView">
        <attr name="npv_ShowCount" format="reference|integer" />//the count of shown items , default is 3
        <attr name="npv_ShowDivider" format="reference|boolean" />//if show dividers
        <attr name="npv_DividerColor" format="reference|color" />//color of two dividers
        <attr name="npv_DividerMarginLeft" format="reference|dimension" />//divider's margin to the left
        <attr name="npv_DividerMarginRight" format="reference|dimension" />//divider's margin to the right
        <attr name="npv_DividerHeight" format="reference|dimension" />//divider's height
        <attr name="npv_TextColorNormal" format="reference|color" />//unselected textColor
        <attr name="npv_TextColorSelected" format="reference|color" />//selected textColor
        <attr name="npv_TextColorHint" format="reference|color" />//hint text color (the text in the center item)
        <attr name="npv_TextSizeNormal" format="reference|dimension" />//unselected textSize
        <attr name="npv_TextSizeSelected" format="reference|dimension" />//selected textColor
        <attr name="npv_TextSizeHint" format="reference|dimension" />//hint text size
        <attr name="npv_TextArray" format="reference" />//displayedValues
        <attr name="npv_MinValue" format="reference|integer" />//minValue, see as setMinValue()
        <attr name="npv_MaxValue" format="reference|integer" />//maxValue, see as setMaxValue()
        <attr name="npv_WrapSelectorWheel" format="reference|boolean" />//if set wrap mode, see as setWrapSelectorWheel(boolean)
        <attr name="npv_HintText" format="reference|string" />//hint text
        <attr name="npv_EmptyItemHint" format="reference|string" />//empty item's text,only shown when WrapSelectorWheel==false or displayedValues length not large than showCount
        <attr name="npv_MarginStartOfHint" format="reference|dimension" />//distance between hint and the right side of the max wide text in displayedValues
        <attr name="npv_MarginEndOfHint" format="reference|dimension" />//distance between hint and the right side of the view
        <attr name="npv_ItemPaddingHorizontal" format="reference|dimension" />//item's horizontal padding, used for wrap_content mode
        <attr name="npv_ItemPaddingVertical" format="reference|dimension" />//item's vertical padding, used for wrap_content mode
        <attr name="npv_RespondChangeOnDetached" format="reference|boolean" />//for reusable `Dialog/PopupWindow`. 
        //If `Dialog/PopupWindow` is hiding meanwhile `NumberPickerView` is still scrolling, then we need it to stop scrolling 
        //and respond (or not) `OnValueChange` callbacks and change the previous picked value. 
        //Add a new attr `npv_RespondChangeOnDetached` as a flag to set if respondding `onValueChange` callbacks, 
        //mainly for multi linked NumberPickerViews to correct other NumberPickerView's position or value.
        //But I highly recommend every time showing a `Dialog/PopupWindow` please set certain data for NumberPickerView, 
        //and set `npv_RespondChangeOnDetached` false to avoid respondding `onValueChange` callbacks. 
        //See dialog in my `GregorianLunarCalendar` project. 

        <attr name="npv_RespondChangeInMainThread" format="reference|boolean" />//set if the `onValueChanged` callbacks invoked 
        // in mainThread or in subThread, default is true, in mainThread. set it false if you want to run `onValueChanged` in 
        // subThread.

    //these attibutes below are used under wrap_content mode, 
    //and if you want to change displayedValues with out making NumberPickerView changing its original position(four points of this view), 
    //then you should added these attrs to set a max width
        <!--just used to measure maxWidth for wrap_content without hint,
            the string array will never be displayed.
            you can set this attr if you want to keep the wraped numberpickerview
            width unchanged when alter the content list-->
        <attr name="npv_AlternativeTextArrayWithMeasureHint" format="reference" />//represents the maxWidth of displayedValues item plus hint width, including hint, the maxWidth used in onMeasure fuction must equal or be larger than this
        <attr name="npv_AlternativeTextArrayWithoutMeasureHint" format="reference" />//represents the maxWidth of displayedValues item, exclude hint text.
        <!--the max length of hint content-->
        <attr name="npv_AlternativeHint" format="reference|string" />//represents the maxWidth of hint text
    </declare-styleable>

```

<br>

### Version
====
####1.0.3
1.fix bug : cannot scroll in `ScrollView`. Thanks Elektroktay's and anjiao's issues<br>
<br>
####1.0.4
1.modify some attrs' name<br>
<br>
####1.0.5
1.in method `onAttachToWindow()`, add code to judge if `mHandlerThread`has been `quit()`, this is to avoid of 'can not correct position when show the same Dialog(or PopupWindow) twice '<br>
<br>
####1.0.6
1.add code in `onDetachToWindow()` to respond callbacks, for reusable `Dialog/PopupWindow`.<br>
<br>
####1.0.7
1.refine code in `onDetachToWindow()` to respond callbacks or not, for reusable `Dialog/PopupWindow`. <br>If `Dialog/PopupWindow` is hiding meanwhile `NumberPickerView` is still scrolling, then we need it to stop scrolling and respond (or not) `OnValueChange` callbacks and change the previous picked value. <br>Add a new attr `npv_RespondChangeOnDetached` as a flag to set if respondding `onValueChange` callbacks, mainly for multi linked NumberPickerViews to correct other NumberPickerView's position or value.<br>
But I highly recommend every time showing a `Dialog/PopupWindow` please set certain data for NumberPickerView, and set `npv_RespondChangeOnDetached` false to avoid respondding `onValueChange` callbacks. See dialog in my `GregorianLunarCalendar` project. <br>These codes are not elegant, If you have any idea, please let me know, thank you.<br>
<br>
####1.0.8
1.modify method `stopScrolling`, add scroll to current Y method before `abortAnimation()` is invoked <br>
2.modify `npv_RespondChangeOnDetached`'s default value to false<br>

####1.0.9
1.add attr `app:npv_RespondChangeInMainThread="true"` to set if the `onValueChanged` callbacks invoked in mainThread or in subThread, default is true, in mainThread. set it false if you want to run `onValueChanged` in subThread.
2.update `TimePickerActivity` example, to give a How-To-Use of `app:npv_RespondChangeInMainThread="true"`.
3.fix bug: when change displayed values, if it is scrolling, then the new displayed values' position is not rewised

####1.1.0
1.refine the duration of position rewising<br>
2.refine the interval of sending refreshing message<br>
3.refine the sample's UI<br>
<br>
### Mechanisms
====
####1.how to generate scrolling animation
`Scroller` + `VelocityTracker` + `onDraw(Canvas canvas)`

####2.how to correct position automatically when scrolling finished
`Handler` refresh current position

####3.how to generate Gradient effection
by calculating the current coordinate, get the items which should be shown and the positions of each shown items, comparing the positions and center coordinate of NumberPickerView, get the current color and size of item's text<br>
<br>
### how to CHANGE NumberPicker to NumberPickerView
====
just modify `NumberPicker` text into `NumberPickerView` in java code and xml, keep the methods and interfaces called by NumberPicker the same.<br>

### And something important<br>
UI design inspired by Meizu company, a mobile phone Manufacturer in china, thanks google and meizu<br>


enjoy<br>
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
