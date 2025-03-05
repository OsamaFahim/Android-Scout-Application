package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.util.Log;

public class UnknownCell extends BaseCell {

    public UnknownCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        // No UI binding is necessary for unknown cell types.
        // Optionally, log that an unknown cell was encountered.
        Log.w("UnknownCell", "Binding unknown cell: " + mTitle);
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
