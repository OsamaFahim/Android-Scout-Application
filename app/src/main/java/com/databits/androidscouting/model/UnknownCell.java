package com.databits.androidscouting.model;

import android.view.View;

public class UnknownCell extends BaseCell {

    public UnknownCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public String exportData(View itemView) {
        return "";
    }

    @Override
    public String getType() {
        return "Unknown";
    }
}
