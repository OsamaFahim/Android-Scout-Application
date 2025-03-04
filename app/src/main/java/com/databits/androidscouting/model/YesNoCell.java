package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

public class YesNoCell extends BaseCell {

    public YesNoCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        // For example, set the title text in the YesNo card
        TextView titleView = itemView.findViewById(R.id.yesno_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Additional binding (e.g., setting up help balloon) can go here.
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_yes_no);
        return String.valueOf(group.getPosition());
    }

    @Override
    public String getType() {
        return "YesNo";
    }
}
