package cn.carbswang.android.numberpickerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                    NumberPickerView.OnValueChangeListenerInScrolling{

    private static final String TAG = "picker";

    private DialogNPV mDialogNPV;
    private NumberPickerView picker;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picker = (NumberPickerView) this.findViewById(R.id.picker);
        button1 = (Button) this.findViewById(R.id.button1);
        button2 = (Button) this.findViewById(R.id.button2);
        button3 = (Button) this.findViewById(R.id.button3);
        button4 = (Button) this.findViewById(R.id.button4);
        button5 = (Button) this.findViewById(R.id.button5);
        button6 = (Button) this.findViewById(R.id.button6);
        button7 = (Button) this.findViewById(R.id.button7);

        picker.setOnScrollListener(this);
        picker.setOnValueChangedListener(this);
        picker.setOnValueChangeListenerInScrolling(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        String[] display_2 = getResources().getStringArray(R.array.test_display_2);
        picker.refreshByNewDisplayedValues(display_2);
        getWrapState();
    }

    //获取当前picker是否wrap
    private void getWrapState() {
        if (picker.getWrapSelectorWheelAbsolutely()) {
            button1.setText(R.string.switch_wrap_mode_true);
        } else {
            button1.setText(R.string.switch_wrap_mode_false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                picker.setWrapSelectorWheel(!picker.getWrapSelectorWheelAbsolutely());
                getWrapState();
                break;
            case R.id.button2:
                String[] display_1 = getResources().getStringArray(R.array.test_display_1);
                picker.refreshByNewDisplayedValues(display_1);
                getWrapState();
                break;
            case R.id.button3:
                String[] display_2 = getResources().getStringArray(R.array.test_display_2);
                picker.refreshByNewDisplayedValues(display_2);
                getWrapState();
                break;
            case R.id.button4:
                int value = picker.getValue();
                picker.smoothScrollToValue(value, value + 2);
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
        }
    }

    private void showNPVDialog(){
        if(mDialogNPV == null){
            mDialogNPV = new DialogNPV(this);
        }
        if(mDialogNPV.isShowing()){
            mDialogNPV.dismiss();
        }else {
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

    private void getCurrentContent(){
        String[] content = picker.getDisplayedValues();
        if (content != null)
            Toast.makeText(getApplicationContext(),
                    getString(R.string.picked_content_is) + content[picker.getValue() - picker.getMinValue()], Toast.LENGTH_SHORT)
                    .show();
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {
        Log.d(TAG, "onScrollStateChange : " + scrollState);
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        String[] content = picker.getDisplayedValues();
        if (content != null)
            Toast.makeText(getApplicationContext(),"oldVal : " + oldVal + " newVal : " + newVal + "\n" +
                    getString(R.string.picked_content_is) + content[newVal - picker.getMinValue()], Toast.LENGTH_SHORT)
                    .show();
    }

    @Override
    public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
        Log.d("wangjj", "onValueChangeInScrolling oldVal : " + oldVal + " newVal : " + newVal);
    }
}