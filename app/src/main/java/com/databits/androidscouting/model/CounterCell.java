package com.databits.androidscouting.model;

import android.view.View;
import com.travijuu.numberpicker.library.NumberPicker;
import com.databits.androidscouting.R;

public class CounterCell extends BaseCell {

    public CounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public String exportData(View itemView) {
        NumberPicker numberPicker = itemView.findViewById(R.id.number_counter_inside);
        return String.valueOf(numberPicker.getValue());
    }

    @Override
    public String getType() {
        return "Counter";
    }
}
