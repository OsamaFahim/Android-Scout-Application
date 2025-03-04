package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;

public class UnknownCell extends BaseCell {

    public UnknownCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        // Optionally, you can log or handle unknown cells here.
        // For now, we leave it empty.
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
