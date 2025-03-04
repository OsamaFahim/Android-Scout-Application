package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.travijuu.numberpicker.library.NumberPicker;
import com.databits.androidscouting.R;

public class SpecialCell extends BaseCell {

    public SpecialCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.special_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Setup additional views such as team selector and counters.
        // For example, configure the segmented button group if needed.
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup teamSelector = itemView.findViewById(R.id.buttonGroup_segments);
        StringBuilder builder = new StringBuilder();
        builder.append(teamSelector.getButton(teamSelector.getPosition()).getText()).append(",");
        NumberPicker algaeMiss = itemView.findViewById(R.id.counterUI1).findViewById(R.id.number_counter_inside);
        NumberPicker algaeSuccess = itemView.findViewById(R.id.counterUI2).findViewById(R.id.number_counter_inside);
        NumberPicker algaeReturned = itemView.findViewById(R.id.counterUI3).findViewById(R.id.number_counter_inside);
        builder.append(algaeMiss.getValue()).append(",");
        builder.append(algaeSuccess.getValue()).append(",");
        builder.append(algaeReturned.getValue());
        return builder.toString();
    }

    @Override
    public String getType() {
        return "Special";
    }
}
