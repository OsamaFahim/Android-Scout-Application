package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.databits.androidscouting.R;
import com.travijuu.numberpicker.library.NumberPicker;

public class DoubleCounterCell extends BaseCell {

    public DoubleCounterCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.double_counter_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Use the travijuu NumberPicker (fully qualified)
        com.travijuu.numberpicker.library.NumberPicker picker1 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_one);
        com.travijuu.numberpicker.library.NumberPicker picker2 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_two);
        if (picker1 != null && picker2 != null && mParam != null) {
            picker1.setMin(mParam.getMin());
            picker1.setMax(mParam.getMax());
            picker1.setValue(mParam.getDefault());

            picker2.setMin(mParam.getMin());
            picker2.setMax(mParam.getMax());
            picker2.setValue(mParam.getDefault());
        }
    }

    @Override
    public String exportData(View itemView) {
        com.travijuu.numberpicker.library.NumberPicker picker1 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_one);
        com.travijuu.numberpicker.library.NumberPicker picker2 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_two);
        return picker1.getValue() + "," + picker2.getValue();
    }

    @Override
    public String getType() {
        return "DoubleCounter";
    }
}
