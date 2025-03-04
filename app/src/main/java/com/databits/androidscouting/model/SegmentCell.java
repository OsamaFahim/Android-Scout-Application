package com.databits.androidscouting.model;

import android.view.View;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

public class SegmentCell extends BaseCell {

    public SegmentCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);
        return String.valueOf(group.getPosition());
    }

    @Override
    public String getType() {
        return "Segment";
    }
}
