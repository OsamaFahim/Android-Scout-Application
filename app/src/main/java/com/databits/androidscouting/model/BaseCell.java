package com.databits.androidscouting.model;

import android.view.View;

public abstract class BaseCell {
    protected int mCellId;
    protected String mTitle;
    protected CellParam mParam; // Additional parameters if needed

    public BaseCell(int mCellId, String mTitle, CellParam mParam) {
        this.mCellId = mCellId;
        this.mTitle = mTitle;
        this.mParam = mParam;
    }

    // Each subclass implements how to export its own data from its view.
    public abstract String exportData(View itemView);

    // Returns the cell type as a String (used for view type in adapter)
    public abstract String getType();
}
