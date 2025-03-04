package com.databits.androidscouting.model;

import android.view.View;
import android.widget.Spinner;
import com.databits.androidscouting.R;

public class ListCell extends BaseCell {

    public ListCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public String exportData(View itemView) {
        Spinner spinner = itemView.findViewWithTag("Spinner");
        return spinner.getSelectedItem().toString();
    }

    @Override
    public String getType() {
        return "List";
    }
}
