package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

public class SpecialCell extends BaseCell {

    public SpecialCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.special_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Additional binding for SpecialCell can be done here (e.g., configuring segmented buttons or counters).
    }

    @Override
    public String exportData(View itemView) {
        // For team selector, we assume it's a SegmentedButtonGroup nested inside a view with id teamSelector_segment.
        SegmentedButtonGroup teamSelector = itemView.findViewById(R.id.teamSelector_segment)
                .findViewById(R.id.buttonGroup_segments);
        StringBuilder builder = new StringBuilder();
        builder.append(teamSelector.getButton(teamSelector.getPosition()).getText()).append(",");

        // For each counter, use NumberPicker's getValue() after setting proper min/max.
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
