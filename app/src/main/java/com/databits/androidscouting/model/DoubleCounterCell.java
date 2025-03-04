package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.travijuu.numberpicker.library.NumberPicker;
import com.databits.androidscouting.R;

public class DoubleCounterCell extends BaseCell {

    public DoubleCounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.double_counter_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        NumberPicker picker1 = itemView.findViewById(R.id.number_counter_one);
        NumberPicker picker2 = itemView.findViewById(R.id.number_counter_two);
        if (picker1 != null && picker2 != null && mParam != null) {
            picker1.setValue(mParam.getDefault());
            picker1.setMax(mParam.getMax());
            picker1.setMin(mParam.getMin());
            picker1.setUnit(mParam.getUnit());

            picker2.setValue(mParam.getDefault());
            picker2.setMax(mParam.getMax());
            picker2.setMin(mParam.getMin());
            picker2.setUnit(mParam.getUnit());
        }
    }

    @Override
    public String exportData(View itemView) {
        NumberPicker picker1 = itemView.findViewById(R.id.number_counter_one);
        NumberPicker picker2 = itemView.findViewById(R.id.number_counter_two);
        return picker1.getValue() + "," + picker2.getValue();
    }

    @Override
    public String getType() {
        return "DoubleCounter";
    }
}
