package cn.carbswang.android.numberpickerview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by carbs on 2016/7/11.
 */

public class DialogNPV extends Dialog implements View.OnClickListener,
        NumberPickerView.OnScrollListener, NumberPickerView.OnValueChangeListener{

    private static final String TAG = "picker";

    private Context mContext;
    private Button mButtonGetInfo;
    private NumberPickerView mNumberPickerView;
    private String[] mDisplayValues;

    public DialogNPV(Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_npv);

        mNumberPickerView = (NumberPickerView) this.findViewById(R.id.picker);
        mNumberPickerView.setOnScrollListener(this);
        mNumberPickerView.setOnValueChangedListener(this);
        mDisplayValues = mContext.getResources().getStringArray(R.array.test_display_2);
//        mNumberPickerView.refreshByNewDisplayedValues(mDisplayValues);

        mButtonGetInfo = (Button) this.findViewById(R.id.button_get_info);
        mButtonGetInfo.setOnClickListener(this);
    }

    // this method should be called after onCreate()
    public void initNPV(){
        mNumberPickerView.refreshByNewDisplayedValues(mDisplayValues);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_info:
                getCurrentContent();
                break;
        }
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {
        Log.d(TAG, "onScrollStateChange : " + scrollState);
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        String[] content = picker.getDisplayedValues();
        if (content != null) {
            Log.d(TAG,"onValueChange content : " + content[newVal - picker.getMinValue()]);
            Toast.makeText(mContext.getApplicationContext(), "oldVal : " + oldVal + " newVal : " + newVal + "\n" +
                    mContext.getString(R.string.picked_content_is) + content[newVal - picker.getMinValue()], Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void getCurrentContent(){
        String[] content = mNumberPickerView.getDisplayedValues();
        if (content != null)
            Toast.makeText(mContext.getApplicationContext(),
                    mContext.getString(R.string.picked_content_is)
                            + content[mNumberPickerView.getValue() - mNumberPickerView.getMinValue()],
                    Toast.LENGTH_SHORT)
                    .show();
    }
}