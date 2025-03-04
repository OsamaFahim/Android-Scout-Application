package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.travijuu.numberpicker.library.NumberPicker;
import com.databits.androidscouting.R;

public class CounterCell extends BaseCell {

    public CounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.counter_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        NumberPicker picker = itemView.findViewById(R.id.number_counter_inside);
        if (picker != null && mParam != null) {
            picker.setValue(mParam.getDefault());
            picker.setMax(mParam.getMax());
            picker.setMin(mParam.getMin());
            picker.setUnit(mParam.getUnit());
        }
    }

    @Override
    public String exportData(View itemView) {
        NumberPicker picker = itemView.findViewById(R.id.number_counter_inside);
        return String.valueOf(picker.getValue());
    }

    @Override
    public String getType() {
        return "Counter";
    }
}
