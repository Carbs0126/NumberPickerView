package cn.carbswang.android.numberpickerview;

import android.os.Bundle;
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
public class ActivityTimePicker extends AppCompatActivity implements View.OnClickListener{

    private NumberPickerView mPickerViewH;
    private NumberPickerView mPickerViewM;
    private NumberPickerView mPickerViewD;
    private Button mButtonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        mPickerViewH = (NumberPickerView)this.findViewById(R.id.picker_hour);
        mPickerViewM = (NumberPickerView)this.findViewById(R.id.picker_minute);
        mPickerViewD = (NumberPickerView)this.findViewById(R.id.picker_half_day);

        mButtonInfo = (Button)this.findViewById(R.id.button_get_info);
        mButtonInfo.setOnClickListener(this);

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
        }
    }
}