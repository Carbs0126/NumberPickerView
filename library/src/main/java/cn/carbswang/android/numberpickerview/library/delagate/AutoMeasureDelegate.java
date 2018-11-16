package cn.carbswang.android.numberpickerview.library.delagate;

import android.graphics.Paint;

/**
 * Created by  uni7corn
 * <p>
 * on 2018/11/16
 * desc: 修正显示的内容，自动测量文本大小，当文本大小超过了控件大小时，自动缩放文本内容
 */
public class AutoMeasureDelegate {

    public static int autoMeasureTextSize(float textSize, float totalWidth, String content) {
        Paint measurePint = new Paint();
        measurePint.setTextSize(textSize);
        float measureTextWidth = measureText(measurePint, content);
        //Log.e(TAG, "PaintTextSize=${textPaint.textSize} textSize=$textSize  measureTextWidth=$measureTextWidth  totalWidth=$totalWidth contentByCurrValue=$contentByCurrValue")
        if (measureTextWidth >= totalWidth) {//超过了控件大小，（ps:当 measureTextWidth==totalWidth（不让文本贴合在控件边框）)）需要进行缩放即 scaleTextSize=textSize-1
            return autoMeasureTextSize(textSize - 1, totalWidth, content);
        } else {//小于控件大小，直接返回
            return (int) textSize;
        }
    }

    private static float measureText(Paint textPaint, String content) {
        return (textPaint.measureText(content) + 0.5f);
    }
}
