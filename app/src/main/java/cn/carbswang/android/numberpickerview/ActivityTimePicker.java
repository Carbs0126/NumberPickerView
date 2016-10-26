package cn.carbswang.android.numberpickerview;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by Carbs.Wang on 2016/6/24.
 */
public class ActivityTimePicker extends AppCompatActivity implements View.OnClickListener, NumberPickerView.OnValueChangeListener{

    private NumberPickerView mPickerViewH;
    private NumberPickerView mPickerViewM;
    private NumberPickerView mPickerViewD;
    private Button mButtonInfo;
    private Button mButtonInfo2;
    private Button mButton4;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mButtonInfo2.setText((String)msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        mPickerViewH = (NumberPickerView)this.findViewById(R.id.picker_hour);
        mPickerViewM = (NumberPickerView)this.findViewById(R.id.picker_minute);
        mPickerViewD = (NumberPickerView)this.findViewById(R.id.picker_half_day);
        mPickerViewH.setOnValueChangedListener(this);
        mPickerViewM.setOnValueChangedListener(this);
        mPickerViewD.setOnValueChangedListener(this);

        /*Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPickerViewH.setHintTextTypeface(font);*/

        mButtonInfo = (Button)this.findViewById(R.id.button_get_info);
        mButtonInfo2 = (Button)this.findViewById(R.id.show_info_button);
        mButton4 = (Button)this.findViewById(R.id.button4);
        mButtonInfo.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        initTime();
    }

    private void initTime(){
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int d = h < 12 ? 0 : 1;
        h = h % 12;

        setData(mPickerViewH, 0, 11, h);
        setData(mPickerViewM, 0, 59, m);
        setData(mPickerViewD, 0, 1, d);
    }

    private void setData(NumberPickerView picker, int minValue, int maxValue, int value){
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_get_info:
                String h = mPickerViewH.getContentByCurrValue();
                String m = mPickerViewM.getContentByCurrValue();
                String d = mPickerViewD.getContentByCurrValue();
                Toast.makeText(getApplicationContext(),h + getString(R.string.hour_hint) + " "
                        + m + getString(R.string.minute_hint) + " " + d,Toast.LENGTH_LONG).show();
            break;
            case R.id.button4:
                Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
                mPickerViewH.setHintTextTypeface(font);
                mPickerViewH.invalidate();
                break;
        }
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mButtonInfo2.setText(getString(R.string.current_thread_name) + Thread.currentThread().getName()
                    + "\n" + getString(R.string.current_picked_value) + String.valueOf(newVal));
        }else{
            Message message = Message.obtain();
            message.obj = getString(R.string.current_thread_name) + Thread.currentThread().getName()
                    + "\n" + getString(R.string.current_picked_value) + String.valueOf(newVal);
            mHandler.sendMessage(message);
        }
    }
}