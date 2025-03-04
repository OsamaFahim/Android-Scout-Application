package com.databits.androidscouting.model;

import android.view.View;
import com.travijuu.numberpicker.library.NumberPicker;
import com.databits.androidscouting.R;

public class DoubleCounterCell extends BaseCell {

    public DoubleCounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
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
