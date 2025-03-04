package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;

public abstract class   BaseCell {
    protected int mCellId;
    protected String mTitle;
    protected CellParam mParam; // Your existing parameter class

    public BaseCell(int mCellId, String mTitle, CellParam mParam) {
        this.mCellId = mCellId;
        this.mTitle = mTitle;
        this.mParam = mParam;
    }

    // Each subclass implements how to bind its data to the view.
    public abstract void bind(View itemView, Context context);

    // Each subclass implements how to export its own data from its view.
    public abstract String exportData(View itemView);

    // Returns the cell type as a String (used for view type in adapter)
    public abstract String getType();

    // Optionally, you can add additional helper methods if needed.
}
