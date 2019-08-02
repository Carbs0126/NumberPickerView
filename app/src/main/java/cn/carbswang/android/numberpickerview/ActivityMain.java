package cn.carbswang.android.numberpickerview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by Carbs.Wang.
 */
public class ActivityMain extends AppCompatActivity implements View.OnClickListener,
        NumberPickerView.OnScrollListener, NumberPickerView.OnValueChangeListener,
        NumberPickerView.OnValueChangeListenerInScrolling {

    private static final String TAG = "NumberPickerView";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private DialogNPV mDialogNPV;
    private NumberPickerView mNumberPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberPickerView = findViewById(R.id.picker);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);
        mButton8 = findViewById(R.id.button8);

        mNumberPickerView.setOnScrollListener(this);
        mNumberPickerView.setOnValueChangedListener(this);
        mNumberPickerView.setOnValueChangeListenerInScrolling(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);

        String[] displayValues = getResources().getStringArray(R.array.test_display_2);
        mNumberPickerView.refreshByNewDisplayedValues(displayValues);
        getWrapState();
    }

    //获取当前picker是否wrap
    private void getWrapState() {
        if (mNumberPickerView.getWrapSelectorWheelAbsolutely()) {
            mButton1.setText(R.string.switch_wrap_mode_true);
        } else {
            mButton1.setText(R.string.switch_wrap_mode_false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                mNumberPickerView.setWrapSelectorWheel(!mNumberPickerView.getWrapSelectorWheelAbsolutely());
                getWrapState();
                break;
            case R.id.button2:
                String[] display_1 = getResources().getStringArray(R.array.test_display_1);
                mNumberPickerView.refreshByNewDisplayedValues(display_1);
                getWrapState();
                break;
            case R.id.button3:
                String[] display_2 = getResources().getStringArray(R.array.test_display_2);
                mNumberPickerView.refreshByNewDisplayedValues(display_2);
                getWrapState();
                break;
            case R.id.button4:
                int value = mNumberPickerView.getValue();
                mNumberPickerView.smoothScrollToValue(value, value + 2);
                break;
            case R.id.button5:
                getCurrentContent();
                break;
            case R.id.button6:
                startActivity(new Intent(ActivityMain.this, ActivityTimePicker.class));
                break;
            case R.id.button7:
                showNPVDialog();
                break;
            case R.id.button8:
                changeFont();
                break;
        }
    }

    private void showNPVDialog() {
        if (mDialogNPV == null) {
            mDialogNPV = new DialogNPV(this);
        }
        if (mDialogNPV.isShowing()) {
            mDialogNPV.dismiss();
        } else {
            mDialogNPV.setCancelable(true);
            mDialogNPV.setCanceledOnTouchOutside(true);
            mDialogNPV.show();
            // recommend initializing data (or setting certain data) of NumberPickView
            // every time setting up NumberPickerView,
            // and setting attr app:npv_RespondChangeOnDetached="false" to avoid NumberPickView
            // of responding onValueChanged callback if NumberPickerView detach from window
            // when it is scrolling
            mDialogNPV.initNPV();
        }
    }

    private void getCurrentContent() {
        String[] content = mNumberPickerView.getDisplayedValues();
        if (content != null)
            Toast.makeText(getApplicationContext(),
                    getString(R.string.picked_content_is) + content[mNumberPickerView.getValue() - mNumberPickerView.getMinValue()], Toast.LENGTH_SHORT)
                    .show();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface tf = Typeface.createFromAsset(assetManager, "font/myfont.ttf");
        mNumberPickerView.setContentTextTypeface(tf);
        mNumberPickerView.setContentTextTypeface(tf);
        mNumberPickerView.postInvalidate();
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {
        Log.d(TAG, "onScrollStateChange : " + scrollState);
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        String[] content = picker.getDisplayedValues();
        if (content != null)
            Toast.makeText(getApplicationContext(), "oldVal : " + oldVal + " newVal : " + newVal + "\n" +
                    getString(R.string.picked_content_is) + content[newVal - picker.getMinValue()], Toast.LENGTH_SHORT)
                    .show();
    }

    @Override
    public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
        Log.d(TAG, "onValueChangeInScrolling oldVal : " + oldVal + " newVal : " + newVal);
    }
}