package com.databits.androidscouting.model;

import android.view.View;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

public class YesNoCell extends BaseCell {

    public YesNoCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup yesNoGroup = itemView.findViewById(R.id.buttonGroup_yes_no);
        return String.valueOf(yesNoGroup.getPosition());
    }

    @Override
    public String getType() {
        return "YesNo";
    }
}
