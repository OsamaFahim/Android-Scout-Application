package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.databits.androidscouting.R;
// Use the travijuu NumberPicker, not android.widget.NumberPicker
import com.travijuu.numberpicker.library.NumberPicker;

public class CounterCell extends BaseCell {

    public CounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.counter_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Use the travijuu NumberPicker methods for version 1.0.7
        NumberPicker picker = (NumberPicker) itemView.findViewById(R.id.number_counter_inside);
        if (picker != null && mParam != null) {
            picker.setMin(mParam.getMin());
            picker.setMax(mParam.getMax());
            picker.setValue(mParam.getDefault());
        }
    }

    @Override
    public String exportData(View itemView) {
        NumberPicker picker = (NumberPicker) itemView.findViewById(R.id.number_counter_inside);
        return String.valueOf(picker.getValue());
    }

    @Override
    public String getType() {
        return "Counter";
    }
}
