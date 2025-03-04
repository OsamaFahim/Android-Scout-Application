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
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.yesno_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Additional UI binding for segmented button group can be done here.
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_yes_no);
        // (Configure the group if necessary.)
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
